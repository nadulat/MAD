package com.example.mad

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertBill : AppCompatActivity(){
    private lateinit var etMonth: EditText
    private lateinit var etCurrent: EditText
    private lateinit var etWater: EditText
    private lateinit var btnSaveData: Button
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bill_insertion)

        etMonth = findViewById(R.id.etMonth)
        etCurrent = findViewById(R.id.etCurrent)
        etWater = findViewById(R.id.etWater)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Bills")

        btnSaveData.setOnClickListener {
            saveBillData()
        }
    }

    private fun saveBillData() {

        //getting values
        val current = etCurrent.text.toString()
        val water = etWater.text.toString()
        val month = etMonth.text.toString()

        if (current.isEmpty()) {
            etCurrent.error = "Please enter current bill"
        }
        if (water.isEmpty()) {
            etWater.error = "Please enter water bill"
        }
        if (month.isEmpty()) {
            etMonth.error = "Please enter month"
        }

        val billID = dbRef.push().key!!
        val prop= intent.getStringExtra("PropId").toString()
        val bill = BillModel(billID, month, current, water,prop)

        dbRef.child(billID).setValue(bill)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etMonth.text.clear()
                etCurrent.text.clear()
                etWater.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}