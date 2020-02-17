package com.adnet.notificationfirebase.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Chats(
    val sender: String? = "",
    val receiver: String? = "",
    val message: String? = "",
    val time: String? = ""
)