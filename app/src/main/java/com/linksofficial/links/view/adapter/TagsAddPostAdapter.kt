package com.linksofficial.links.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linksofficial.links.data.model.Tags
import com.linksofficial.links.databinding.ContainerTagsBinding
import com.linksofficial.links.viewmodel.SelectTagVM

class TagsAddPostAdapter: ListAdapter<Tags,TagsAddPostAdapter.TagsAddPostViewHolder>(TagDiffUtil()){


    inner class TagsAddPostViewHolder(val binding: ContainerTagsBinding): RecyclerView.ViewHolder(binding.root){
        val text = binding.tvTags
        val vm = SelectTagVM()
        init {
            binding.vm = vm
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAddPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerTagsBinding.inflate(inflater)
        return TagsAddPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagsAddPostViewHolder, position: Int) {
        val position = getItem(position)
        holder.text.text = position.tagName
    }

}

class TagDiffUtil: DiffUtil.ItemCallback<Tags>(){
    override fun areItemsTheSame(oldItem: Tags, newItem: Tags): Boolean {
        return oldItem.tagName==newItem.tagName
    }

    override fun areContentsTheSame(oldItem: Tags, newItem: Tags): Boolean {
        return oldItem==newItem
    }
}