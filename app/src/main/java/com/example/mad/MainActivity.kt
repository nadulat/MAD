package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var btnInsertData: Button
    private lateinit var btnFetchData: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInsertData = findViewById(R.id.btnInsertData)
        btnFetchData = findViewById(R.id.btnFetchData)

        btnInsertData.setOnClickListener{
            val prop = intent.getStringExtra("PropId").toString()
            val intent = Intent(this, InsertBill::class.java)
            intent.putExtra("PropId", prop)
            startActivity(intent)
        }
        btnFetchData.setOnClickListener {
            val prop = intent.getStringExtra("PropId").toString()
            val intent = Intent(this, FetchBill::class.java)
            intent.putExtra("PropId", prop)
            startActivity(intent)
        }
    }

}