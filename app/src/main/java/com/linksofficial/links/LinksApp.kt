package com.linksofficial.links

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class LinksApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
        initializeTimber()
    }

    private fun initializeKoin(){
        startKoin {
            androidContext(this@LinksApp)
        }
    }

    private fun initializeTimber(){
        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

}