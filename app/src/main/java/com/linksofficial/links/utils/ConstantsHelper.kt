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

    //SavedStateHandlerKeys
    const val POST_STATUS = "post_status"

    //Get tag list
    fun getTagList() =
        mutableListOf(
            Tags("Technology"),
            Tags("Science"),
            Tags("Space"),
            Tags("Politics"),
            Tags("Sports"),
            Tags("Cinema"),
            Tags("Entertainment"),
            Tags("Music"),
            Tags("Cricket")
        )

}