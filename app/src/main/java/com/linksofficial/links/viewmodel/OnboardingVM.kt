package com.linksofficial.links.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.linksofficial.links.data.repository.MainRepository
import kotlinx.coroutines.launch

class OnboardingVM(private val mainRepo: MainRepository): ViewModel() {

    fun readFirstAppOpen():LiveData<Boolean>{
        return mainRepo.readFirstAppOpen().asLiveData()
    }

    fun writeFirstAppOpen(isFirstAppOpen: Boolean){
        viewModelScope.launch {
            mainRepo.writeFirstAppOpen(isFirstAppOpen)
        }
    }

}