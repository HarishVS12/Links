package com.linksofficial.links.view.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.linksofficial.links.R
import com.linksofficial.links.databinding.ContainerOnboardingBinding
import com.linksofficial.links.utils.ConstantsHelper

class OnboardingObjectFragment() : Fragment() {

    private lateinit var binding: ContainerOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContainerOnboardingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ConstantsHelper.ONBOARDING_VP_ARG) }.apply {
            when (this?.getInt(ConstantsHelper.ONBOARDING_VP_ARG)) {
                1 -> updateUI(
                    getString(R.string.onboard_main_1), getString(R.string.onboard_sub_1),
                    R.drawable.ic_onboarding_main1
                )

                2 -> updateUI(
                    getString(R.string.onboard_main_2), getString(R.string.onboard_sub_2),
                    R.drawable.ic_onboarding_main2
                )

                3 -> updateUI(
                    getString(R.string.onboard_main_3), getString(R.string.onboard_sub_3),
                    R.drawable.ic_onboarding_main3
                )
            }
        }
    }

    private fun updateUI(titleText: String, subText: String, mainImage: Int) {
        binding.tvTitle.text = titleText
        binding.tvSubtitle.text = subText
        binding.ivMain.setImageResource(mainImage)
    }


}