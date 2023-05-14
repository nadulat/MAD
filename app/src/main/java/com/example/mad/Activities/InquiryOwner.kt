package com.example.mad.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mad.LoginAndSignUp.UserProfile
import com.example.mad.R
import com.google.firebase.database.*

class InquiryOwner : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquiry_owner)

        val inquiry = findViewById<TextView>(R.id.UDinquiry)
        val id = findViewById<TextView>(R.id.UDid)
        val propId = findViewById<TextView>(R.id.propId)

        val update = findViewById<Button>(R.id.update)
        val delete = findViewById<Button>(R.id.delete)

        val UID = intent.getStringExtra("UID").toString()

        database = FirebaseDatabase.getInstance().getReference("Inquiry").child(UID)

        val pid = intent.getStringExtra("id").toString()
        val pInquiry = intent.getStringExtra("inquiry").toString()
        val prop = intent.getStringExtra("prop").toString()
        id.setText(pid)
        inquiry.setText(pInquiry)
        propId.setText(prop)

    }
}