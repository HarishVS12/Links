package com.linksofficial.links.view.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.view.ui.feed.FeedObjectFragment
import timber.log.Timber

class FeedVPAdapter(fragments: Fragment): FragmentStateAdapter(fragments) {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = ConstantsHelper.getTagList().size

    override fun createFragment(position: Int): Fragment {
        val fragment = FeedObjectFragment()
        Timber.d("FeedObjPositions(VPADAPTER) = $position")
        fragment.arguments = Bundle().apply {
            putInt(ConstantsHelper.FEED_VP_ARG,position)
        }
        return fragment
    }



}