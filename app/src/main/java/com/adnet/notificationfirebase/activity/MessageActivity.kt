package com.adnet.notificationfirebase.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.adnet.notificationfirebase.R
import com.adnet.notificationfirebase.model.Chats
import com.adnet.notificationfirebase.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_message.*
import java.util.*
import kotlin.collections.HashMap

class MessageActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var idRoom: String
    private lateinit var ref: DatabaseReference
    private var chats = mutableListOf<Chats>()
    private val messageAdapter by lazy {
        MessageAdapter(chats)
    }

    private var messageListAdapter = MessageListAdapter { position: Chats ->
       // deleteNote(position)
    }

    private val user by lazy {
        Users(
            intent.getStringExtra("id"),
            intent.getStringExtra("userName"),
            intent.getStringExtra("imageUrl")
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        Log.d("Hien", user.toString())
        initView()
        getChats()
    }

    private fun initView() {
        auth = FirebaseAuth.getInstance()
        textNameChat.text = user.userName
        imageBtnSend.setOnClickListener(this)

        idRoom = if(user.id.toString() > auth.uid.toString()){
            user.id.toString()+ auth.uid.toString()
        } else  auth.uid.toString()+user.id.toString()

        setUpAdapter()
    }

    private fun sendMessage(sender: String, receiver: String, message: String) {
        val ref = FirebaseDatabase.getInstance().getReference("Chats").child(idRoom).push()
        val map = HashMap<String, String>()
        map["sender"] = sender
        map["receiver"] = receiver
        map["message"] = message
        map["image"] = ""
        map["type"] = ""
        map["visibility"] = ""
        map["time"] = Calendar.getInstance().time.time.toString()
        ref.setValue(map)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imageBtnSend ->{
                sendMessage(
                    auth.uid.toString(),
                    user.id.toString(),
                    edtTextSend.text.toString()
                )
              //  edtTextSend.text = " "
            }
        }
    }

    private fun getChats() {
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("Chats").child(idRoom)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child: DataSnapshot in dataSnapshot.children) {
                    val chat = child.getValue(Chats::class.java)
                    chat?.let { chats.add(it) }
                }
                messageListAdapter.submitList(chats)

            }

        })
    }

    fun setUpAdapter() {
//        recyclerViewUser.visibility = View.VISIBLE
//        progressUser.visibility = View.GONE

        recyclerviewMessage.run {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            scrollToPosition(chats.size - 1)
            adapter = messageListAdapter
        }
    }

    companion object {

        fun getIntent(context: Context, id: String, userName: String, imageUrl: String): Intent {
            val intent = Intent(context, MessageActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("userName", userName)
            intent.putExtra("imageUrl", imageUrl)
            return intent
        }
    }


}
