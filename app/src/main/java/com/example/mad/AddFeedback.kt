package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mad.Feedback
import com.example.mad.databinding.ActivityAddFeedbackBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddFeedback : AppCompatActivity() {
    private lateinit var binding: ActivityAddFeedbackBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var ratingScore = 0

        database = FirebaseDatabase.getInstance()
        val uid= intent.getStringExtra("uid").toString()
        val propId= intent.getStringExtra("PropId").toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("feedback")

        binding.submitFeedbackBtn.setOnClickListener {
            val dis = binding.etNewDescription.text.toString()

            if (dis.isEmpty()) {
                if (dis.isEmpty()) {
                    binding.etNewDescription.error = "Enter feedback"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
            }else {
                var id = databaseRef.push().key!!
                val feedback: Feedback = Feedback(id,dis, ratingScore.toString(),uid,propId)
                databaseRef.child(id).setValue(feedback).addOnCompleteListener {
                    if (it.isSuccessful) {
                        intent = Intent(applicationContext, ViewAllFeedbacks::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Something went wrong, try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        binding.ratingBar.setOnRatingBarChangeListener { bar, fl, b ->

            when(bar.rating.toInt()){
                1-> ratingScore=1
                2-> ratingScore=2
                3-> ratingScore=3
                4-> ratingScore=4
                5-> ratingScore=5
                else -> ratingScore=0
            }
        }

    }
}