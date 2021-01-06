package com.linksofficial.links.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import com.linksofficial.links.utils.ConstantsHelper
import timber.log.Timber

class MyAccountVM(private val mainRepo: MainRepository) : ViewModel() {

    private var _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User>
        get() = _userDetails


    fun getUserDetails(uniqueId: String?) {
        val database = Firebase.firestore
        database.collection(ConstantsHelper.USER)
            .document(uniqueId.toString())
            .get()
            .addOnSuccessListener { doc ->
                var user = doc.toObject<User>()
                _userDetails.postValue(user)
            }
            .addOnFailureListener {
                Timber.e(it.printStackTrace().toString())
            }
    }


}