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
import com.linksofficial.links.databinding.FragmentMyAccountBinding


class MyAccountFragment() : Fragment() {

    private lateinit var binding: FragmentMyAccountBinding

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

        val auth = Firebase.auth
        val link = auth.currentUser?.photoUrl

        binding.ivProfileImage.load(link){
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountFragment_to_editProfileFragment)
        }

        binding.ivLogout.setOnClickListener {
            Firebase.auth.signOut()
        }

    }

}