package com.linksofficial.links.view.ui.addPost

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentAddTagBottomSheetBinding
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.viewmodel.PostVisibilityVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTagBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddTagBottomSheetBinding

    private val postVisibilityVM: PostVisibilityVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTagBottomSheetBinding.inflate(inflater)
        binding.apply {
            vm = postVisibilityVM
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postVisibilityVM.postStatus.observe(viewLifecycleOwner, {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                ConstantsHelper.POST_STATUS,
                it
            )
            findNavController().popBackStack()
        })

        ConstantsHelper.getTagList().forEach {
            val chip = Chip(requireActivity())
            chip.text = (it.tagName)
            chip.setTextColor(Color.WHITE)
            chip.chipBackgroundColor = resources.getColorStateList(R.color.primaryColor)
            chip.setOnClickListener {
                val chipText = chip.text
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    ConstantsHelper.TAG_NAME,
                    chipText
                )
                findNavController().popBackStack()
            }
            binding.chipGroup.addView(chip)
        }


    }


    override fun getTheme(): Int {
        return R.style.BottomSheetBgTheme
    }

}