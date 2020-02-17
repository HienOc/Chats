package com.adnet.notificationfirebase.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adnet.notificationfirebase.R
import com.adnet.notificationfirebase.ViewPagerAdapter
import com.adnet.notificationfirebase.fragment.ChatFragment
import com.adnet.notificationfirebase.fragment.UsersFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private val _adapter by lazy {
        ViewPagerAdapter(this).apply {
            addFragment(ChatFragment.newInstance())
            addFragment(UsersFragment.newInstance())
        }
    }

    private val nameTab = mutableListOf<String>().apply {
        add("Chat")
        add("User")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewPagerMain.adapter = _adapter
        TabLayoutMediator(tabLayoutMain, viewPagerMain,
            TabLayoutMediator.OnConfigureTabCallback { tab, position ->
                tab.text = nameTab[position]
            }).attach()
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }

}
