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
import com.linksofficial.links.databinding.ContainerMyLinksBinding
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import com.linksofficial.links.view.ui.activities.WebViewActivity
import com.linksofficial.links.viewmodel.MyLinkVM

class MyLinkTabAdapter(val vm: MyLinkVM) :
    ListAdapter<Post, MyLinkTabAdapter.MyLinkTabViewHolder>(MyLinkDiffUtil()) {

    inner class MyLinkTabViewHolder(val binding: ContainerMyLinksBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(post: Post) {
            vm.getImageFromURL(binding.ivThumbnail, post.link!!, binding.ivShimmerThumb)

            if(post.caption.isNullOrEmpty()){
                binding.tvCaption.visibility = View.GONE
            }else{
                binding.tvCaption.visibility = View.VISIBLE
            }

            binding.apply {
                tvTitle.text = post.title
                tvCaption.text = post.caption
                tvLink.text = "${post.link}"
            }


            binding.constraintMain.setOnClickListener {
                var link = post.link!!
                if (!checkIfYoutube(it, link)) {
                    val intent =
                        Intent((it.context as LinkMainActivity), WebViewActivity::class.java)
                    intent.putExtra("url", link.trim())
                    it.context.startActivity(intent)
                }
            }

            binding.ivPopup.setOnClickListener {
                val document = post.user_id + "_" + post.id
                vm.openPopUp(it, document, post.is_public)
            }

        }

    }


    private fun checkIfYoutube(v: View, link: String): Boolean {
        if (link.contains("youtu.be") || link.contains("youtube.com")) {
            v.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
            return true
        }
        return false
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