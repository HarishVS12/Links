package com.linksofficial.links.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostVisibilityVM: ViewModel() {

    private var _postStatus = MutableLiveData<Boolean>()
    val postStatus: LiveData<Boolean>
        get() = _postStatus

     fun isPublic(status: String?){
         _postStatus.value = status=="public"
     }

}