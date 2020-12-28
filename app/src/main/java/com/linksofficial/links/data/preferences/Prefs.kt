package com.linksofficial.links.data.preferences

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.linksofficial.links.data.preferences.Prefs.PreferencesKeys.FIRST_APP_OPEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Prefs(private val context:Context) {

    companion object{
        //Name of the data store
        const val LINKS_DATA = "links_data"

        //Name of the preferences
        const val firstAppOpen = "first_app_open"
    }

    private object PreferencesKeys{
        val FIRST_APP_OPEN = preferencesKey<Boolean>(firstAppOpen)
    }

    private fun createDataStore():DataStore<Preferences>{
        return context.createDataStore(
            name = LINKS_DATA
        )
    }


    fun readFirstAppOpen():Flow<Boolean>{
        return createDataStore().data.map {
            it[FIRST_APP_OPEN]?:false
        }
    }

    suspend fun writeFirstAppOpen(isFirstAppOpen:Boolean){
        createDataStore().edit {
            it[FIRST_APP_OPEN] = isFirstAppOpen
        }
    }


}