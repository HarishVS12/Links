package com.linksofficial.links.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.linksofficial.links.view.ui.tabs.TabMyLinkFragment
import com.linksofficial.links.view.ui.tabs.TabSavedLinkFragment

class MyLinkFragAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position){
            0 -> fragment = TabMyLinkFragment()
            1 -> fragment = TabSavedLinkFragment()
        }
        return fragment!!
    }





}