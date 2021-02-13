package com.linksofficial.links.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linksofficial.links.data.local.model.PostLocal
import com.linksofficial.links.data.repository.MainRepository
import kotlinx.coroutines.launch
import timber.log.Timber

/**
Created by Harish on 13-02-2021
 **/
class MySavedLinkTabVM(val repo: MainRepository) : ViewModel() {


    fun postLinkLocal(postLocal: PostLocal) {
        viewModelScope.launch {
            Timber.d("localDB: VM called")
            repo.postLocalPost(postLocal)
        }
    }

}