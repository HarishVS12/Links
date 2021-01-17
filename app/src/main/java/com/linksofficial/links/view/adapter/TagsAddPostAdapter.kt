package com.linksofficial.links.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linksofficial.links.data.model.Tags
import com.linksofficial.links.databinding.ContainerTagsBinding
import com.linksofficial.links.viewmodel.SelectTagVM

class TagsAddPostAdapter :
    ListAdapter<Tags, TagsAddPostAdapter.TagsAddPostViewHolder>(TagDiffUtil()) {

    private var selectedPosition: Int = -1

    inner class TagsAddPostViewHolder(val binding: ContainerTagsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val text = binding.tvTags
        val vm = SelectTagVM()

        init {
            binding.vm = vm
        }

        fun bind(tag: Tags) {
            //Set Name of the Tag
            binding.tvTags.text = tag.tagName

            //Set ImageView visibility
            if (selectedPosition == -1)
                setVisibility(binding, View.GONE)
            else {
                if (selectedPosition == adapterPosition)
                    setVisibility(binding, View.VISIBLE)
                else setVisibility(binding, View.GONE)
            }

            //Visibility when clicked
            binding.tvTags.setOnClickListener {
                setVisibility(binding, View.VISIBLE)
                if (selectedPosition != adapterPosition) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                }

            }

        }

    }

    private fun setVisibility(binding: ContainerTagsBinding, visibility: Int) {
        binding.ivSelected.visibility = visibility
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAddPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerTagsBinding.inflate(inflater)
        return TagsAddPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagsAddPostViewHolder, position: Int) {
        val tag = getItem(position)
        holder.bind(tag)
    }

}

class TagDiffUtil : DiffUtil.ItemCallback<Tags>() {
    override fun areItemsTheSame(oldItem: Tags, newItem: Tags): Boolean {
        return oldItem.tagName == newItem.tagName
    }

    override fun areContentsTheSame(oldItem: Tags, newItem: Tags): Boolean {
        return oldItem == newItem
    }
}