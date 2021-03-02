package com.linksofficial.links.view.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.linksofficial.links.databinding.FragmentTabMyLinkBinding
import com.linksofficial.links.view.adapter.MyLinkTabAdapter
import com.linksofficial.links.viewmodel.MyLinkVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class
TabMyLinkFragment : Fragment() {

    private lateinit var binding: FragmentTabMyLinkBinding
    private val myLinkVM: MyLinkVM by sharedViewModel()
    private lateinit var adapter: MyLinkTabAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTabMyLinkBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MyLinkTabAdapter(myLinkVM)
        binding.rvMyLink.adapter  = adapter
        binding.rvMyLink.layoutManager = LinearLayoutManager(requireContext())

        myLinkVM.postDetails.observe(viewLifecycleOwner, { it ->
            if(it.isEmpty()){
                binding.lottieAnim.visibility = View.VISIBLE
            }else{
                binding.lottieAnim.visibility = View.GONE
            }
            adapter.submitList(it)
        })
    }


}