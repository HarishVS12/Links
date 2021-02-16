package com.linksofficial.links.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.linksofficial.links.data.local.model.PostLocal
import com.linksofficial.links.databinding.ContainerMySavedLinksBinding
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import com.linksofficial.links.view.ui.activities.WebViewActivity
import com.linksofficial.links.viewmodel.MyLinkVM
import timber.log.Timber

class MySavedLinkTabAdapter(val vm: MyLinkVM) :
    ListAdapter<PostLocal, MySavedLinkTabAdapter.MyLinkTabViewHolder>(MySavedLinkDiffUtil()) {

    inner class MyLinkTabViewHolder(val binding: ContainerMySavedLinksBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(post: PostLocal) {
            vm.getImageFromURL(binding.ivThumbnail, post.postLink!!)


            binding.apply {
                tvTitle.text = post.postTitle
                tvCaption.text = post.postCaption
                tvUserName.text = "Posted by ${post.userName}"
                tvLink.text = "\u2022 ${post.postLink}"


                Glide.with(ivUserPhoto.context)
                    .load(post.userPhotoUrl)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivUserPhoto)
            }

            binding.constraintMain.setOnClickListener {
                val intent = Intent((it.context as LinkMainActivity), WebViewActivity::class.java)
                intent.putExtra("url", post.postLink!!)
                Timber.d("UrlForWeb: ${post.postLink!!}")
                it.context.startActivity(intent)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLinkTabViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerMySavedLinksBinding.inflate(inflater)
        return MyLinkTabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyLinkTabViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class MySavedLinkDiffUtil() : DiffUtil.ItemCallback<PostLocal>() {

    override fun areItemsTheSame(oldItem: PostLocal, newItem: PostLocal): Boolean {
        return oldItem.postID == newItem.postID
    }

    override fun areContentsTheSame(oldItem: PostLocal, newItem: PostLocal): Boolean {
        return oldItem == newItem
    }

}