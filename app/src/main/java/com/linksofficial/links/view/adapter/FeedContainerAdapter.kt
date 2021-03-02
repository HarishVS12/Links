package com.linksofficial.links.view.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.databinding.ContainerPostFeedBinding
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import com.linksofficial.links.view.ui.activities.WebViewActivity
import com.linksofficial.links.viewmodel.FeedVM

class FeedContainerAdapter(private val feedVM: FeedVM) :
    ListAdapter<Post, FeedContainerAdapter.FeedContainerVH>(FeedContainerDiffUTIL()) {


    inner class FeedContainerVH(val binding: ContainerPostFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.vm = feedVM
        }

        fun bind(post: Post) {
            feedVM.setPostItem(post)
            feedVM.getImageFromURL(post.link ?: "", binding.ivThumbnail, binding.ivThumbShimmer)

            binding.cardPost.setOnClickListener {
                var link = post.link!!
                if (!checkIfYoutube(it, link)) {
                    val intent =
                        Intent((it.context as LinkMainActivity), WebViewActivity::class.java)
                    intent.putExtra("url", link.trim())
                    it.context.startActivity(intent)
                }
            }

            /*binding.ivShare.setOnClickListener {
                Share.shareLink(it.context, post.link!!)
            }*/

            /*binding.ivOptions.setOnClickListener {
                val postDirections =
                    FeedFragmentDirections.actionFeedFragmentToFeedPropBottomSheet(post)
                (it.context as LinkMainActivity).findNavController(R.id.home_nav_host)
                    .navigate(postDirections)
            }*/


        }

    }

    private fun checkIfYoutube(v: View, link: String): Boolean {
        if (link.contains("youtu.be") || link.contains("youtube.com")) {
            v.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
            return true
        }
        return false
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedContainerAdapter.FeedContainerVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerPostFeedBinding.inflate(inflater, parent, false)
        return FeedContainerVH(binding)
    }

    override fun onBindViewHolder(holder: FeedContainerAdapter.FeedContainerVH, position: Int) {
        val postItem = getItem(position)
        holder.bind(postItem)
        holder.binding.executePendingBindings()
    }

}

class FeedContainerDiffUTIL() : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}
