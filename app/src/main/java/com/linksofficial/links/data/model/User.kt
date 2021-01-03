package com.linksofficial.links.data.model

import com.google.firebase.Timestamp

data class User(
    val user_id:String? = null,
    val username: String? = null,
    val email: String? = null,
    val photo_url:String? = null,
    val created_at:Timestamp? = null,
    val bio: String? = null,
    val favorite_tags:String? = null
)