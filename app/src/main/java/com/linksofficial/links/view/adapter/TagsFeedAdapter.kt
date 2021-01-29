package com.linksofficial.links.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linksofficial.links.data.model.Tags
import com.linksofficial.links.databinding.ContainerFeedTagsBinding
import com.linksofficial.links.viewmodel.FeedVM

class TagsFeedAdapter() :
    ListAdapter<Tags, TagsFeedAdapter.TagsAddPostViewHolder>(TagFeedDiffUtil()) {

    private var selectedPosition: Int = 0
    private var isFirstTimeCreated = true

    inner class TagsAddPostViewHolder(val binding: ContainerFeedTagsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val vm = FeedVM()

        init {
            binding.vm = vm
        }

        val text = binding.tvTags

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAddPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerFeedTagsBinding.inflate(inflater)
        return TagsAddPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagsAddPostViewHolder, position: Int) {
        val tag = getItem(position)
        getItem(position)?.let {
            if (position == 0 && isFirstTimeCreated) {
                isFirstTimeCreated = false
                holder.vm.isTagFocused.set(true)
            }
        }
    }

}

class TagFeedDiffUtil : DiffUtil.ItemCallback<Tags>() {
    override fun areItemsTheSame(oldItem: Tags, newItem: Tags): Boolean {
        return oldItem.tagName == newItem.tagName
    }

    override fun areContentsTheSame(oldItem: Tags, newItem: Tags): Boolean {
        return oldItem == newItem
    }
}