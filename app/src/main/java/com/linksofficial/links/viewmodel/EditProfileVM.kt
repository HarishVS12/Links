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

    var _userDetails = MutableLiveData<User?>()
    val userDetails: LiveData<User?>
        get() = _userDetails

    fun updateUserDetails(map: HashMap<String, Any?>) {
        mainRepo.updateUserDetails(Firebase.auth.currentUser?.uid, map)
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