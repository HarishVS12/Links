package com.linksofficial.links.view.ui.addPost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentPostVisibilityBottomSheetBinding

class PostVisibilityBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPostVisibilityBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostVisibilityBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetBgTheme
    }

}