package com.linksofficial.links.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import timber.log.Timber

class BindingAdapters {

    companion object {

        @JvmStatic
        @BindingAdapter("app:setImage", "progressbar")
        fun setImage(view: ImageView, imageUrl: String?, progressBar: ProgressBar) {
            Timber.i("ImageUrl: $imageUrl")

            Glide
                .with(view.context)
                .load(imageUrl)
                .circleCrop()
                .into(view)
            progressBar.visibility = View.GONE
        }

        @JvmStatic
        @BindingAdapter("app:putImage")
        fun putImage(view: ImageView, imageUrl: String?) {

            Timber.d("imURL(binding): $imageUrl")
            Glide
                .with(view.context)
                .load(imageUrl)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        }
    }

}