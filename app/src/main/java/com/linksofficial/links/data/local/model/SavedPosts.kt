package com.linksofficial.links.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by Harish on 13-02-2021
 **/

@Entity(tableName = "post_table")
data class SavedPosts(
    @PrimaryKey
    @ColumnInfo(name = "post_id")
    val postID: String,
    @ColumnInfo(name = "post_title")
    val postTitle:String,
    @ColumnInfo(name = "post_caption")
    val postCaption:String,
    @ColumnInfo(name = "post_link")
    val postLink: String,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "user_photo_url")
    val userPhotoUrl: String
)