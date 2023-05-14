package com.example.mad

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mad.Activities.ShowdataActivityOwner
import com.example.mad.LoginAndSignUp.UserProfile
import com.google.firebase.database.FirebaseDatabase

class PropertyDetailsActivityOwner : AppCompatActivity(){
    private lateinit var tvPropId: TextView
    private lateinit var tvPropTittle: TextView
    private lateinit var tvPropLoc: TextView
    private lateinit var tvPropDis: TextView
    private lateinit var tvPropPrice: TextView
    private lateinit var tvPropOwner: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnBill: Button
    private lateinit var btnInq: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.property_details_owner)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("propId").toString(),
                intent.getStringExtra("propTitle").toString()
            )
        }
        btnDelete = findViewById(R.id.btnDelete)
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("PropId").toString()
            )
        }

        btnBill = findViewById(R.id.btnBill)
        btnBill.setOnClickListener{
            val prop = intent.getStringExtra("PropId").toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("PropId", prop)
            startActivity(intent)
        }

        btnInq=findViewById(R.id.btnInq)
        btnInq.setOnClickListener{
            val prop = intent.getStringExtra("PropId").toString()
            val intent = Intent(this, ShowdataActivityOwner::class.java)
            intent.putExtra("PropId", prop)
            startActivity(intent)
        }

        initView()
        setValuesToViews()
    }

    private fun initView() {
        tvPropId = findViewById(R.id.tvPropId)
        tvPropTittle = findViewById(R.id.tvPropTittle)
        tvPropLoc = findViewById(R.id.tvPropLoc)
        tvPropDis = findViewById(R.id.tvPropDis)
        tvPropPrice = findViewById(R.id.tvPropPrice)
        tvPropOwner = findViewById(R.id.tvPropOwner)
        btnUpdate = findViewById(R.id.btnUpdate)
    }

    private fun setValuesToViews() {
        tvPropId.text = intent.getStringExtra("PropId")
        tvPropTittle.text = intent.getStringExtra("PropTittle")
        tvPropLoc.text = intent.getStringExtra("PropLoc")
        tvPropDis.text = intent.getStringExtra("PropDis")
        tvPropPrice.text = intent.getStringExtra("PropPrice")
        tvPropOwner.text = intent.getStringExtra("PropOwner")
    }

    private fun deleteRecord(
        id: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Properties").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Property data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, UserProfile::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun openUpdateDialog(        propId: String,
        propTittle: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.property_update_dialog, null)

        mDialog.setView(mDialogView)

        val etPropId = mDialogView.findViewById<EditText>(R.id.etPropId)
        val etPropTittle = mDialogView.findViewById<EditText>(R.id.etPropTittle)
        val etPropLoc = mDialogView.findViewById<EditText>(R.id.etPropLoc)
        val etPropDis = mDialogView.findViewById<EditText>(R.id.etPropDis)
        val etPropPrice = mDialogView.findViewById<EditText>(R.id.etPropPrice)
        val etPropOwner = mDialogView.findViewById<EditText>(R.id.etPropOwner)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etPropId.setText(intent.getStringExtra("PropId").toString())
        etPropTittle.setText(intent.getStringExtra("PropTittle").toString())
        etPropLoc.setText(intent.getStringExtra("PropLoc").toString())
        etPropDis.setText(intent.getStringExtra("PropDis").toString())
        etPropPrice.setText(intent.getStringExtra("PropPrice").toString())
        etPropOwner.setText(intent.getStringExtra("PropOwner").toString())

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                etPropId.text.toString(),
                etPropTittle.text.toString(),
                etPropLoc.text.toString(),
                etPropDis.text.toString(),
                etPropPrice.text.toString(),
                etPropOwner.text.toString(),
            )

            Toast.makeText(applicationContext, "Property Data Updated", Toast.LENGTH_LONG).show()


            tvPropTittle.text = etPropTittle.text.toString()
            tvPropLoc.text = etPropLoc.text.toString()
            tvPropDis.text = etPropDis.text.toString()
            tvPropPrice.text = etPropPrice.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        tittle: String,
        loc : String,
        dis: String,
        price: String,
        owner : String,
    ) {
        Log.d(ContentValues.TAG, "Id - ${id.toString()}")
        val dbRef = FirebaseDatabase.getInstance().getReference("Properties").child(id)
        val propInfo = PropertyModel(id, tittle, loc, dis, price, owner)
        dbRef.setValue(propInfo).addOnCompleteListener {
            Log.d(ContentValues.TAG, "After update")
    }
}}