package com.linksofficial.links.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.linksofficial.links.data.local.dao.PostsDao
import com.linksofficial.links.data.local.model.PostsLocal
import com.linksofficial.links.data.local.model.SavedPosts

/**
Created by Harish on 13-02-2021
 **/

@Database(entities = [SavedPosts::class, PostsLocal::class], version = 1, exportSchema = false)
abstract class PostLocalDatabase : RoomDatabase() {

    abstract fun savedPostsDao(): PostsDao
}