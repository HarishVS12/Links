package com.linksofficial.links.utils

import android.content.Context

class DeviceDetails{

    companion object{
        @JvmStatic
        fun getDeviceWidth(context: Context):Int{
            return context.resources.displayMetrics.widthPixels
        }

        @JvmStatic
        fun getDeviceLength(context: Context):Int{
            return context.resources.displayMetrics.heightPixels
        }
    }

}