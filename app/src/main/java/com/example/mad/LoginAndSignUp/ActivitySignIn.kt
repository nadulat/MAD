package com.example.mad.LoginAndSignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mad.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class ActivitySignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Login Sucsess", Toast.LENGTH_SHORT).show()
                        val intent= Intent(this, UserProfile::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Login Fail" + it.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                if(email.isEmpty()){
                    Toast.makeText(this,"Email field is Empty", Toast.LENGTH_LONG).show()
                }
                if(pass.isEmpty()){
                    Toast.makeText(this,"Password field is Empty", Toast.LENGTH_LONG).show()
                }
            }

        }



    }
}