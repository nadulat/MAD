package com.example.mad.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mad.BillModel
import com.example.mad.LoginAndSignUp.UserProfile
import com.example.mad.ModelClassInquiry
import com.example.mad.PropertyDetailsActivityTenant
import com.example.mad.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddInquiriesActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inquiries)



        dbRef=FirebaseDatabase.getInstance().getReference("Inquiry")//.child(UID)

        var inquiry=findViewById<EditText>(R.id.inquiry)
        var submit=findViewById<Button>(R.id.submit)
        var cancel=findViewById<Button>(R.id.CANCEL)

        submit.setOnClickListener{

            var Inquiry=inquiry.text.toString()

            if (Inquiry.isNotEmpty()){

                val id=dbRef.push().key!!
                val prop = intent.getStringExtra("PropId").toString()
                val data= ModelClassInquiry(id,Inquiry, prop)

                dbRef.child(id).setValue(data).addOnCompleteListener{
                    Toast.makeText(this,"Data insert succesfully", Toast.LENGTH_LONG).show()

                         val intent=Intent(this, UserProfile::class.java)
                         startActivity(intent)

                }.addOnFailureListener{ err->
                    Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
                }


            }else{
                Toast.makeText(this,"Please fill Empty fields",Toast.LENGTH_LONG).show()
            }



        }

    }
}