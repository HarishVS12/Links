package com.linksofficial.links.view.ui.editProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.linksofficial.links.databinding.FragmentEditProfileBinding
import com.linksofficial.links.view.ui.home.fragments.MyAccountFragmentArgs
import com.linksofficial.links.viewmodel.EditProfileVM
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileVm: EditProfileVM by viewModel()

    private val args: MyAccountFragmentArgs by navArgs()

    private lateinit var getContent:ActivityResultLauncher<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*getContent = requireActivity().registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            Toast.makeText(requireActivity(), "$uri", Toast.LENGTH_SHORT).show()
        }*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            vm = editProfileVm

            lifecycleOwner = this@EditProfileFragment

            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }

            args.apply {
                editProfileVm._userDetails.value = this.userDetails
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
}