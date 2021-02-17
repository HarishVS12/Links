package com.linksofficial.links.view.ui.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.linksofficial.links.R
import com.linksofficial.links.databinding.ActivityWebViewBinding
import com.linksofficial.links.utils.Share
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)

        val url = intent.getStringExtra("url")
        Timber.d("UrlForWeb: $url")
        if (url != null) {
            binding.webView.loadUrl(url)
        }

        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
                setSupportZoom(true)
                builtInZoomControls = true
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            }
            webViewClient = MyWebViewClient()
        }



        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivShare.setOnClickListener {
            Share.shareLink(this, url!!)
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}

private class MyWebViewClient() : WebViewClient() {

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        return super.shouldInterceptRequest(view, request)
    }

    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        if (url == null) {
            return super.shouldInterceptRequest(view, url as String)
        }
        return if (url.toLowerCase(Locale.ROOT).contains("jpg") || url.toLowerCase(Locale.ROOT)
                .contains("jpeg")
        ) {
            val bitmap =
                Glide.with(view!!).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).load(url)
                    .submit().get()
            WebResourceResponse(
                "image/jpg",
                "UTF-8",
                getBitmapInputStream(bitmap, Bitmap.CompressFormat.JPEG)
            )
        } else if (url.toLowerCase(Locale.ROOT).contains(".png")) {
            val bitmap =
                Glide.with(view!!).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).load(url)
                    .submit().get()
            WebResourceResponse(
                "image/png", "UTF-8", getBitmapInputStream(
                    bitmap,
                    Bitmap.CompressFormat.PNG
                )
            )
        } else if (url.toLowerCase(Locale.ROOT).contains(".webp")) {
            val bitmap =
                Glide.with(view!!).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).load(url)
                    .submit().get()
            WebResourceResponse(
                "image/webp", "UTF-8", getBitmapInputStream(
                    bitmap,
                    Bitmap.CompressFormat.WEBP_LOSSY
                )
            )
        } else {
            super.shouldInterceptRequest(view, url)
        }
    }

    private fun getBitmapInputStream(
        bitmap: Bitmap,
        compressFormat: Bitmap.CompressFormat
    ): InputStream {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(compressFormat, 80, byteArrayOutputStream)
        val bitmapData: ByteArray = byteArrayOutputStream.toByteArray()
        return ByteArrayInputStream(bitmapData)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return false
    }

}