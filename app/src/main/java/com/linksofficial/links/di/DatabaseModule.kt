package com.linksofficial.links.di

import androidx.room.Room
import com.linksofficial.links.data.local.PostLocalDatabase
import com.linksofficial.links.data.local.dao.PostLocalDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
Created by Harish on 13-02-2021
 **/
val databaseModule = module {

    fun providePostDB(database: PostLocalDatabase): PostLocalDao {
        return database.postLocalDao()
    }


    single {
        Room.databaseBuilder(
            androidApplication(),
            PostLocalDatabase::class.java,
            "post_local_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        providePostDB(get())
    }

}