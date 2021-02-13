package com.linksofficial.links.viewmodel

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.utils.LinkPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class FeedVM() : ViewModel() {

    private var _postItem = MutableLiveData<Post>()
    val postItem: LiveData<Post>
        get() = _postItem

    private var _focusTagPosition = MutableLiveData<Int>()
    val focusTagPosition: LiveData<Int>
        get() = _focusTagPosition

    fun setPostItem(postItem: Post) {
        _postItem.setValue(postItem)
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

    fun getImageFromURL(v: ImageView, url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var getImageUrl = ""
            try {
                getImageUrl = LinkPreview(url).getImageUrl()
                Timber.d("imURL: $getImageUrl")
            } catch (exception: Exception) {
                Log.d("Exception", "getImageFromURL: $exception")
            }
            withContext(Dispatchers.Main) {
                if (getImageUrl.isNullOrBlank()) {
                    v.setImageResource(R.drawable.ic_icon_links)
                } else {
//                    v.load(getImageUrl)
                    Glide.with(v.context)
                        .load(getImageUrl)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(v)
                }
            }

        }
    }

}