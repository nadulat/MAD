package com.example.mad

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mad.LoginAndSignUp.UserProfile
import com.google.firebase.database.FirebaseDatabase

class BillDetails : AppCompatActivity() {
    private lateinit var tvBillId: TextView
    private lateinit var tvBillMonth: TextView
    private lateinit var tvCurrent: TextView
    private lateinit var tvWater: TextView
    private lateinit var tvProp: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bill_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("BillID").toString(),
                intent.getStringExtra("Month").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("BillId").toString()
            )
        }
    }

    private fun initView() {
        tvBillId = findViewById(R.id.tvBillId)
        tvBillMonth = findViewById(R.id.tvBillMonth)
        tvCurrent = findViewById(R.id.tvCurrent)
        tvWater = findViewById(R.id.tvWater)
        tvProp = findViewById(R.id.tvProp)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvBillId.text = intent.getStringExtra("BillId")
        tvBillMonth.text = intent.getStringExtra("Month")
        tvCurrent.text = intent.getStringExtra("Current")
        tvWater.text = intent.getStringExtra("Water")
        tvProp.text = intent.getStringExtra("Prop")
    }

    private fun deleteRecord(
        id: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Bills").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Bill data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, UserProfile::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        billId: String,
        Month: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etId = mDialogView.findViewById<EditText>(R.id.etId)
        val etMonth = mDialogView.findViewById<EditText>(R.id.etMonth)
        val etCurrent = mDialogView.findViewById<EditText>(R.id.etCurrent)
        val etWater = mDialogView.findViewById<EditText>(R.id.etWater)
        val etProp = mDialogView.findViewById<EditText>(R.id.etProp)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etId.setText(intent.getStringExtra("BillId").toString())
        etMonth.setText(intent.getStringExtra("Month").toString())
        etCurrent.setText(intent.getStringExtra("Current").toString())
        etWater.setText(intent.getStringExtra("Water").toString())
        etProp.setText(intent.getStringExtra("Prop").toString())

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                etId.text.toString(),
                etMonth.text.toString(),
                etCurrent.text.toString(),
                etWater.text.toString(),
                etProp.text.toString()
            )

            Toast.makeText(applicationContext, "Bill Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our text views
            tvBillMonth.text = etMonth.text.toString()
            tvCurrent.text = etCurrent.text.toString()
            tvWater.text = etWater.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        month: String,
        current: String,
        water: String,
        prop: String
    ) {
        Log.d(ContentValues.TAG, "Id - ${id.toString()}")
        val dbRef = FirebaseDatabase.getInstance().getReference("Bills").child(id)
        val bill = BillModel(id, month, current, water, prop)
        dbRef.setValue(bill).addOnCompleteListener {
            Log.d(ContentValues.TAG, "After update")

        }
    }

}
