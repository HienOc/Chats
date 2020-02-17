package com.adnet.notificationfirebase.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adnet.notificationfirebase.R
import com.adnet.notificationfirebase.model.Chats
import com.adnet.notificationfirebase.model.ChatsDiffUtilCallback
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.item_chat_right.view.*

class MessageListAdapter(
    private val clickListener: (Chats) -> Unit
) :ListAdapter<Chats, MessageListAdapter.ViewHolder>(
    ChatsDiffUtilCallback()
){
    private lateinit var auth: FirebaseAuth

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return if (viewType == MessageAdapter.LEFT) {
            val itemView = LayoutInflater.from(
                viewGroup.context
            ).inflate(R.layout.item_chat_left, viewGroup, false)
           ViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(
                viewGroup.context
            ).inflate(R.layout.item_chat_right, viewGroup, false)
           ViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position), clickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(chats: Chats, clickListener: (Chats) -> Unit) {
            itemView.textChat.text = chats.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        auth = FirebaseAuth.getInstance()
        return if (getItem(position).sender.equals(auth.uid)) {
            MessageAdapter.RIGHT
        } else MessageAdapter.LEFT
    }


}