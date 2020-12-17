package com.linksofficial.links.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import com.linksofficial.links.BuildConfig

class NetworkHelper {

    companion object {

        private fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm?.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }

        fun isNetConnected(context: Context): Boolean {
            var c = false
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                val cm =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val n = cm.activeNetwork
                if (n != null) {
                    val b = cm.getNetworkCapabilities(n)
                    c = b!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            b!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                }
            } else {
                c = isNetworkConnected(context)
            }
            return c
        }
    }
}