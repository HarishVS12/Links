package com.linksofficial.links.view.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.data.model.User
import com.linksofficial.links.databinding.FragmentMyAccountBinding
import com.linksofficial.links.viewmodel.MyAccountVM
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyAccountFragment() : Fragment() {

    private lateinit var binding: FragmentMyAccountBinding
    private val myAccountVM: MyAccountVM by viewModel()


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
        myAccountVM.getUserDetails(Firebase.auth.currentUser?.uid)

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
            myAccountVM.userDetails?.observe(viewLifecycleOwner,{
                val action = MyAccountFragmentDirections.actionMyAccountFragmentToEditProfileFragment(it)
                findNavController().navigate(action)
            })
        }

        binding.ivLogout.setOnClickListener {
            Firebase.auth.signOut()
        }

    }

}