package com.linksofficial.links.di

import android.app.Application
import androidx.room.Room
import com.linksofficial.links.data.local.PostLocalDatabase
import com.linksofficial.links.data.local.dao.PostLocalDao
import com.linksofficial.links.utils.ConstantsHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
Created by Harish on 13-02-2021
 **/
val databaseModule = module {

    fun providePostDB(database: PostLocalDatabase): PostLocalDao {
        return database.postLocalDao()
    }

    fun provideDatabase(application: Application) =
        Room.databaseBuilder(
            application,
            PostLocalDatabase::class.java,
            ConstantsHelper.POST_DB
        ).fallbackToDestructiveMigration()
            .build()


    single {
        provideDatabase(androidApplication())
    }

    single {
        providePostDB(get())
    }

}