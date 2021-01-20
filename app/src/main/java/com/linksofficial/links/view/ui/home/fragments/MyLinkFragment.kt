package com.linksofficial.links.view.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentMyLinkBinding
import com.linksofficial.links.view.adapter.MyLinkFragAdapter

class MyLinkFragment : Fragment() {

    private lateinit var binding: FragmentMyLinkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyLinkBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        attachTabLayout()

    }

    private fun attachTabLayout(){

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab1_label)
                1 -> tab.text = getString(R.string.tab2_label)
            }
        }.attach()
    }

    private fun init() {
        binding.viewPager.adapter = MyLinkFragAdapter(this)
    }

}