package com.linksofficial.links.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import kotlinx.coroutines.launch

class LoginVM(private val mainRepo: MainRepository) : ViewModel() {

    fun writeUserLogin(user: User, uniqueId: String?) {
        mainRepo.writeUserLogin(user, uniqueId)
    }

    fun writeUserDetail(user: User) {
        viewModelScope.launch {
            mainRepo.writeUserDetails(user)
        }
    }


}