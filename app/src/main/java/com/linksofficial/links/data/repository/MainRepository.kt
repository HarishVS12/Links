package com.linksofficial.links.data.repository

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.linksofficial.links.data.model.Post
import com.linksofficial.links.data.model.User
import com.linksofficial.links.data.preferences.Prefs
import com.linksofficial.links.utils.ConstantsHelper
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class MainRepository(private val prefs: Prefs) {

    val database = Firebase.firestore

    //Preferences
    fun readFirstAppOpen(): Flow<Boolean> {
        return prefs.readFirstAppOpen()
    }

    suspend fun writeFirstAppOpen(isFirstAppOpen: Boolean) {
        prefs.writeFirstAppOpen(isFirstAppOpen)
    }

    suspend fun writeUserDetails(user: User) {
        prefs.writeUserDetails(user)
    }

    fun readUserDetails(): Flow<User> {
        return prefs.readUserDetail()
    }


    //Firestore writes and reads
    fun writeUserLogin(user: User, uniqueId: String?) {
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
    fun updateUserDetails(uniqueId: String?, userData: HashMap<String, Any?>) {
        database.collection(ConstantsHelper.USER)
            .document(uniqueId.toString())
            .update(userData)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    Timber.i("Updated User data successfully")
            }
            .addOnFailureListener {
                Timber.e(it)
            }
    }

    //Post Link
    suspend fun postLink(context:Context,uniqueId: String?, post: Post) {
        database.collection(ConstantsHelper.POST)
            .document("${uniqueId.toString()}_${post.created_at?.seconds.toString()}")
            .set(post)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    Timber.i("Posted link post successfully")
                    Toasty.success(context, "Link posted successfully!", Toast.LENGTH_LONG, true)
                        .show()
            }
            .addOnFailureListener {
                Timber.e(it)
                Toasty.error(context, "Link post failed. Please try again later!", Toast.LENGTH_LONG, true).show()
            }
    }

}