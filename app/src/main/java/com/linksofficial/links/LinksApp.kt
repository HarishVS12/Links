package com.linksofficial.links

import android.app.Application
import com.linksofficial.links.di.appModule
import com.linksofficial.links.di.repositoryModule
import com.linksofficial.links.di.viewModelModule
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
            modules(listOf(appModule,repositoryModule, viewModelModule))
        }
    }

    private fun initializeTimber(){
        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

}