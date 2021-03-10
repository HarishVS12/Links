package com.linksofficial.links.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.linksofficial.links.data.local.model.PostsLocal
import com.linksofficial.links.data.local.model.SavedPosts
import kotlinx.coroutines.flow.Flow

/**
Created by Harish on 13-02-2021
 **/

@Dao
interface PostsDao {

    //Saved Posts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedPost(vararg post:SavedPosts)

    @Query("Select * from post_table")
    fun getAllSavedPosts(): Flow<List<SavedPosts>>

    @Query("Delete from post_table where post_id=:post_id")
    suspend fun deletePost(vararg post_id:String)

    //Local Posts
    @Query("Select * from posts_local")
    fun getAllLocalPosts(): Flow<List<PostsLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocalPosts(vararg postLocal:PostsLocal)

}