package com.adnet.notificationfirebase.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.adnet.notificationfirebase.R
import com.adnet.notificationfirebase.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private var users = mutableListOf<Users>()

    private val usersAdapter by lazy {
        UsersAdapter(users)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_users, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child: DataSnapshot in dataSnapshot.children) {
                    val user = child.getValue(Users::class.java)
                    if(user?.id.equals(auth.uid)){

                    }
                    else{
                        user?.let { users.add(it) }
                    }
                }
                setUpAdapter()
            }

        })

    }

    fun setUpAdapter() {
        recyclerViewUser.visibility = View.VISIBLE
        progressUser.visibility = View.GONE

        recyclerViewUser.run {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = usersAdapter
        }
    }


    companion object {
        fun newInstance() = UsersFragment()
    }
}