package com.linksofficial.links.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeedVM : ViewModel() {

    private var _focusTagPosition = MutableLiveData<Int>()
    val focusTagPosition: LiveData<Int>
        get() = _focusTagPosition


    fun setTagPosition(position: Int){
        _focusTagPosition.postValue(position)
    }

}