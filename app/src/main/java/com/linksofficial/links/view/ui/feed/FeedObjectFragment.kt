package com.linksofficial.links.view.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.linksofficial.links.databinding.ContainerFeedBinding
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.view.adapter.FeedContainerAdapter
import com.linksofficial.links.viewmodel.FeedVM
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FeedObjectFragment() : Fragment() {

    private lateinit var binding: ContainerFeedBinding
    private val feedVM: FeedVM by viewModel()
    private lateinit var adapter: FeedContainerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContainerFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = FeedContainerAdapter(feedVM)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())



        arguments?.takeIf { it.containsKey(ConstantsHelper.FEED_VP_ARG) }.apply {
            this?.getInt(ConstantsHelper.FEED_VP_ARG)?.let {
                Timber.d("Posts:(POS) = $it")
                val tagName = ConstantsHelper.getTagList()[it]
                tagName.tagName?.let { it1 -> updateUI(it1) }
            }

        }

        feedVM.postList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

    }


    private fun updateUI(tagText: String) {
        Timber.d("Posts:(TEXT) = $tagText")
        feedVM.getPostFromRemote(tagText)
    }

}