package com.linksofficial.links.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        onDestinationChange()
    }

    private fun init() {
        navController = findNavController(requireActivity(), R.id.home_nav_host)
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun onDestinationChange() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.feedFragment, R.id.myAccountFragment, R.id.myLinkFragment -> updateUI(View.VISIBLE)
                R.id.editProfileFragment, R.id.addPostFragment -> updateUI(View.GONE)
            }
        }
    }

    private fun updateUI(visibility: Int) {
        binding.bottomNav.visibility = visibility
    }

}