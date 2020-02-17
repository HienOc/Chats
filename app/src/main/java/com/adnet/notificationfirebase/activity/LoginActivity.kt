package com.adnet.notificationfirebase.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.adnet.notificationfirebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pd= ProgressDialog(this)
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            startActivity(HomeActivity.getIntent(this))
            finish()
        } else {
            progressLogin.visibility = View.GONE
            linearLogin.visibility = View.VISIBLE
        }
        loginButton.setOnClickListener(this)
        buttonRegister.setOnClickListener(this)
    }

    private fun signIn(email: String, password: String) {
        pd.setMessage("loading")
        pd.show()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                pd.dismiss()
                if (task.isSuccessful) {
                    startActivity(HomeActivity.getIntent(this))
                    finish()
                } else {
                    Log.d("Hien", "signInWithEmail:failure")
                }
            }
    }

    private fun signUp(email: String, password: String) {
        pd.setMessage("loading")
        pd.show()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                pd.dismiss()
                if (task.isSuccessful) {
                    val firUser = auth.currentUser
                    val idUser = firUser?.uid
                    ref = FirebaseDatabase.getInstance().getReference("Users")
                        .child(idUser.toString())
                    val map = HashMap<String, String>()
                    map["id"] = idUser.toString()
                    map["userName"] = email
                    map["imageUrl"] = "Abc"
                    ref.setValue(map)
                } else {

                }
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loginButton -> signIn(username.text.toString(), password.text.toString())
            R.id.buttonRegister -> signUp(username.text.toString(), password.text.toString())
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

}
