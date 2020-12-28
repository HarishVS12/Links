package com.linksofficial.links.di

import com.linksofficial.links.utils.NetworkHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single{ NetworkHelper(androidContext())}

}