package com.linksofficial.links.view.ui.home.bottomNav

import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.linksofficial.links.R
import com.linksofficial.links.databinding.FragmentFeedBinding
import com.linksofficial.links.utils.ConstantsHelper
import com.linksofficial.links.view.adapter.FeedVPAdapter
import com.linksofficial.links.view.adapter.TagsFeedAdapter
import com.linksofficial.links.view.ui.activities.LinkMainActivity
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
//        feedViewModel.writeLinkCopied(true)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedViewModel.readLinkCopied().observe(viewLifecycleOwner, {
            if (it) checkClipData()
        })

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



        feedViewModel.focusTagPosition.observe(viewLifecycleOwner, {
            Timber.d("Posts:(POS_VM) = $it")
            binding.viewPager.setCurrentItem(it, true)
        })

    }


    private fun checkClipData() {
        val clipBoard =
            (context as LinkMainActivity).getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipBoard.hasPrimaryClip()) {
            val primaryClip = clipBoard.primaryClip?.getItemAt(0)
            val copiedText = primaryClip?.text

            if (Patterns.WEB_URL.matcher(copiedText).matches()) {
                showSnackBar(copiedText.toString())
            }
        }
    }

    private fun showSnackBar(text: String) {
        val mSnackbar = Snackbar.make(binding.coordinatorLayout, "Post Copied link?", Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setAction("Post") {
                val action = FeedFragmentDirections.actionFeedFragmentToAddPostFragment(text)
                findNavController().navigate(action)
            }
            .setBackgroundTint(resources.getColor(R.color.primaryColor))
            .setActionTextColor(resources.getColor(R.color.white))

        val snackText = (mSnackbar.view).findViewById<TextView>(R.id.snackbar_text)
        snackText.typeface = ResourcesCompat.getFont(requireActivity(), R.font.lato_bold)
        val snackAction = (mSnackbar.view).findViewById<TextView>(R.id.snackbar_action)
        snackAction.typeface = ResourcesCompat.getFont(requireActivity(), R.font.lato_black)

        mSnackbar.view.translationZ = 10f
        mSnackbar.show()
        feedViewModel.writeLinkCopied(false)
    }

    override fun onStop() {
        super.onStop()
        feedViewModel.writeLinkCopied(false)
    }
}