package com.linksofficial.links.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import com.linksofficial.links.utils.ConstantsHelper
import kotlinx.coroutines.launch
import timber.log.Timber

class LinkActivityVM(private val mainRepo: MainRepository) : ViewModel() {

    fun readFirstAppOpen(): LiveData<Boolean> {
        return mainRepo.readFirstAppOpen().asLiveData()
    }

    fun writeFirstAppOpen(isFirstAppOpen: Boolean) {
        viewModelScope.launch {
            mainRepo.writeFirstAppOpen(isFirstAppOpen)
        }
    }

    fun writeUserDetail(user: User) {
        viewModelScope.launch {
            mainRepo.writeUserDetails(user)
            Timber.d("USER:AC = $user")
        }
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