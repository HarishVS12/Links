package com.linksofficial.links.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class User(
    val user_id:String? = null,
    val username: String? = null,
    val email: String? = null,
    val photo_url:String? = null,
    val created_at:Timestamp? = null,
    val bio: String? = null,
    val favorite_tags:String? = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(user_id)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(photo_url)
        parcel.writeParcelable(created_at, flags)
        parcel.writeString(bio)
        parcel.writeString(favorite_tags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}