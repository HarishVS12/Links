package com.linksofficial.links.view.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linksofficial.links.R
import com.linksofficial.links.data.local.model.PostLocal
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.databinding.ContainerPostFeedBinding
import com.linksofficial.links.utils.Share
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import com.linksofficial.links.view.ui.activities.WebViewActivity
import com.linksofficial.links.viewmodel.FeedVM
import es.dmoral.toasty.Toasty
import timber.log.Timber

class FeedContainerAdapter(private val feedVM: FeedVM) :
    ListAdapter<Post, FeedContainerAdapter.FeedContainerVH>(FeedContainerDiffUTIL()) {


    inner class FeedContainerVH(val binding: ContainerPostFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.vm = feedVM
        }

        fun bind(post: Post) {
            feedVM.setPostItem(post)
            feedVM.getImageFromURL(post.link ?: "", binding.ivThumbnail, binding.ivThumbShimmer)

            binding.cardPost.setOnClickListener {
                var link = post.link!!
                if (!checkIfYoutube(it, link)) {
                    val intent =
                        Intent((it.context as LinkMainActivity), WebViewActivity::class.java)
                    intent.putExtra("url", link.trim())
                    it.context.startActivity(intent)
                }
            }

            binding.frameSave.setOnClickListener {
                val postLocal = PostLocal(
                    postTitle = post?.title ?: "",
                    postCaption = post?.caption ?: "",
                    postLink = post?.link ?: "",
                    userName = post?.user_name ?: "",
                    userPhotoUrl = post?.user_photo_url ?: "",
                    postID = post?.id ?: ""
                )
                Timber.d("localDB: $postLocal")
                feedVM.postLinkLocal(postLocal)
                Toasty.success(it.context, "Saved Link Successfully!", Toasty.LENGTH_SHORT).show()
            }

            binding.frameCopy.setOnClickListener {
                val clipboard =
                    (it.context).getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Link", post?.link)
                clipboard.setPrimaryClip(clip)

                Toasty.custom(
                    it.context,
                    "Link Copied!",
                    (it.context).resources.getDrawable(R.drawable.ic_file_copy, null),
                    (it.context).resources.getColor(
                        R.color.primaryColor
                    ),
                    Color.WHITE,
                    Toasty.LENGTH_SHORT,
                    true,
                    true
                ).show()
            }

            binding.frameShare.setOnClickListener {
                post?.link?.let { it1 -> Share.shareLink((it.context as LinkMainActivity), it1) }
            }
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private fun checkIfYoutube(v: View, link: String): Boolean {
        if (link.contains("youtu.be") || link.contains("youtube.com")) {
            v.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
            return true
        }
        return false
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedContainerAdapter.FeedContainerVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerPostFeedBinding.inflate(inflater, parent, false)
        return FeedContainerVH(binding)
    }

    override fun onBindViewHolder(holder: FeedContainerAdapter.FeedContainerVH, position: Int) {
        val postItem = getItem(position)
        holder.bind(postItem)
        holder.binding.executePendingBindings()
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
