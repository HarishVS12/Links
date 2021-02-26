package com.linksofficial.links.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import com.linksofficial.links.R
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import com.linksofficial.links.utils.Share
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import com.linksofficial.links.view.ui.home.bottomNav.MyAccountFragmentDirections

class MyAccountVM(private val mainRepo: MainRepository) : ViewModel() {


    private var _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User>
        get() = _userDetails

    fun readUserDetail(): LiveData<User> =
        mainRepo.readUserDetails().asLiveData()

    fun writeUserLD(user: User?) {
        _userDetails.value = user!!
    }

    fun gotoEditProfile(v: View) {
        val action =
            MyAccountFragmentDirections.actionMyAccountFragmentToEditProfileFragment(userDetails.value)
        (v.context as LinkMainActivity).findNavController(R.id.home_nav_host).navigate(action)
    }


    fun shareCommon(v: View, shareProp: Int) {
        when (shareProp) {
            0 -> Share.shareApp((v.context as LinkMainActivity))
            1 -> Share.contactUs((v.context as LinkMainActivity))
            2 -> Share.rateApp((v.context as LinkMainActivity))
        }
    }


}