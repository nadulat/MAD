package com.example.mad.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mad.FetchingActivity
import com.example.mad.EmployeeModel
import com.example.mad.R
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpDis: TextView
    private lateinit var tvEmpPrice: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )

        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("empId").toString(),
            )
        }
    }
    private fun deleteRecord(empId: String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(empId)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Record Deleted Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FetchingActivity:: class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this, " Deleted error ${error.message}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun initView(){
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpDis = findViewById(R.id.tvEmpDis)
        tvEmpPrice = findViewById(R.id.tvEmpPrice)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)



    }
    private fun setValuesToViews(){
        tvEmpId.text = intent.getStringExtra("EmpId")
        tvEmpName.text = intent.getStringExtra("EmpName")
        tvEmpDis.text = intent.getStringExtra("EmpDis")
        tvEmpPrice.text = intent.getStringExtra("EmpPrice")


    }

    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(
        empId: String,
        empName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)
        val etId = mDialogView.findViewById<EditText>(R.id.etId)
        val etEmpName = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etEmpDis = mDialogView.findViewById<EditText>(R.id.etEmpDis)
        val etEmpPrice = mDialogView.findViewById<EditText>(R.id.etEmpPrice)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etId.setText(intent.getStringExtra("EmpId").toString())
        etEmpName.setText(intent.getStringExtra("EmpName").toString())
        etEmpDis.setText(intent.getStringExtra("EmpDis").toString())
        etEmpPrice.setText(intent.getStringExtra("EmpPrice").toString())

        mDialog.setTitle("Updating $empName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                etId.text.toString(),
                etEmpName.text.toString(),
                etEmpDis.text.toString(),
                etEmpPrice.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvEmpId.text = etId.text.toString()
            tvEmpName.text = etEmpName.text.toString()
            tvEmpDis.text = etEmpDis.text.toString()
            tvEmpPrice.text = etEmpPrice.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        name: String,
        dis: String,
        price: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = EmployeeModel(id, name, dis, price)
        dbRef.setValue(empInfo)
    }

    }
