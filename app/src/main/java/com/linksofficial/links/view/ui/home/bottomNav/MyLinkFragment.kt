package com.linksofficial.links.view.ui.home.bottomNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentMyLinkBinding
import com.linksofficial.links.view.adapter.MyLinkVPAdapter
import com.linksofficial.links.viewmodel.MyLinkVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyLinkFragment : Fragment() {

    private lateinit var binding: FragmentMyLinkBinding
    private val viewModel: MyLinkVM by sharedViewModel()

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
        viewModel.getAllPosts()
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
        binding.viewPager.adapter = MyLinkVPAdapter(this)
    }

}