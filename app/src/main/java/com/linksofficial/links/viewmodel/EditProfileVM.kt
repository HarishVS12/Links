package com.linksofficial.links.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import kotlinx.coroutines.launch

class EditProfileVM(private val mainRepo: MainRepository) : ViewModel() {

    private var _userDetails = MutableLiveData<User?>()
    val userDetails: LiveData<User?>
        get() = _userDetails

    fun updateUserDetails(map: HashMap<String, Any?>) {
        mainRepo.updateUserDetails(Firebase.auth.currentUser?.uid, map)
    }

    fun writeUserDetail(user: User) {
        viewModelScope.launch {
            mainRepo.writeUserDetails(user)
        }
    }

    fun updateUserLD(user:User?){
        _userDetails.value = user!!
    }


    fun uploadImageToStorage(
        currentUser: FirebaseUser,
        imageURI: Uri?
    ) {
        viewModelScope.launch {
            val imageRef = Firebase.storage.reference.child(currentUser.uid ?: "NoUID")
                .child(currentUser?.email.toString())
            var uploadTask = imageRef.putFile(imageURI!!)

            uploadTask.continueWithTask {
                if (!it.isSuccessful) {
                    it.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    updateUserDetails(hashMapOf("photo_url" to downloadUri.toString()))
                    writeUserDetail(User(photo_url = downloadUri.toString()))
                }
            }
        }
    }

}