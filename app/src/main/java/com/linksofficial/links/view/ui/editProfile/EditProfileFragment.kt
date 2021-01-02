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
import com.linksofficial.links.R


class EditProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.iv_profile_image).load(
            FirebaseAuth.getInstance().currentUser?.photoUrl
        ){
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        view.findViewById<ImageView>(R.id.iv_back).setOnClickListener {
            findNavController().popBackStack()
        }

    }

}