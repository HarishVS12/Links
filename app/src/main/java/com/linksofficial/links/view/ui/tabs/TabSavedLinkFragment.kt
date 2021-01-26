package com.linksofficial.links.view.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.linksofficial.links.R


class TabSavedLinkFragment : Fragment() {

    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_saved_link, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById<ImageView>(R.id.image)

        getImageFromURL()
    }


    fun getImageFromURL() {

        /*lifecycleScope.launch(Dispatchers.IO) {
            val getImageUrl =
                LinkPreview(
                    "https://www.youtube.com/watch?v=5XmGuQHLjoI&list=RD2lxScNFuCA0&index=5"
                ).getImageUrl()
            withContext(Dispatchers.Main) {
                Glide
                    .with(this@TabSavedLinkFragment)
                    .load(getImageUrl)
                    .into(imageView)
            }
        }*/

    }

}