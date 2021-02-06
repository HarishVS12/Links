package com.linksofficial.links.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.linksofficial.links.databinding.ContainerFeedPostBinding

class FeedContainerAdapter() : RecyclerView.Adapter<FeedContainerAdapter.FeedContainerVH>() {


    class FeedContainerVH(val binding: ContainerFeedPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedContainerAdapter.FeedContainerVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerFeedPostBinding.inflate(inflater)
        return FeedContainerAdapter.FeedContainerVH(binding)
    }

    override fun onBindViewHolder(holder: FeedContainerAdapter.FeedContainerVH, position: Int) {

    }

    override fun getItemCount(): Int = 3

}
