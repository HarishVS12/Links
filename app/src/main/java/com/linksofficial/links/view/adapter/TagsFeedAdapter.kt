package com.linksofficial.links.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linksofficial.links.R
import com.linksofficial.links.data.model.Tags
import com.linksofficial.links.databinding.ContainerFeedTagsBinding
import com.linksofficial.links.viewmodel.FeedVM

class TagsFeedAdapter(val context:Context, val viewModel: FeedVM) :
    ListAdapter<Tags, TagsFeedAdapter.TagsAddPostViewHolder>(TagFeedDiffUtil()) {

    private var selectedPosition: Int = 0

    inner class TagsAddPostViewHolder(val binding: ContainerFeedTagsBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(tags:Tags){
            binding.tvTags.text = tags.tagName

            if(selectedPosition==-1){
                updateUI(false,binding)
            }else{
                if(selectedPosition==adapterPosition)
                    updateUI(true,binding)
                else updateUI(false,binding)
            }

            binding.tvTags.setOnClickListener {
                updateUI(true,binding)
                if(selectedPosition!=adapterPosition){
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                }
                viewModel.setTagPosition(adapterPosition)
            }

        }

    }

    fun updateUI(isSelected:Boolean, binding:ContainerFeedTagsBinding){
        if(isSelected){
            binding.cardTag.background =  context.getDrawable(R.drawable.ic_background_feed_highlighted)
            binding.tvTags.setTextColor(Color.WHITE)
        }else{
            binding.cardTag.background =  context.getDrawable(R.drawable.ic_background_feed)
            binding.tvTags.setTextColor(Color.BLACK)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAddPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerFeedTagsBinding.inflate(inflater)
        return TagsAddPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagsAddPostViewHolder, position: Int) {
        val tag = getItem(position)
        getItem(position)?.let {
            holder.bind(tag)
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