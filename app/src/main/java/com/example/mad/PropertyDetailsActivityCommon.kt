package com.example.mad.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mad.*
import com.google.firebase.database.FirebaseDatabase

class PropertyDetailsActivityCommon : AppCompatActivity() {

    private lateinit var tvPropId : TextView
    private lateinit var tvPropTittle: TextView
    private lateinit var tvPropLoc: TextView
    private lateinit var tvPropDis: TextView
    private lateinit var tvPropPrice: TextView
    private lateinit var tvPropOwner: TextView
    private lateinit var tvPropTenant: TextView

    private lateinit var btnRent: Button
    private lateinit var btnReviews: Button
    private lateinit var btnYourReview: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.property_details_common)

        initView()
        setValuesToViews()

        btnRent = findViewById(R.id.btnRent)
        btnReviews = findViewById(R.id.btnReviews)
        btnYourReview = findViewById(R.id.btnYourReviews)

        btnReviews.setOnClickListener {
            val prop = intent.getStringExtra("PropId").toString()
            val uid = intent.getStringExtra("uid").toString()
            val intent = Intent(this, ViewAllFeedbacks::class.java)
            intent.putExtra("PropId", prop)
            intent.putExtra("uid", uid)
            startActivity(intent)
        }
        btnRent.setOnClickListener{
            openRentDialog(
                intent.getStringExtra("uid").toString()
            )
        }
        btnYourReview.setOnClickListener{
            val prop = intent.getStringExtra("PropId").toString()
            val uid = intent.getStringExtra("uid").toString()
            val intent = Intent(this, ViewAllFeedbackUser::class.java)
            intent.putExtra("PropId", prop)
            intent.putExtra("uid", uid)
            startActivity(intent)
        }
    }

    private fun initView() {
        tvPropId = findViewById(R.id.tvPropId)
        tvPropTittle = findViewById(R.id.tvPropTittle)
        tvPropLoc = findViewById(R.id.tvPropLoc)
        tvPropDis = findViewById(R.id.tvPropDis)
        tvPropPrice = findViewById(R.id.tvPropPrice)
        tvPropOwner = findViewById(R.id.tvPropOwner)
        tvPropTenant = findViewById(R.id.tvPropTenant)
    }

    private fun setValuesToViews() {
        tvPropId.text = intent.getStringExtra("PropId")
        tvPropTittle.text = intent.getStringExtra("PropTittle")
        tvPropLoc.text = intent.getStringExtra("PropLoc")
        tvPropDis.text = intent.getStringExtra("PropDis")
        tvPropPrice.text = intent.getStringExtra("PropPrice")
        tvPropOwner.text = intent.getStringExtra("PropOwner")
        tvPropTenant.text = intent.getStringExtra("PropTenant")
    }

    private fun openRentDialog(
        uid: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.rent_dialog, null)

        mDialog.setView(mDialogView)

        val etPropId = mDialogView.findViewById<EditText>(R.id.etPropId)
        val etPropTittle = mDialogView.findViewById<EditText>(R.id.etPropTittle)
        val etPropLoc = mDialogView.findViewById<EditText>(R.id.etPropLoc)
        val etPropDis = mDialogView.findViewById<EditText>(R.id.etPropDis)
        val etPropPrice = mDialogView.findViewById<EditText>(R.id.etPropPrice)
        val etPropOwner = mDialogView.findViewById<EditText>(R.id.etPropOwner)
        val etPropTenant = mDialogView.findViewById<EditText>(R.id.etPropTenant)

        val btnYes = mDialogView.findViewById<Button>(R.id.btnYes)

        etPropId.setText(intent.getStringExtra("PropId").toString())
        etPropTittle.setText(intent.getStringExtra("PropTittle").toString())
        etPropLoc.setText(intent.getStringExtra("PropLoc").toString())
        etPropDis.setText(intent.getStringExtra("PropDis").toString())
        etPropPrice.setText(intent.getStringExtra("PropPrice").toString())
        etPropOwner.setText(intent.getStringExtra("PropOwner").toString())
        etPropTenant.setText(intent.getStringExtra("PropTenant").toString())

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnYes.setOnClickListener {
            updateEmpData(
                etPropId.text.toString(),
                etPropTittle.text.toString(),
                etPropLoc.text.toString(),
                etPropDis.text.toString(),
                etPropPrice.text.toString(),
                etPropOwner.text.toString(),
                etPropTenant.text.toString()
            )

            Toast.makeText(applicationContext, "Property Data Updated", Toast.LENGTH_LONG).show()

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
        tenant : String
    ) {
        Log.d(ContentValues.TAG, "Id - ${id.toString()}")
        val dbRef = FirebaseDatabase.getInstance().getReference("Properties").child(id)
        val tenant = intent.getStringExtra("uid")
        val propInfo = PropertyModel(id, tittle, loc, dis, price, owner, tenant)
        dbRef.setValue(propInfo).addOnCompleteListener {
            Log.d(ContentValues.TAG, "After update")
        }
    }
}