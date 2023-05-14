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

class UpdateDelete : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)

        val inquiry=findViewById<TextView>(R.id.UDinquiry)
        val id=findViewById<TextView>(R.id.UDid)
        val propId=findViewById<TextView>(R.id.propId)

        val update=findViewById<Button>(R.id.update)
        val delete=findViewById<Button>(R.id.delete)

        val UID=intent.getStringExtra("UID").toString()

        database=FirebaseDatabase.getInstance().getReference("Inquiry").child(UID)

        val pid = intent.getStringExtra("id").toString()
        val pInquiry = intent.getStringExtra("inquiry").toString()
        val prop = intent.getStringExtra("prop").toString()
        id.setText(pid)
        inquiry.setText(pInquiry)
        propId.setText(prop)


        update.setOnClickListener {
            val newInquiry=inquiry.text.toString()

            updateDetail(newInquiry)
        }

    delete.setOnClickListener {
        DataDelete(pid)
    }

    }

    fun DataDelete(id:String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Inquiry").child(id)
        val mTask = dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Data deleted successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this, UserProfile::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to delete data", Toast.LENGTH_LONG).show()
        }
    }


    fun updateDetail(inquiry:String){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Inquiry")

        val updates= mapOf<String,String>(
            "inquiry" to inquiry)

        val id = intent.getStringExtra("id").toString()

        myRef.child(id).updateChildren(updates)
            .addOnSuccessListener {
                Toast.makeText(this,"update details Successful",Toast.LENGTH_LONG).show()
                val intent=Intent(this, UserProfile::class.java)
                startActivity(intent)
            }
            .addOnFailureListener{
                Toast.makeText(this,"Details Update Unsuccessful",Toast.LENGTH_LONG).show()
            }
    }
}