package com.linksofficial.links.view.ui.feed.bottomSheet

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.linksofficial.links.R
import com.linksofficial.links.data.local.model.PostLocal
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.databinding.FragmentFeedPropBottomSheetBinding
import com.linksofficial.links.utils.Share
import com.linksofficial.links.viewmodel.MySavedLinkTabVM
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FeedPropBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFeedPropBottomSheetBinding

    private val args: FeedPropBottomSheetArgs by navArgs()
    private var post: Post? = null

    private val feedVM: MySavedLinkTabVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedPropBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        post = args.post

        binding.linearShare.setOnClickListener {
            post?.link?.let { it1 -> Share.shareLink(requireActivity(), it1) }
        }

        binding.linearCopy.setOnClickListener {
            val clipboard = (it.context).getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Link",post?.link)
            clipboard.setPrimaryClip(clip)

            Toasty.custom(it.context,"Link Copied!",resources.getDrawable(R.drawable.ic_file_copy,null),resources.getColor(R.color.primaryColor),Color.WHITE,Toasty.LENGTH_SHORT,true,true).show()
            dialog?.dismiss()
        }

        binding.linearSaveLink.setOnClickListener {
            val postLocal = PostLocal(
                postTitle = post?.title?:"",
                postCaption =  post?.caption?:"",
                postLink = post?.link?:"",
                userName = post?.user_name?:"",
                userPhotoUrl = post?.user_photo_url?:"",
                postID = post?.id?:""
            )
            Timber.d("localDB: $postLocal")
            feedVM.postLinkLocal(postLocal)
            Toasty.success(requireActivity(),"Saved Link Successfully!",Toasty.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

    }


    override fun getTheme(): Int {
        return R.style.BottomSheetBgTheme
    }

}