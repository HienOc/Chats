package com.adnet.notificationfirebase.model

import androidx.recyclerview.widget.DiffUtil

class ChatsDiffUtilCallback : DiffUtil.ItemCallback<Chats>() {
    override fun areItemsTheSame(oldItem: Chats, newItem: Chats): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: Chats, newItem: Chats): Boolean {
        return oldItem == newItem
    }
}
