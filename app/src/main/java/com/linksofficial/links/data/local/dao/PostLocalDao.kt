package com.linksofficial.links.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.linksofficial.links.data.local.model.PostLocal
import kotlinx.coroutines.flow.Flow

/**
Created by Harish on 13-02-2021
 **/

@Dao
interface PostLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(vararg post:PostLocal)

    @Query("Select * from post_table")
    fun getAllPosts(): Flow<List<PostLocal>>

}