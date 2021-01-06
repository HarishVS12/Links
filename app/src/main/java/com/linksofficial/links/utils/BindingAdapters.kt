package com.linksofficial.links.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import timber.log.Timber

class BindingAdapters {

    companion object{

        @JvmStatic
        @BindingAdapter("app:setImage")
        fun setImage(view: ImageView, imageUrl: String?) {
            Timber.i("ImageUrl: $imageUrl")
            view.load(imageUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }

    }

}