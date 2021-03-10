package com.linksofficial.links.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.data.repository.MainRepository
import com.linksofficial.links.utils.ConstantsHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MyLinkVM(private val mainRepo: MainRepository) : ViewModel() {


    private var _postDetails = MutableLiveData<MutableList<Post>>()
    val postDetails: LiveData<MutableList<Post>>
        get() = _postDetails

    private var _imageURL = MutableLiveData<String>()
    val imageURL: LiveData<String>
        get() = _imageURL


    val readAllLocalPosts = mainRepo.readAllSavedPosts.asLiveData()

    //Delete Saved Post
    fun deleteSavedPost(postId: String) {
        viewModelScope.launch {
            mainRepo.deleteSavedPost(postId)
        }
    }

    //Get Link
    fun getAllPosts() {
        var i = 0
        val database = Firebase.firestore
        val docRef = database.collection(ConstantsHelper.POST)
        val source = Source.CACHE
        docRef
            .whereEqualTo("user_id", Firebase.auth.currentUser?.uid)
            .get(source)
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
    fun getImageFromURL(v: ImageView, url: String, shimmer: ShimmerFrameLayout) {
        viewModelScope.launch(Dispatchers.IO) {
            var imageURL = mainRepo?.getImageFromURL(url)
            withContext(Dispatchers.Main) {
                if (imageURL.isNullOrBlank()) {
                    v.visibility = View.GONE
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

    //Delete the post
    private fun deletePost(context: Context, document: String) {
        viewModelScope.launch {
            mainRepo.deletePost(context, document)
        }
        getAllPosts()
    }

    fun openPopUp(v: View, document: String, isPublic: Boolean) {
        val popUp = PopupMenu(v.context, v)
        popUp.inflate(R.menu.my_link_menu)
        popUp.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteLink -> {
                    Timber.d("PopupShow = Delete clicked")
                    alert(v.context, document, if (isPublic) "public" else "private")
                    true
                }
                else -> true
            }
        }
        popUp.show()
    }

    private fun alert(context: Context, document: String, isPublic: String) {
        val builder = AlertDialog.Builder(context)
            .setTitle("Are you sure?")
            .setMessage("It is a $isPublic post. Do you want to delete this post?")
            .setPositiveButton("Yes") { _, _ ->
                deletePost(context, document)
            }
            .setNegativeButton("Nope") { _, _ -> }
        builder.show()
    }


}