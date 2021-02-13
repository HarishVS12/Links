package com.linksofficial.links.di

import com.linksofficial.links.data.preferences.Prefs
import com.linksofficial.links.data.repository.MainRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    single { MainRepository(get(), get()) }
    single { Prefs(androidContext()) }

}