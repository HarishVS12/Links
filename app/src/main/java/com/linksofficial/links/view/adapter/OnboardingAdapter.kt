package com.linksofficial.links.view.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.view.ui.onboarding.OnboardingObjectFragment

class OnboardingAdapter(fragments: Fragment): FragmentStateAdapter(fragments) {


    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = OnboardingObjectFragment()
        fragment.arguments = Bundle().apply {
            putInt(ConstantsHelper.ONBOARDING_ARG,position+1)
        }
        return fragment
    }



}