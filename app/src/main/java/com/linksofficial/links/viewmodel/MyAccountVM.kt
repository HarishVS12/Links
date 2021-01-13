package com.linksofficial.links.viewmodel

import androidx.lifecycle.*
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import kotlinx.coroutines.launch

class MyAccountVM(private val mainRepo: MainRepository) : ViewModel() {


    private var _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User>
        get() = _userDetails

    fun writeUserDetail(user: User) {
        viewModelScope.launch {
            mainRepo.writeUserDetails(user)
        }
    }

    fun readUserDetail(): LiveData<User> =
        mainRepo.readUserDetails().asLiveData()

    fun writeUserLD(user:User?){
        _userDetails.value = user!!
    }




}