package com.example.mad

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mad.LoginAndSignUp.UserProfile
import com.example.mad.databinding.ActivityViewFeedbackCommonBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewFeedbackCommon : AppCompatActivity() {
    private lateinit var binding: ActivityViewFeedbackCommonBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewFeedbackCommonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var ratingScore = 0
        val etid=findViewById<TextView>(R.id.tvid)

        databaseRef = FirebaseDatabase.getInstance().reference.child("feedback")
        val id = intent.getStringExtra("id").toString()
        val uid = intent.getStringExtra("uid").toString()
        val propId = intent.getStringExtra("propId").toString()
        val des = intent.getStringExtra("des").toString()
        val rating = intent.getStringExtra("rating").toString()

        etid.setText(id)
        binding.etNewDescription.setText(des)
        binding.ratingBar.rating = rating.toFloat()


        binding.ratingBar.setOnRatingBarChangeListener { bar, fl, b ->

            when (bar.rating.toInt()) {
                1 -> ratingScore = 1
                2 -> ratingScore = 2
                3 -> ratingScore = 3
                4 -> ratingScore = 4
                5 -> ratingScore = 5
                else -> ratingScore = 0
            }
        }


    }
}
