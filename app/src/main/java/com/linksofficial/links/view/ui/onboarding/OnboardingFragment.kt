package com.linksofficial.links.view.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentOnboardingBinding
import com.linksofficial.links.view.adapter.OnboardingAdapter
import com.linksofficial.links.viewmodel.LinkActivityVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


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

    private fun clickListeners(){
        binding.tvSkip.setOnClickListener { setCurrentPosition(2) }
        binding.tvNext.setOnClickListener { setCurrentPosition(binding.viewPager.currentItem + 1) }
        binding.btnProceed.setOnClickListener {
            onBoardingVM.writeFirstAppOpen(true)
            findNavController().navigate(R.id.action_onboardingFragment_to_homeFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        onBoardingVM.readFirstAppOpen().observe(this,{
            if(it)
                findNavController().navigate(R.id.action_onboardingFragment_to_homeFragment)
        })
    }

    private fun updateUI(linearVisibility: Int, buttonVisibility: Int) {
        binding.linearLayoutInternal.visibility = linearVisibility
        binding.btnProceed.visibility = buttonVisibility
    }

    private fun initVP() {
        binding.viewPager.adapter = OnboardingAdapter(this)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                when (position) {
                    0 -> {
                        updateUI(View.VISIBLE, View.GONE)
                        binding.ivSub.setImageResource(R.drawable.ic_onboarding_sub1)
                        Timber.d("${findNavController().currentBackStackEntry?.destination}")
                    }
                    1 -> {
                        binding.ivSub.setImageResource(R.drawable.ic_onboarding_sub2)
                        updateUI(View.VISIBLE, View.GONE)
                        Timber.d("${findNavController().currentBackStackEntry}")
                    }
                    2 -> {
                        updateUI(View.GONE, View.VISIBLE)
                        Timber.d("${findNavController().currentBackStackEntry}")
                    }

                }
            }
        })
    }


    private fun setCurrentPosition(position: Int) {
        binding.viewPager.setCurrentItem(position, true)
    }

    private fun handleBackPress(){
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(binding.viewPager.currentItem==0)
                    activity?.finish()
                binding.viewPager.setCurrentItem(binding.viewPager.currentItem-1,true)
            }
        })

    }

}