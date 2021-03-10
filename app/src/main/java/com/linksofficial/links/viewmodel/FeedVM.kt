package com.linksofficial.links.viewmodel

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.data.local.model.SavedPosts
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

    var lastDocSnap: DocumentSnapshot? = null

    fun setPostItem(postItem: Post) {
        _postItem.value = postItem
    }

    fun setTagPosition(position: Int) {
        _focusTagPosition.value = position
    }

    var postList = MutableLiveData<MutableList<Post>>()


    fun getPostFromRemote(tag: String) {
        val db = Firebase.firestore
        db.collection(ConstantsHelper.POST)
            .whereEqualTo("_public", true)
            .whereEqualTo("tag", tag)
            .limit(4)
            .orderBy("created_at", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    lastDocSnap = documents.documents.last()
                    val posts = mutableListOf<Post>()
                    for (post in documents) {
                        posts.add(post.toObject(Post::class.java))
                    }
                    postList.postValue(posts)
                }
            }
    }

    fun getImageFromURL(url: String, v: ImageView, shimmer: ShimmerFrameLayout) {
        viewModelScope.launch(Dispatchers.IO) {
            var imageURL = mainRepository?.getImageFromURL(url)
            withContext(Dispatchers.Main) {
                if (imageURL.isNullOrBlank()) {
                    v.visibility = View.GONE
//                    v.setImageResource(R.mipmap.ic_launcher)
                } else {
                    Timber.d("ImageUrl = $imageURL")
                    Glide.with(v.context)
                        .load(imageURL)
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

    fun postLinkLocal(savedPosts: SavedPosts) {
        viewModelScope.launch {
            Timber.d("localDB: VM called")
            mainRepository.postSavedPosts(savedPosts)
        }
    }
}