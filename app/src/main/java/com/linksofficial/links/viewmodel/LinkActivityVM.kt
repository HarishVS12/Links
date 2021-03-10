package com.linksofficial.links.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.data.local.model.PostsLocal
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import com.linksofficial.links.utils.ConstantsHelper
import kotlinx.coroutines.launch
import timber.log.Timber

open class LinkActivityVM(private val mainRepo: MainRepository) : ViewModel() {

    fun readFirstAppOpen(): LiveData<Boolean> {
        return mainRepo.readFirstAppOpen().asLiveData()
    }

    fun writeFirstAppOpen(isFirstAppOpen: Boolean) {
        viewModelScope.launch {
            mainRepo.writeFirstAppOpen(isFirstAppOpen)
        }
    }

    fun writeLinkCopied(isLinkCopied: Boolean) {
        viewModelScope.launch {
            mainRepo.writeCopiedLink(isLinkCopied)
        }
    }

    fun writeUserDetail(user: User) {
        viewModelScope.launch {
            mainRepo.writeUserDetails(user)
            Timber.d("USER:AC = $user")
        }
    }

    fun getAllPosts() {
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
                    viewModelScope.launch {
                        mainRepo.insertAllPostsLocal(convertPostObject(post))
                    }
                }
                Timber.d("Documents: $postList")
            }.addOnFailureListener {
                Timber.d("Documents failed: ${it.message}")
            }
    }

    private fun convertPostObject(post: Post): PostsLocal {
        return PostsLocal(
            post.id ?: "",
            post.user_id ?: "",
            post.user_name ?: "",
            post.user_photo_url ?: "",
            post.link ?: "",
            post.title ?: "",
            post.caption ?: "",
            post.is_public ?: false,
            post.tag ?: "",
            post.likes_count ?: 0
        )
    }


    fun getUserDetails(uniqueId: String?) {
        val database = Firebase.firestore
        database.collection(ConstantsHelper.USER)
            .document(uniqueId.toString())
            .get()
            .addOnSuccessListener { doc ->
                var user = doc.toObject<User>()
                writeUserDetail(user!!)
            }
            .addOnFailureListener {
                Timber.e(it.printStackTrace().toString())
            }

    }


}