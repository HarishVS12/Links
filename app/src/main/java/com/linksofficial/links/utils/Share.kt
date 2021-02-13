package com.linksofficial.links.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.linksofficial.links.R

/**
Created by Harish on 08-02-2021
 **/
class Share {

    companion object {

        @JvmStatic
        fun shareApp(context: Context) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Dummy Text")
            try {
                context.startActivity(Intent.createChooser(intent, "Share using"))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show()
            }
        }

        @JvmStatic
        fun shareLink(context: Context, link: String) {
            val playLink = "https://play.google.com/store/apps/details?id=com.linksofficial.links"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Hi! I would like to share the link to you which I found interesting. \n\n \uD83D\uDC49\uD83C\uDFFC $link\n\n"
                        + "You can find more such links in the Links App. Download it in play store.\n\n \uD83D\uDC49\uD83C\uDFFC $playLink"
            )
            try {
                context.startActivity(Intent.createChooser(intent, "Share using"))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Whatsapp not found", Toast.LENGTH_SHORT).show()
            }
        }

        @JvmStatic
        fun contactUs(context: Context) {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.setData(Uri.parse("mailto:"))
            intent.putExtra(Intent.EXTRA_EMAIL, Array(1) { context.getString(R.string.dev_mail) })
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                context.getString(R.string.links_feedback_subject)
            )
//            intent.type = "message/rfc822"
            try {
                context.startActivity(Intent.createChooser(intent, "Select an app"))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "No Email client found", Toast.LENGTH_SHORT).show()
            }
        }

        @JvmStatic
        fun rateApp(context: Context) {
            val packageName = "com.linksofficial.links"
            val i = Intent(Intent.ACTION_VIEW)
            try {
                i.data = Uri.parse("market://details?id=$packageName")
            } catch (e: Exception) {
                i.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            }
            context.startActivity(i)
        }


    }

}