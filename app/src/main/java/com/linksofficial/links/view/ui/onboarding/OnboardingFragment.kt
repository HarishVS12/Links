package com.linksofficial.links.view.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentOnboardingBinding
import com.linksofficial.links.view.adapter.OnboardingVPAdapter
import com.linksofficial.links.viewmodel.LinkActivityVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val onBoardingVM: LinkActivityVM by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVP()
        clickListeners()
        handleBackPress()

    }

    private fun clickListeners() {
        binding.tvSkip.setOnClickListener { setCurrentPosition(2) }
        binding.tvNext.setOnClickListener { setCurrentPosition(binding.viewPager.currentItem + 1) }
        binding.btnProceed.setOnClickListener {
            onBoardingVM.writeFirstAppOpen(true)
            findNavController().navigate(R.id.action_onboardingFragment_to_homeFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        onBoardingVM.readFirstAppOpen().observe(this, {
            if (it)
                findNavController().navigate(R.id.action_onboardingFragment_to_homeFragment)
        })
    }

    private fun updateUI(linearVisibility: Int, buttonVisibility: Int, subImage: Int) {
        binding.linearLayoutInternal.visibility = linearVisibility
        binding.btnProceed.visibility = buttonVisibility
        binding.ivSub.setImageResource(subImage)
    }

    private fun initVP() {
        binding.viewPager.adapter = OnboardingVPAdapter(this)
        binding.viewPager.offscreenPageLimit = 2

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                when (position) {
                    0 -> updateUI(View.VISIBLE, View.GONE, R.drawable.ic_onboarding_sub1)
                    1 -> updateUI(View.VISIBLE, View.GONE, R.drawable.ic_onboarding_sub2)
                    2 -> updateUI(View.GONE, View.VISIBLE, R.drawable.ic_onboarding_sub3)
                }
            }
        })
    }


    private fun setCurrentPosition(position: Int) {
        binding.viewPager.setCurrentItem(position, true)
    }

    private fun handleBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.viewPager.currentItem == 0)
                        activity?.finish()
                    binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1, true)
                }
            })

    }

}