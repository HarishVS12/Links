package com.linksofficial.links.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.databinding.ContainerFeedPostBinding
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import com.linksofficial.links.view.ui.activities.WebViewActivity
import com.linksofficial.links.viewmodel.FeedVM
import timber.log.Timber

class FeedContainerAdapter() :
    ListAdapter<Post, FeedContainerAdapter.FeedContainerVH>(FeedContainerDiffUTIL()) {


    class FeedContainerVH(val binding: ContainerFeedPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val vm = FeedVM()

        init {
            binding.vm = vm
        }

        fun bind(post: Post) {
            vm.setPostItem(post)
            vm.getImageFromURL(binding.ivPostImage, post.link ?: "")

            binding.cardPost.setOnClickListener {
                val intent = Intent((it.context as LinkMainActivity), WebViewActivity::class.java)
                intent.putExtra("url", post.link!!)
                Timber.d("UrlForWeb: ${post.link!!}")
                it.context.startActivity(intent)
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
