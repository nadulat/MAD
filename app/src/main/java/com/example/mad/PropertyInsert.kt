package com.example.mad

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PropertyInsert : AppCompatActivity() {
    private lateinit var etPropTitle: EditText
    private lateinit var etPropDis: EditText
    private lateinit var etPropLoc: EditText
    private lateinit var etPropPrice: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.property_insert)

        etPropTitle = findViewById(R.id.etPropTitle)
        etPropLoc = findViewById(R.id.etPropLoc)
        etPropDis = findViewById(R.id.etPropDis)
        etPropPrice = findViewById(R.id.etPropPrice)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Properties")

        btnSaveData.setOnClickListener {
            savePropertyData()
        }
    }

    private fun savePropertyData(){
        val propTitle = etPropTitle.text.toString()
        val propLoc = etPropLoc.text.toString()
        val propDis = etPropDis.text.toString()
        val propPrice = etPropPrice.text.toString()

        if(propTitle.isEmpty()){
            etPropTitle.error = "Please Enter Property Title"
        }
        if(propLoc.isEmpty()){
            etPropLoc.error = "Please Enter Property Location"
        }
        if(propDis.isEmpty()){
            etPropDis.error = "Please Enter Property Description"
        }
        if(propPrice.isEmpty()){
            etPropPrice.error = "Please Enter Property Price"
        }

        val oId = intent.getStringExtra("uid")
        val pId = dbRef.push().key!!

        val property = PropertyModel(pId, propTitle, propLoc, propDis, propPrice, oId)

        dbRef.child(pId).setValue(property)
            .addOnCompleteListener{
                Toast.makeText(this, "Data Successfully Inserted", Toast.LENGTH_LONG).show()

                etPropTitle.text.clear()
                etPropLoc.text.clear()
                etPropDis.text.clear()
                etPropPrice.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}