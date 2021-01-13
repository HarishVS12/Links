package com.linksofficial.links.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.preferences.Prefs.PreferencesKeys.BIO
import com.linksofficial.links.data.preferences.Prefs.PreferencesKeys.EMAIL
import com.linksofficial.links.data.preferences.Prefs.PreferencesKeys.FAV_TAGS
import com.linksofficial.links.data.preferences.Prefs.PreferencesKeys.FIRST_APP_OPEN
import com.linksofficial.links.data.preferences.Prefs.PreferencesKeys.PHOTO_URL
import com.linksofficial.links.data.preferences.Prefs.PreferencesKeys.UNIQUE_ID
import com.linksofficial.links.data.preferences.Prefs.PreferencesKeys.USERNAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class Prefs(private val context: Context) {

    companion object {
        //Name of the data store
        const val LINKS_DATA = "links_data"

        //Name of the preferences
        const val firstAppOpen = "first_app_open"
        const val userpreference = "user_preference"

        //User Details
        const val userName = "user_name"
        const val uniqueId = "unique_id"
        const val email = "email"
        const val photoUrl = "photo_url"
        const val bio = "bio"
        const val favTags = "fav_tags"
    }

    private object PreferencesKeys {
        val FIRST_APP_OPEN = preferencesKey<Boolean>(firstAppOpen)

        val USERNAME = preferencesKey<String>(userName)
        val UNIQUE_ID = preferencesKey<String>(uniqueId)
        val EMAIL = preferencesKey<String>(email)
        val PHOTO_URL = preferencesKey<String>(photoUrl)
        val BIO = preferencesKey<String>(bio)
        val FAV_TAGS = preferencesKey<String>(favTags)

    }

    private fun createDataStore(): DataStore<Preferences> {
        return context.createDataStore(
            name = LINKS_DATA
        )
    }

    fun readFirstAppOpen(): Flow<Boolean> {
        return createDataStore().data.map {
            it[FIRST_APP_OPEN] ?: false
        }

    }
    suspend fun writeFirstAppOpen(isFirstAppOpen: Boolean) {
        createDataStore().edit {
            it[FIRST_APP_OPEN] = isFirstAppOpen
        }
    }

    suspend fun writeUserDetails(user: User){
        Timber.d("WRITE USER: $user")
        createDataStore().edit {
            it[USERNAME] = user.username?:it[USERNAME].orEmpty()
            it[UNIQUE_ID] = user.user_id?:it[UNIQUE_ID].orEmpty()
            it[EMAIL] = user.email?:it[EMAIL].orEmpty()
            it[PHOTO_URL] = user.photo_url?:it[PHOTO_URL].orEmpty()
            it[BIO] = user.bio?:it[BIO].orEmpty()
            it[FAV_TAGS] = user.favorite_tags?:it[FAV_TAGS].orEmpty()
        }
    }

    fun readUserDetail():Flow<User>{
        return createDataStore().data.map {
            User(
                user_id = it[UNIQUE_ID]?:"",
                username = it[USERNAME]?:"",
                email = it[EMAIL]?:"",
                photo_url = it[PHOTO_URL]?:"",
                bio = it[BIO]?:"",
                favorite_tags = it[FAV_TAGS]?:"",
            )
        }
    }



}