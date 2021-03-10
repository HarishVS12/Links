package com.linksofficial.links.view.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.linksofficial.links.R
import com.linksofficial.links.data.local.model.SavedPosts
import com.linksofficial.links.databinding.ContainerMySavedLinksBinding
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import com.linksofficial.links.view.ui.activities.WebViewActivity
import com.linksofficial.links.viewmodel.MyLinkVM

class MySavedLinkTabAdapter(val vm: MyLinkVM) :
    ListAdapter<SavedPosts, MySavedLinkTabAdapter.MyLinkTabViewHolder>(MySavedLinkDiffUtil()) {

    inner class MyLinkTabViewHolder(val binding: ContainerMySavedLinksBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(post: SavedPosts) {
            vm.getImageFromURL(binding.ivThumbnail, post.postLink!!, binding.ivShimmerThumb)


            if (post.postCaption.isNullOrEmpty()) {
                binding.tvCaption.visibility = View.GONE
            } else {
                binding.tvCaption.visibility = View.VISIBLE
            }

            binding.ivPopup.setOnClickListener {
                val postId = post.postID
                openPopUp(it, postId)
            }

            binding.apply {
                tvTitle.text = post.postTitle
                tvCaption.text = post.postCaption
                tvUserName.text = "Posted by ${post.userName}"
                tvLink.text = "${post.postLink}"


                Glide.with(ivUserPhoto.context)
                    .load(post.userPhotoUrl)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivUserPhoto)
            }

            binding.constraintMain.setOnClickListener {
                var link = post.postLink!!
                if (!checkIfYoutube(it, link)) {
                    val intent =
                        Intent((it.context as LinkMainActivity), WebViewActivity::class.java)
                    intent.putExtra("url", link.trim())
                    it.context.startActivity(intent)
                }
            }

        }

    }

    private fun openPopUp(v: View, postId: String) {
        val popUp = PopupMenu(v.context, v)
        popUp.inflate(R.menu.my_link_menu)
        popUp.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteLink -> {
                    vm.deleteSavedPost(postId)
                    true
                }
                else -> true
            }
        }
        popUp.show()
    }

    private fun checkIfYoutube(v: View, link: String): Boolean {
        if (link.contains("youtu.be") || link.contains("youtube.com")) {
            v.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
            return true
        }
        return false
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
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

class MySavedLinkDiffUtil() : DiffUtil.ItemCallback<SavedPosts>() {

    override fun areItemsTheSame(oldItem: SavedPosts, newItem: SavedPosts): Boolean {
        return oldItem.postID == newItem.postID
    }

    override fun areContentsTheSame(oldItem: SavedPosts, newItem: SavedPosts): Boolean {
        return oldItem == newItem
    }

}