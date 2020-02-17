package com.adnet.notificationfirebase.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adnet.notificationfirebase.R
import com.adnet.notificationfirebase.model.Chats
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.item_chat_right.view.*


class MessageAdapter(
    private var chats: List<Chats>
) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == LEFT) {
            val itemView = LayoutInflater.from(
                viewGroup.context
            ).inflate(R.layout.item_chat_left, viewGroup, false)
            ViewHolder(itemView, viewGroup.context)
        } else {
            val itemView = LayoutInflater.from(
                viewGroup.context
            ).inflate(R.layout.item_chat_right, viewGroup, false)
            ViewHolder(itemView, viewGroup.context)
        }

    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
//        getItemViewTyper(holder.adapterPosition)
//        holder.bindData(chats, holder.adapterPosition)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //  getItemViewTyper(position)
        holder.bindData(chats, position)
    }

    override fun getItemCount() = chats.size

    class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        fun bindData(chats: List<Chats>, position: Int) {
            itemView.textChat.text = chats[position].message
        }

    }

    override fun getItemViewType(position: Int): Int {
        auth = FirebaseAuth.getInstance()
        return if (chats[position].sender.equals(auth.uid)) {
            RIGHT
        } else LEFT
    }


    companion object {
        const val LEFT = 0
        const val RIGHT = 1
    }

}
