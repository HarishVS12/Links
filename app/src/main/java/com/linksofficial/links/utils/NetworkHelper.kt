package com.linksofficial.links.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkHelper(val context: Context) {

        private fun isNetworkConnected(): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm?.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }

        fun isNetConnected(): Boolean {
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
                c = isNetworkConnected()
            }
            return c
        }

}