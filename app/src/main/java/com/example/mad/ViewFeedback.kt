package com.example.mad

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mad.LoginAndSignUp.UserProfile
import com.example.mad.databinding.ActivityViewFeedbackBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewFeedback : AppCompatActivity() {
    private lateinit var binding: ActivityViewFeedbackBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewFeedbackBinding.inflate(layoutInflater)
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


        binding.saveBtn.setOnClickListener {
            val dis = binding.etNewDescription.text.toString()

            if (dis.isEmpty()) {
                if (dis.isEmpty()) {
                    binding.etNewDescription.error = "Enter feedback"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
            } else {
                val map = HashMap<String, Any>()

                //add data to hashMap
                map["dis"] = dis
                map["ratingScore"] = ratingScore.toString()


                //update database from hashMap
                databaseRef.child(id).updateChildren(map).addOnCompleteListener {
                    if (it.isSuccessful) {
                        intent = Intent(applicationContext, UserProfile::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
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
        val delete=findViewById<Button>(R.id.deleteBtn)
        delete.setOnClickListener {
            DataDelete(id)
        }

    }

    fun DataDelete(id:String){
        val databaseRef = FirebaseDatabase.getInstance().getReference("feedback").child(id)
        val mTask = databaseRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Data deleted successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this, UserProfile::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to delete data", Toast.LENGTH_LONG).show()
        }
    }
}