package com.linksofficial.links.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import timber.log.Timber

class BindingAdapters {

    companion object{

        @JvmStatic
        @BindingAdapter("app:setImage","progressbar")
        fun setImage(view: ImageView, imageUrl: String?, progressBar:ProgressBar) {
            Timber.i("ImageUrl: $imageUrl")
            view.load(imageUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
                progressBar.visibility = View.GONE
            }
        }

    }

}