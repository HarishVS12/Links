package com.linksofficial.links.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import com.linksofficial.links.utils.ConstantsHelper
import timber.log.Timber

class EditProfileVM(private val mainRepo: MainRepository) : ViewModel() {

    private var username = MutableLiveData<String?>()
    val _username: LiveData<String?>
        get() = username

    private var bio = MutableLiveData<String?>()
    val _bio: LiveData<String?>
        get() = bio

    private var tags = MutableLiveData<String?>()
    val _tags: LiveData<String?>
        get() = tags

    private var image_url = MutableLiveData<String?>()
    val _image_url: LiveData<String?>
        get() = image_url

    fun updateUserDetails(map:HashMap<String,Any?>){
        mainRepo.updateUserDetails(Firebase.auth.currentUser?.uid,map)
    }


    fun getUserDetails(uniqueId: String?) {
        val database = Firebase.firestore
        var user: User? = null
        database.collection(ConstantsHelper.USER)
            .document(uniqueId.toString())
            .get()
            .addOnSuccessListener { doc ->
                user = doc.toObject<User>()
                username.postValue(user?.username)
                bio.postValue(user?.bio)
                tags.postValue(user?.favorite_tags)
                image_url.postValue(user?.photo_url)
                Timber.i(user?.photo_url)
            }
            .addOnFailureListener {
                Timber.e(it.printStackTrace().toString())
            }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:setImage")
        fun setImage(view: ImageView, imageUrl: String?) {
            Timber.i("ImageUrl: $imageUrl")
            view.load(imageUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

}