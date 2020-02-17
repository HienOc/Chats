package com.adnet.notificationfirebase.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adnet.notificationfirebase.R
import com.adnet.notificationfirebase.activity.MessageActivity
import com.adnet.notificationfirebase.model.Users
import kotlinx.android.synthetic.main.item_users.view.*

class UsersAdapter(
    private var users: List<Users>
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            viewGroup.context
        ).inflate(R.layout.item_users, viewGroup, false)
        return ViewHolder(itemView, viewGroup.context)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bindData(users, holder.adapterPosition)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = users.size

    class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        fun bindData(users: List<Users>, position: Int) {
            itemView.textViewName.text = users[position].userName

            itemView.setOnClickListener {
                context.startActivity(
                    MessageActivity.getIntent(
                        context,
                        users[position].id.toString(),
                        users[position].userName.toString(),
                        users[position].imageUrl.toString()
                    )
                )
            }
        }

    }

}