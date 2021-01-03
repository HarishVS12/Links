package com.linksofficial.links.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.preferences.Prefs
import com.linksofficial.links.utils.ConstantsHelper
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class MainRepository(private val prefs: Prefs) {


    //Preferences
    fun readFirstAppOpen(): Flow<Boolean> {
        return prefs.readFirstAppOpen()
    }

    suspend fun writeFirstAppOpen(isFirstAppOpen: Boolean) {
        prefs.writeFirstAppOpen(isFirstAppOpen)
    }

    //Firestore writes and reads
    fun writeUserLogin(user: User, uniqueId: String?) {
        val database = Firebase.firestore
        database.collection(ConstantsHelper.USER)
            .document(uniqueId.toString())
            .set(user)
            .addOnCompleteListener() {
                if (it.isSuccessful)
                    Timber.d("Inserted USER to firestore successfully")
                else
                    Timber.d("Could not insert USER to firestore DB")
            }
            .addOnFailureListener {
                Timber.e(it.message)
            }
    }

    //Update User data
    fun updateUserDetails(uniqueId: String?,userData:HashMap<String,Any?>){
        val database = Firebase.firestore
        database.collection(ConstantsHelper.USER)
            .document(uniqueId.toString())
            .update(userData)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    Timber.i("Updated User data successfully")
            }
            .addOnFailureListener {
                Timber.e(it)
            }
    }



}