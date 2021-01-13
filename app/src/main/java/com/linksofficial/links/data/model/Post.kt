package com.linksofficial.links.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class Post(
    var id: String? = null,
    var user_id: String? = null,
    var link: String? = null,
    var title: String? = null,
    var caption: String? = null,
    var is_public: Boolean = true,
    var tag: String? = null,
    var likes_count: Int? = 0,
    var created_at: Timestamp? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Timestamp::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(user_id)
        parcel.writeString(link)
        parcel.writeString(title)
        parcel.writeString(caption)
        parcel.writeByte(if (is_public) 1 else 0)
        parcel.writeString(tag)
        parcel.writeValue(likes_count)
        parcel.writeParcelable(created_at, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }

}