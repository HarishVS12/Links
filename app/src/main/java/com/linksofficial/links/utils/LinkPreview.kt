package com.linksofficial.links.utils

import android.webkit.URLUtil
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.net.URI
import java.net.URISyntaxException

data class Properties(
    var url: String? = null,
    var imageUrl: String? = null,
    var title: String? = null,
    var description: String? = null
)

class LinkPreview(var url: String?) {


    fun getImageUrl(): String {
        var doc: Document? = null
        doc = Jsoup.connect(url)
            .timeout(30 * 1000)
            .get()
        val elements: Elements = doc.select("meta[property=og:image]")
        if (elements.size > 0) {
            val image = elements.attr("content")
            if (!image.isEmpty()) {
                return resolveURL(url!!, image)
            }
        }
        return ""
    }

    private fun resolveURL(url: String, part: String): String {
        return if (URLUtil.isValidUrl(part)) {
            part
        } else {
            var baseUri: URI? = null
            try {
                baseUri = URI(url)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            baseUri = baseUri?.resolve(part)
            baseUri.toString()
        }
    }

}