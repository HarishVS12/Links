package com.linksofficial.links.view.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.linksofficial.links.R
import com.linksofficial.links.data.model.Tags
import com.linksofficial.links.databinding.FragmentFeedBinding
import com.linksofficial.links.view.adapter.TagsFeedAdapter


class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var list: MutableList<Tags>

    private lateinit var adapter: TagsFeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_addPostFragment)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvTags.layoutManager = layoutManager

        adapter = TagsFeedAdapter()
        binding.rvTags.adapter = adapter

        list = mutableListOf(
            Tags("Technology"),
            Tags("Science"),
            Tags("Space"),
            Tags("Politics"),
            Tags("Sports"),
        )

        adapter.submitList(list)

    }
}