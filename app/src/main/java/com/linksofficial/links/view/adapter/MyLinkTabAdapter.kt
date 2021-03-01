package com.linksofficial.links.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.databinding.ContainerMyLinksBinding
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import com.linksofficial.links.view.ui.activities.WebViewActivity
import com.linksofficial.links.viewmodel.MyLinkVM
import timber.log.Timber

class MyLinkTabAdapter(val vm: MyLinkVM) :
    ListAdapter<Post, MyLinkTabAdapter.MyLinkTabViewHolder>(MyLinkDiffUtil()) {

    inner class MyLinkTabViewHolder(val binding: ContainerMyLinksBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(post: Post) {
            vm.getImageFromURL(binding.ivThumbnail, post.link!!, binding.ivShimmerThumb)


            binding.apply {
                tvTitle.text = post.title
                tvCaption.text = post.caption
                tvLink.text = "${post.link}"
            }

            binding.constraintMain.setOnClickListener {
                val intent = Intent((it.context as LinkMainActivity), WebViewActivity::class.java)
                intent.putExtra("url", post.link!!)
                Timber.d("UrlForWeb: ${post.link!!}")
                it.context.startActivity(intent)
            }

            binding.ivPopup.setOnClickListener {
                val document = post.user_id + "_" + post.id
                vm.openPopUp(it, document, post.is_public)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLinkTabViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerMyLinksBinding.inflate(inflater)
        return MyLinkTabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyLinkTabViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class MyLinkDiffUtil() : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}