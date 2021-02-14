package com.linksofficial.links.view.ui.activities

import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.linksofficial.links.R
import com.linksofficial.links.databinding.ActivityWebViewBinding
import com.linksofficial.links.utils.Share
import timber.log.Timber

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
                supportZoom()
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

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return false
    }

}