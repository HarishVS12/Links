package com.linksofficial.links.view.ui.editProfile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.linksofficial.links.data.model.User
import com.linksofficial.links.databinding.FragmentEditProfileBinding
import com.linksofficial.links.view.ui.home.fragments.MyAccountFragmentArgs
import com.linksofficial.links.viewmodel.EditProfileVM
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileVm: EditProfileVM by viewModel()


    private val args: MyAccountFragmentArgs by navArgs()

    private var auth: FirebaseAuth? = null
    private var uid: String? = null
    private var currentUser: FirebaseUser? = null

    private var imageURI: Uri? = null

    private var map = hashMapOf<String, Any?>()

    private lateinit var storageReference: StorageReference

    private lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        currentUser = auth?.currentUser
        uid = currentUser?.uid.toString()

        storageReference = Firebase.storage.reference.child(uid ?: "NoUID")

        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageURI = uri
            binding.ivProfileImage.load(uri) {
                transformations(CircleCropTransformation())
            }
        }
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
                editProfileVm.updateUserLD(this.userDetails)
            }

            btnSaveProfile.setOnClickListener {

                if (imageURI != null) {
                    editProfileVm.uploadImageToStorage(currentUser!!, imageURI)
                    editProfileVm.writeUserDetail(createUser(currentUser?.uid, currentUser))
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("userModel",createUser(currentUser?.uid, currentUser))
                } else {
                    editProfileVm.writeUserDetail(createUserWOphURL(currentUser?.uid, currentUser))
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("userModel",createUserWOphURL(currentUser?.uid, currentUser)  )
                }
                updateUserDetails()
                findNavController().popBackStack()
            }

            ivProfileImage.setOnClickListener {
                getContent.launch("image/*")
            }

        }
    }

    private fun updateUserDetails() {
        map.apply {
            put("username", binding.etUsername.text.toString())
            put("bio", binding.etBio.text.toString())
            put("favorite_tags", binding.etTags.text.toString())
        }
        editProfileVm.updateUserDetails(map)
    }

    private fun createUser(uniqueId: String?, user: FirebaseUser?): User =
        User(
            uniqueId,
            binding.etUsername.text.toString(),
            user?.email,
            bio = binding.etBio.text.toString(),
            photo_url = imageURI.toString(),
            favorite_tags = binding.etTags.text.toString()
        )

    private fun createUserWOphURL(uniqueId: String?, user: FirebaseUser?): User =
        User(
            uniqueId,
            binding.etUsername.text.toString(),
            user?.email,
            bio = binding.etBio.text.toString(),
            favorite_tags = binding.etTags.text.toString()
        )


}