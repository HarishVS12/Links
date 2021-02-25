package com.linksofficial.links.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linksofficial.links.R
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.databinding.ContainerFeedPostBinding
import com.linksofficial.links.utils.Share
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import com.linksofficial.links.view.ui.activities.WebViewActivity
import com.linksofficial.links.view.ui.home.bottomNav.FeedFragmentDirections
import com.linksofficial.links.viewmodel.FeedVM
import timber.log.Timber

class FeedContainerAdapter(private val feedVM: FeedVM) :
    ListAdapter<Post, FeedContainerAdapter.FeedContainerVH>(FeedContainerDiffUTIL()) {


    inner class FeedContainerVH(val binding: ContainerFeedPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val vms = feedVM

        init {
            binding.vm = vms
        }

        fun bind(post: Post) {
            vms.setPostItem(post)
            vms.getImageFromURL(post.link ?: "", binding.ivThumbnail)

            binding.cardPost.setOnClickListener {
                val intent = Intent((it.context as LinkMainActivity), WebViewActivity::class.java)
                intent.putExtra("url", post.link!!)
                Timber.d("UrlForWeb: ${post.link!!}")

/*              val url = post.link
                val builder = CustomTabsIntent.Builder()
                builder.setUrlBarHidingEnabled(true)
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, url!!.toUri())
*/
                it.context.startActivity(intent)
            }

            binding.ivShare.setOnClickListener {
                Share.shareLink(it.context, post.link!!)
            }

            binding.ivOptions.setOnClickListener {
                val postDirections =
                    FeedFragmentDirections.actionFeedFragmentToFeedPropBottomSheet(post)
                (it.context as LinkMainActivity).findNavController(R.id.home_nav_host)
                    .navigate(postDirections)
            }

        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedContainerVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerFeedPostBinding.inflate(inflater, parent, false)
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
