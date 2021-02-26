package com.linksofficial.links.viewmodel

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.linksofficial.links.R
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.repository.MainRepository
import com.linksofficial.links.utils.Share
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import com.linksofficial.links.view.ui.home.bottomNav.MyAccountFragmentDirections
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