package com.linksofficial.links.view.ui.home.bottomNav

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentFeedBinding
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.view.adapter.FeedVPAdapter
import com.linksofficial.links.view.adapter.TagsFeedAdapter
import com.linksofficial.links.viewmodel.FeedVM
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class FeedFragment : Fragment() {

    lateinit var binding: FragmentFeedBinding

    private lateinit var adapter: TagsFeedAdapter

    private val feedViewModel: FeedVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_addPostFragment)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvTags.layoutManager = layoutManager

        adapter = TagsFeedAdapter(requireActivity(), feedViewModel)
        binding.rvTags.adapter = adapter


        adapter.submitList(ConstantsHelper.getTagList())

        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = FeedVPAdapter(this)

        binding.nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.fab.hide()
            } else {
                binding.fab.show()
            }
        }


        feedViewModel.focusTagPosition.observe(viewLifecycleOwner, {
            Timber.d("Posts:(POS_VM) = $it")
            binding.viewPager.setCurrentItem(it, true)
        })

    }
}