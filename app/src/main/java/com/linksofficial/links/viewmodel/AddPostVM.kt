package com.linksofficial.links.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPostVM(val context: Application, private val mainRepo: MainRepository) :
    AndroidViewModel(context) {

    var getUserDetails = MutableLiveData<Post>()
    
    private var _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User>
        get() = _userDetails

    private var _tagPosition = MutableLiveData(0)
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

    fun goToPostVis(v: View) {
        (v.context as LinkMainActivity).findNavController(R.id.home_nav_host)
            .navigate(R.id.action_addPostFragment_to_postVisibilityBottomSheet)
    }

}