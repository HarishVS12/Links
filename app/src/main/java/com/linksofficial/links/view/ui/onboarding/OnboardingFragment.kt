package com.linksofficial.links.view.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentOnboardingBinding
import com.linksofficial.links.view.adapter.OnboardingAdapter


class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding

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
        binding.tvSkip.setOnClickListener { setCurrentPosition(2) }
        binding.tvNext.setOnClickListener { setCurrentPosition(binding.viewPager.currentItem + 1) }
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
                    }
                    1 -> {
                        binding.ivSub.setImageResource(R.drawable.ic_onboarding_sub2)
                        updateUI(View.VISIBLE, View.GONE)
                    }
                    2 -> updateUI(View.GONE, View.VISIBLE)
                }
            }
        })
    }

    private fun setCurrentPosition(position: Int) {
        binding.viewPager.setCurrentItem(position, true)
    }

}