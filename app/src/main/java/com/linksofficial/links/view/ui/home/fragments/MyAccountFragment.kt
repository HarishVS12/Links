package com.linksofficial.links.view.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.preferences.Prefs
import com.linksofficial.links.databinding.FragmentMyAccountBinding
import com.linksofficial.links.viewmodel.MyAccountVM
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MyAccountFragment() : Fragment() {

    private lateinit var binding: FragmentMyAccountBinding
    private val myAccountVM: MyAccountVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAccountVM.getUserDetails(Firebase.auth.currentUser?.uid)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readStuff()

        binding.apply {
            vm = myAccountVM
            lifecycleOwner = this@MyAccountFragment
        }

        val auth = Firebase.auth
        val link = auth.currentUser?.photoUrl

        binding.ivProfileImage.load(link){
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        binding.btnEditProfile.setOnClickListener {
            val user = User(
                username = myAccountVM._username.value,
                bio = myAccountVM._userBio.value,
                favorite_tags = myAccountVM._userFavTags.value,
                photo_url = myAccountVM._userImageUrl.value
            )
            val action = MyAccountFragmentDirections.actionMyAccountFragmentToEditProfileFragment(user)
            findNavController().navigate(action)
        }

        binding.ivLogout.setOnClickListener {
            Firebase.auth.signOut()
            requireActivity().findNavController(R.id.nav_host_frag).navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    private fun readStuff(){
        readUserDetails(Prefs.bio)
        readUserDetails(Prefs.userName)
        readUserDetails(Prefs.email)
        readUserDetails(Prefs.favTags)
        readUserDetails(Prefs.photoUrl)
    }

    private fun readUserDetails(userInfo:String){
        myAccountVM.readUserDetail(userInfo).observe(viewLifecycleOwner,{
            when(userInfo){
                Prefs.userName -> myAccountVM._username.value = it
                Prefs.bio -> myAccountVM._userBio.value = it
                Prefs.email -> myAccountVM._usermail.value = it
                Prefs.photoUrl -> {
                    myAccountVM._userImageUrl.value = it
                    Timber.d("BOYS: $it")
                }
                Prefs.favTags -> myAccountVM._userFavTags.value = it
            }
        })
    }


}