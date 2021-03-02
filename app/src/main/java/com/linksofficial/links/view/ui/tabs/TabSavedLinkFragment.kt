package com.linksofficial.links.view.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.linksofficial.links.databinding.FragmentTabSavedLinkBinding
import com.linksofficial.links.view.adapter.MySavedLinkTabAdapter
import com.linksofficial.links.viewmodel.MyLinkVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TabSavedLinkFragment : Fragment() {

    private lateinit var binding: FragmentTabSavedLinkBinding
    private val mySavedLinkVM: MyLinkVM by sharedViewModel()
    private lateinit var mySavedLinkTabAdapter: MySavedLinkTabAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTabSavedLinkBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mySavedLinkTabAdapter = MySavedLinkTabAdapter(mySavedLinkVM)
        binding.rvMySavedLink.apply {
            adapter = mySavedLinkTabAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        mySavedLinkVM.readAllLocalPosts.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                binding.lottieAnim.visibility = View.VISIBLE
            } else {
                binding.lottieAnim.visibility = View.GONE
            }
            mySavedLinkTabAdapter.submitList(it)
        })

    }

}