package com.linksofficial.links.view.ui.editProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentEditProfileBinding
import com.linksofficial.links.viewmodel.EditProfileVM
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileVm: EditProfileVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = editProfileVm

            lifecycleOwner = this@EditProfileFragment

            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSaveProfile.setOnClickListener {
                val map = hashMapOf<String,Any?>(
                    "username" to etUsername.text.toString(),
                    "bio" to etBio.text.toString(),
                    "favorite_tags" to etTags.text.toString()
                )
                editProfileVm.updateUserDetails(map)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        editProfileVm.getUserDetails(Firebase.auth.currentUser?.uid)
    }

}