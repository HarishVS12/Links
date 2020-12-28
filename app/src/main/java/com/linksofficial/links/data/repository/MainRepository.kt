package com.linksofficial.links.data.repository

import com.linksofficial.links.data.preferences.Prefs
import kotlinx.coroutines.flow.Flow

class MainRepository(private val prefs: Prefs) {


    //Preferences
    fun readFirstAppOpen(): Flow<Boolean> {
        return prefs.readFirstAppOpen()
    }

    suspend fun writeFirstAppOpen(isFirstAppOpen:Boolean){
        prefs.writeFirstAppOpen(isFirstAppOpen)
    }


}