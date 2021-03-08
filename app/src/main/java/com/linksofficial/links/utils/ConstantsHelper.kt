package com.linksofficial.links.utils

import com.linksofficial.links.data.model.Tags

object ConstantsHelper {
    const val RC_SIGN_IN = 12

    //For ViewPagers without creating unique fragments
    const val ONBOARDING_VP_ARG = "onboarding_object"
    const val FEED_VP_ARG = "feed_object"

    //Firestore Collections
    const val USER = "User"
    const val POST = "Post"

    //Database
    const val POST_DB = "post_local_database"

    //SavedStateHandlerKeys
    const val POST_STATUS = "post_status"

    //Get tag list
    fun getTagList() =
        mutableListOf(
            Tags("Technology"),
            Tags("Arts & Entertainment"),
            Tags("Programming"),
            Tags("Cricket"),
            Tags("Cinema"),
            Tags("Personal Development"),
            Tags("Science"),
            Tags("Space"),
            Tags("Politics"),
            Tags("Sports"),
            Tags("Music"),
            Tags("Health")
        )

}