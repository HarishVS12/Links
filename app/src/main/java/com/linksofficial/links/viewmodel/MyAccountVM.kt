package com.linksofficial.links.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import com.linksofficial.links.utils.ConstantsHelper
import timber.log.Timber

class MyAccountVM(private val mainRepo: MainRepository) : ViewModel() {

    var _username = MutableLiveData<String>()
    var _usermail = MutableLiveData<String>()
    var _userBio = MutableLiveData<String>()
    var _userImageUrl = MutableLiveData<String>()
    var _userFavTags = MutableLiveData<String>()


    fun readUserDetail(userInfo: String): LiveData<String> {
        return mainRepo.readUserDetails(userInfo).asLiveData()
    }


    fun getUserDetails(uniqueId: String?) {
        val database = Firebase.firestore
        database.collection(ConstantsHelper.USER)
            .document(uniqueId.toString())
            .get()
            .addOnSuccessListener { doc ->
                var user = doc.toObject<User>()
                _userImageUrl.postValue(user?.photo_url?:"")
            }
            .addOnFailureListener {
                Timber.e(it.printStackTrace().toString())
            }
    }


}