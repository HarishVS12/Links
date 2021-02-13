package com.linksofficial.links.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.linksofficial.links.data.local.dao.PostLocalDao
import com.linksofficial.links.data.local.model.PostLocal

/**
Created by Harish on 13-02-2021
 **/

@Database(entities = [PostLocal::class], version = 1, exportSchema = false)
abstract class PostLocalDatabase : RoomDatabase() {

    abstract fun postLocalDao(): PostLocalDao


}