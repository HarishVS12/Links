package com.linksofficial.links.view.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.linksofficial.links.data.model.Tags
import com.linksofficial.links.databinding.ContainerFeedBinding
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.view.adapter.FeedContainerAdapter

class FeedObjectFragment() : Fragment() {

    private lateinit var binding: ContainerFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContainerFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ConstantsHelper.FEED_VP_ARG) }.apply {
            this?.getInt(ConstantsHelper.FEED_VP_ARG)?.let {
                val tagName = getList()[it]
                tagName.tagName?.let { it1 -> updateUI(it1) }
            }

        }

        binding.recyclerView.adapter = FeedContainerAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }


    private fun updateUI(tagText: String) {
        binding.tvTag.text = tagText
    }

    private fun getList() =
        mutableListOf(
            Tags("Technology"),
            Tags("Science"),
            Tags("Space"),
            Tags("Politics"),
            Tags("Sports"),
            Tags("Cinema"),
            Tags("Entertainment"),
            Tags("Music"),
            Tags("Cricket")
        )


}