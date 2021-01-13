package com.linksofficial.links.view.ui.addPost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.linksofficial.links.data.model.Tags
import com.linksofficial.links.databinding.FragmentAddPostBinding
import com.linksofficial.links.view.adapter.TagsAddPostAdapter


class AddPostFragment : Fragment() {

    private lateinit var binding: FragmentAddPostBinding
    private lateinit var tagAdapter: TagsAddPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddPostBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tagAdapter = TagsAddPostAdapter()

        val list = mutableListOf<Tags>(
            Tags("Technology"),
            Tags("Science"),
            Tags("Space"),
            Tags("Politics"),
            Tags("Sports"),
        )

        binding.rvTags.apply {
            adapter = tagAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        tagAdapter.submitList(list)

    }
}