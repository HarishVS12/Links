package com.linksofficial.links.di

import android.app.Application
import androidx.room.Room
import com.linksofficial.links.data.local.PostLocalDatabase
import com.linksofficial.links.data.local.dao.PostsDao
import com.linksofficial.links.utils.ConstantsHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
Created by Harish on 13-02-2021
 **/
val databaseModule = module {

    fun provideSavedPostDB(database: PostLocalDatabase): PostsDao {
        return database.savedPostsDao()
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
        provideSavedPostDB(get())
    }

}