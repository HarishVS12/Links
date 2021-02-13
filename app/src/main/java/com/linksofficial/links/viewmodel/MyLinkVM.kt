package com.linksofficial.links.viewmodel

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.data.repository.MainRepository
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.utils.LinkPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MyLinkVM(val mainRepo: MainRepository) : ViewModel() {

    private var _postDetails = MutableLiveData<MutableList<Post>>()
    val postDetails: LiveData<MutableList<Post>>
        get() = _postDetails

    private var _imageURL = MutableLiveData<String>()
    val imageURL: LiveData<String>
        get() = _imageURL


    //Get Link
    fun getAllPosts() {
        var i = 0
        val database = Firebase.firestore
        val docRef = database.collection(ConstantsHelper.POST)
        val source = Source.CACHE
        docRef
            .whereEqualTo("user_id", Firebase.auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { result ->
                var postList = mutableListOf<Post>()
                for (document in result) {
                    val post = document.toObject<Post>()
                    postList.add(post)
                }
                Timber.d("Documents: $postList")
                _postDetails.postValue(postList)
            }.addOnFailureListener {
                Timber.d("Documents failed: ${it.message}")
            }
    }

    //TODO: Try catching the error
    fun getImageFromURL(v: ImageView, url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var getImageUrl = ""
            try {
                getImageUrl = LinkPreview(url).getImageUrl()
                Timber.d("imURL: $getImageUrl")
            } catch (exception: Exception) {
                /*Toast.makeText(v.context, "Couldn't pick thumbnail image", Toast.LENGTH_SHORT)
                    .show()*/
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