package com.linksofficial.links.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPostVM(val context: Application, private val mainRepo: MainRepository) :
    AndroidViewModel(context) {

    private var _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User>
        get() = _userDetails

    private var _tagPosition = MutableLiveData<Int>()
    val tagPosition: LiveData<Int>
        get() = _tagPosition

    private var _postDetails = MutableLiveData<Post>()
    val postDetails: LiveData<Post>
        get() = _postDetails

    fun postPosition(position: Int) {
        _tagPosition.value = position
    }

    fun readUserDetail() {
        _userDetails = mainRepo.readUserDetails().asLiveData() as MutableLiveData<User>
    }

    fun postLink(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = Firebase.auth.currentUser?.uid
            post.user_id = uid
            mainRepo.postLink(context, uid, post)
        }
    }


}