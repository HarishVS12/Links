package com.linksofficial.links.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by Harish on 09-03-2021
 **/
@Entity(tableName = "posts_local")
data class PostsLocal(
    @PrimaryKey
    var id: String,
    var user_id: String,
    var user_name: String,
    var user_photo_url: String,
    var link: String,
    var title: String,
    var caption: String,
    var is_public: Boolean = true,
    var tag: String,
    var likes_count: Int,
)