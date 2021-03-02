package com.linksofficial.links.viewmodel

import android.widget.ImageView
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.data.repository.MainRepository
import com.linksofficial.links.utils.ConstantsHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class FeedVM(private val mainRepository: MainRepository) : ViewModel() {

    private var _postItem = MutableLiveData<Post>()
    val postItem: LiveData<Post>
        get() = _postItem

    private var _focusTagPosition = MutableLiveData<Int>()
    val focusTagPosition: LiveData<Int>
        get() = _focusTagPosition


    fun setPostItem(postItem: Post) {
        Timber.d("postBro : $postItem")
        _postItem.value = postItem
    }

    fun setTagPosition(position: Int) {
        _focusTagPosition.value = position
    }

    var postList = MutableLiveData<List<Post>>()

    fun getPostFromRemote(tag: String) {
        val db = Firebase.firestore
        db.collection(ConstantsHelper.POST)
            .whereEqualTo("_public", true)
            .whereEqualTo("tag", tag)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                val posts = ArrayList<Post>()
                for (post in value!!) {
                    posts.add(post.toObject(Post::class.java))
                    Timber.d("Posts: ${post.toObject(Post::class.java).tag}")
                }
                postList.postValue(posts)
            }
    }

    fun getImageFromURL(url: String, v: ImageView, shimmer: ShimmerFrameLayout) {
        viewModelScope.launch(Dispatchers.IO) {
            var imageURL = mainRepository?.getImageFromURL(url)
            withContext(Dispatchers.Main) {
                if (imageURL.isNullOrBlank()) {
//                    v.visibility = View.INVISIBLE
                    v.setImageResource(R.mipmap.ic_launcher)
                } else {
                    Glide.with(v.context)
                        .load(imageURL)
                        .thumbnail(0.5f)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(v)
                }
                shimmer.hideShimmer()
            }
        }
    }

    fun readLinkCopied() = mainRepository.readCopiedLink().asLiveData()

    fun writeLinkCopied(isLinkCopied: Boolean) {
        viewModelScope.launch {
            mainRepository.writeCopiedLink(isLinkCopied)
        }
    }

}