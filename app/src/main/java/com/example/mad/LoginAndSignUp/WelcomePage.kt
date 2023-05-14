package com.example.mad.LoginAndSignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mad.R

class WelcomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        var signIn=findViewById<Button>(R.id.login)
        var signUn=findViewById<Button>(R.id.signup)

        signIn.setOnClickListener {
            var intent= Intent(this, ActivitySignIn::class.java)
            startActivity(intent)
        }

        signUn.setOnClickListener {
            var intent= Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}