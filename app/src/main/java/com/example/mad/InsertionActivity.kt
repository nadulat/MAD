package com.example.mad

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpDis: EditText
    private lateinit var etEmpPrice: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpDis = findViewById(R.id.etEmpDis)
        etEmpPrice = findViewById(R.id.etEmpPrice)
        btnSaveData = findViewById(R.id.btnSave)



        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }
    private fun saveEmployeeData() {

        //getting values
        val empName = etEmpName.text.toString()
        val empAge = etEmpDis.text.toString()
        val empSalary = etEmpPrice.text.toString()

        if (empName.isEmpty()) {
            etEmpName.error = "Please enter name"
        }
        if (empAge.isEmpty()) {
            etEmpDis.error = "Please enter age"
        }
        if (empSalary.isEmpty()) {
            etEmpPrice.error = "Please enter salary"
        }

        //val empId = dbRef.push().key!!
        val uid=intent.getStringExtra("uid")
        val employee = EmployeeModel(uid.toString(), empName, empAge, empSalary)

        dbRef.child(uid.toString()).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etEmpName.text.clear()
                etEmpDis.text.clear()
                etEmpPrice.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}