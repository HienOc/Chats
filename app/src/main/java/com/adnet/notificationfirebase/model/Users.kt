package com.adnet.notificationfirebase.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(
    val id: String? = "",
    val userName: String? = "",
    val imageUrl: String? = ""
)