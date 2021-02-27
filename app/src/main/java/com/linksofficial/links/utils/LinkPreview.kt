package com.linksofficial.links.utils

import android.webkit.URLUtil
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.net.URI
import java.net.URISyntaxException

/**
 * Created by Harish
 */
data class LinkProperties(
    var url: String? = null,
    var imageUrl: String? = null,
    var title: String? = null,
    var description: String? = null
)

class LinkPreview(var url: String?) {

    fun getLinkProperties():LinkProperties =
        LinkProperties(
            url = url,
            imageUrl = getImageUrl(),
            title = getTitle(),
            description = getCaption()
        )

    private fun getElements(url: String): Elements {
        return getDocument(url).getElementsByTag("meta")
    }

    private fun getDocument(url: String): Document {
        return Jsoup.connect(url).timeout(30 * 1000).get()
    }

    private fun getCaption(): String {
        var description = getElements(url ?: "").select("meta[name=description]").attr("content")
        if (description.isEmpty() || description == null) {
            description = getElements(url ?: "").select("meta[name=Description]").attr("content")
        }
        if (description.isEmpty() || description == null) {
            description = getElements(url ?: "").select("meta[name=og:description]").attr("content")
        }
        if (description.isEmpty() || description == null) {
            description = ""
        }
        return description
    }

    private fun getTitle(): String {
        var title = getElements(url ?: "").select("meta[property=og:title]").attr("content")
        if (title == null || title.isEmpty()) {
            title = getDocument(url ?: "").title()
        }
        return title
    }

    fun getImageUrl(): String {
        val elements: Elements = getElements(url ?: "").select("meta[property=og:image]")
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