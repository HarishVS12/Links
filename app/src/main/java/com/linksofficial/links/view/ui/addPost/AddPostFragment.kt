package com.linksofficial.links.view.ui.addPost

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.Timestamp
import com.linksofficial.links.R
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.databinding.FragmentAddPostBinding
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.utils.NetworkHelper
import com.linksofficial.links.view.ui.activities.LinkMainActivity
import com.linksofficial.links.viewmodel.AddPostVM
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPostFragment : Fragment() {

    private lateinit var binding: FragmentAddPostBinding
    private val addPostViewModel: AddPostVM by viewModel()

    private val feedLinkArgs by navArgs<AddPostFragmentArgs>()

    private var isPublic = true

    private var userName = ""
    private var userPhotoURL = ""

    private var selectedTagName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddPostBinding.inflate(inflater)
        addPostViewModel.readUserDetail()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readUserDetails()
        binding.apply {
            vm = addPostViewModel
            lifecycleOwner = viewLifecycleOwner
        }



        onClicks()
        observePostStatus()
        checkBoxInit()
        handleBackPress()
    }

    private fun onClicks() {
        binding.btnAddPost.setOnClickListener { addPost(it) }
        binding.ivCheckPost.setOnClickListener { addPost(it) }
        binding.ivBack.setOnClickListener { findNavController().popBackStack() }

        binding.constraintTags.setOnClickListener {
            findNavController().navigate(R.id.action_addPostFragment_to_addTagBottomSheet)
        }

    }


    private fun handleBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (feedLinkArgs.isIntent)
                        requireActivity().finish()
                    else
                        findNavController().popBackStack()
                }
            })
    }

    override fun onResume() {
        super.onResume()
        observeTagName()

        if (!feedLinkArgs.isIntent) {
            binding.etLink.setText(feedLinkArgs.link ?: "")
            checkClipData()
        } else {
            checkClipData()
            binding.etLink.setText(feedLinkArgs.link ?: "")
        }
    }

    private fun checkBoxInit() {
        updateCheckBox(false)
        binding.checkbox.isChecked = false
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                updateCheckBox(false)
                binding.apply {
                    textinputTitle.visibility = View.GONE
                    etTitle.visibility = View.GONE
                    textinputCaption.visibility = View.GONE
                    etCaption.visibility = View.GONE
                }
            } else {
                updateCheckBox(true)
                binding.apply {
                    textinputTitle.visibility = View.VISIBLE
                    etTitle.visibility = View.VISIBLE
                    textinputCaption.visibility = View.VISIBLE
                    etCaption.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updateCheckBox(isEnabled: Boolean) {
        binding.apply {
            textinputTitle.isEnabled = isEnabled
            etTitle.isEnabled = isEnabled
            textinputCaption.isEnabled = isEnabled
            etCaption.isEnabled = isEnabled
        }
    }

    private fun addPost(v: View) {
        if (NetworkHelper(requireActivity()).isNetConnected())
            checkSubmission(v)
        else
            Toasty.error(
                requireActivity(),
                "Please switch on your internet",
                Toasty.LENGTH_LONG
            )
                .show()
    }


    private fun readUserDetails() {
        addPostViewModel.userDetails.observe(viewLifecycleOwner, {
            userName = it.username ?: ""
            userPhotoURL = it.photo_url ?: ""
        })
    }

    private fun checkSubmission(v: View) {
        val etLink = binding.etLink.text
        val etTitle = binding.etTitle.text.toString()
        val etCaption = binding.etCaption.text.toString()

        if (!Patterns.WEB_URL.matcher(etLink)
                .matches() && etTitle.isNullOrBlank() && binding.etTitle.isEnabled
        ) {
            binding.textinputTitle.error = getString(R.string.enter_title)
            binding.textinputLink.error = getString(R.string.enter_url)
        } else {

            if (Patterns.WEB_URL.matcher(etLink).matches()) {
                binding.textinputLink.error = null
                if (etTitle.isNullOrEmpty() && binding.etTitle.isEnabled) {
                    binding.textinputTitle.error = getString(R.string.enter_title)
                } else {
                    binding.textinputTitle.error = null
                    binding.cardProgress.visibility = View.VISIBLE

                    if (!binding.etTitle.isEnabled) {
                        if (binding.chip.visibility == View.VISIBLE)
                            fetchTitleAndCaption(etLink.toString())
                        else {
                            Toasty.error(
                                requireContext(),
                                "Please select a tag",
                                Toasty.LENGTH_SHORT
                            ).show()
                            binding.cardProgress.visibility = View.INVISIBLE
                        }

                    } else {
                        if (binding.chip.visibility == View.VISIBLE)
                            postLink(etLink, etTitle, etCaption)
                        else {
                            Toasty.error(
                                requireContext(),
                                "Please select a tag",
                                Toasty.LENGTH_SHORT
                            ).show()
                            binding.cardProgress.visibility = View.INVISIBLE
                        }
                    }
                }

            } else {
                binding.textinputTitle.error = null
                binding.textinputLink.error = getString(R.string.enter_url)
            }
        }
    }

    private fun fetchTitleAndCaption(url: String) {
        addPostViewModel.getTitleAndCaption(url)
        lifecycleScope.launch {
            delay(timeMillis = 500)
            addPostViewModel.linkProperties.observe(viewLifecycleOwner, {
                postLink(url, it.title ?: "", it.description)
            })
        }
    }

    private fun postLink(etLink: CharSequence?, etTitle: String, etCaption: String? = null) {
        val post = Post(
            user_name = userName,
            user_photo_url = userPhotoURL,
            link = etLink.toString(),
            tag = selectedTagName,
            title = etTitle,
            caption = etCaption,
            created_at = Timestamp.now(),
            id = Timestamp.now().seconds.toString(),
            is_public = isPublic
        )
        addPostViewModel.postLink(post)
        if (feedLinkArgs.isIntent) {
            requireActivity().finish()
        } else {
            findNavController().popBackStack()
        }
    }

    private fun checkClipData() {
        val clipBoard =
            (context as LinkMainActivity).getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipBoard.hasPrimaryClip()) {
            val primaryClip = clipBoard.primaryClip?.getItemAt(0)
            val copiedText = primaryClip?.text

            if (Patterns.WEB_URL.matcher(copiedText).matches()) {
                binding.etLink.setText(copiedText)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        addPostViewModel.writeLinkCopied(false)
    }

    // region ObservePostStatus and ObserveTagName
    private fun observePostStatus() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            ConstantsHelper.POST_STATUS
        )?.observe(viewLifecycleOwner, {
            if (it) {
                isPublic = it
                postStatusVisibility(R.string.status_public, R.drawable.ic_add_link_public)
            } else {
                isPublic = it
                postStatusVisibility(R.string.status_private, R.drawable.ic_add_link_private)
            }
        })
    }

    private fun observeTagName() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            ConstantsHelper.TAG_NAME
        )?.observe(viewLifecycleOwner, {
            binding.chip.text = it
            selectedTagName = it
            binding.chip.visibility = View.VISIBLE
        })
    }

    private fun postStatusVisibility(postStatus: Int, imageStatus: Int) {
        binding.apply {
            tvPostStatus.text = getString(postStatus)
            ivStatus.setImageResource(imageStatus)
        }
    }
    //endregion

}