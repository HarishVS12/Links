package com.linksofficial.links.view.ui.addPost

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.linksofficial.links.R
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.databinding.FragmentAddPostBinding
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.utils.NetworkHelper
import com.linksofficial.links.view.adapter.TagsAddPostAdapter
import com.linksofficial.links.viewmodel.AddPostVM
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddPostFragment : Fragment() {

    private lateinit var binding: FragmentAddPostBinding
    private lateinit var tagAdapter: TagsAddPostAdapter

    private val addPostViewModel: AddPostVM by viewModel()

    private var isPublic = true

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
        binding.apply {
            vm = addPostViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        tagAdapter = TagsAddPostAdapter(addPostViewModel)

        binding.rvTags.apply {
            adapter = tagAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
        }

        binding.btnAddPost.setOnClickListener {
            if(NetworkHelper(requireActivity()).isNetConnected())
                checkSubmission(it)
            else
                Toasty.error(requireActivity(),"Please switch on your internet",Toasty.LENGTH_LONG)
                    .show()
        }

        binding.cardStatus.setOnClickListener {
            findNavController().navigate(R.id.action_addPostFragment_to_postVisibilityBottomSheet)
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        tagAdapter.submitList(ConstantsHelper.getTagList())

        observePostStatus()

    }

    fun checkSubmission(v: View) {
        val etLink = binding.etLink.text
        val etTitle = binding.etTitle.text.toString()
        val etCaption = binding.etCaption.text.toString()

        if (!Patterns.WEB_URL.matcher(etLink).matches() && etTitle.isNullOrBlank()) {
            binding.textinputTitle.error = "Please enter the title"
            binding.textinputLink.error = "Please enter a valid URL"
        }else {

            if (Patterns.WEB_URL.matcher(etLink).matches()) {
                binding.textinputLink.error = null
                if (etTitle.isNullOrEmpty()) {
                    binding.textinputTitle.error = "Please enter the title"
                } else {
                    binding.textinputTitle.error = null
                    postLink(etLink, etTitle, etCaption)
                }

            } else {
                binding.textinputTitle.error = null
                binding.textinputLink.error = "Please enter a valid URL"
            }
        }
    }

    private fun postLink(etLink: CharSequence?, etTitle: String, etCaption: String? = null) {
        val post = Post(
            link = etLink.toString().toLowerCase(),
            tag = ConstantsHelper.getTagList()[addPostViewModel.tagPosition.value!!].tagName,
            title = etTitle,
            caption = etCaption,
            created_at = Timestamp.now(),
            id = Timestamp.now().seconds.toString(),
            is_public = isPublic
        )
        addPostViewModel.postLink(post)
        findNavController().popBackStack()
    }


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

    private fun postStatusVisibility(postStatus: Int, imageStatus: Int) {
        binding.apply {
            tvPostStatus.text = getString(postStatus)
            ivStatus.setImageResource(imageStatus)
        }
    }

}