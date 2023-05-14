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
import com.example.mad.Activities.AddInquiriesActivity
import com.example.mad.Activities.ShowdataActivity
import com.example.mad.LoginAndSignUp.UserProfile
import com.google.firebase.database.FirebaseDatabase

class PropertyDetailsActivityTenant : AppCompatActivity() {
    private lateinit var tvPropId: TextView
    private lateinit var tvPropTittle: TextView
    private lateinit var tvPropLoc: TextView
    private lateinit var tvPropDis: TextView
    private lateinit var tvPropPrice: TextView
    private lateinit var tvPropTenant: TextView
    private lateinit var btnBill: Button
    private lateinit var btnInq: Button
    private lateinit var btnAddInq: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.property_details_tenant)

        initView()
        setValuesToViews()

        btnBill = findViewById(R.id.btnBill)
        btnBill.setOnClickListener{
            val prop = intent.getStringExtra("PropId").toString()
            val intent = Intent(this, FetchBillTenant::class.java)
            intent.putExtra("PropId", prop)
            startActivity(intent)
        }
        btnInq = findViewById(R.id.btnInq)
        btnInq.setOnClickListener{
            val prop = intent.getStringExtra("PropId").toString()
            val intent = Intent(this, ShowdataActivity::class.java)
            intent.putExtra("PropId", prop)
            startActivity(intent)
        }
        btnAddInq = findViewById(R.id.btnAddInq)
        btnAddInq.setOnClickListener{
            val prop = intent.getStringExtra("PropId").toString()
            val intent = Intent(this, AddInquiriesActivity::class.java)
            intent.putExtra("PropId", prop)
            startActivity(intent)
        }
    }

    private fun initView() {
        tvPropId = findViewById(R.id.tvPropId)
        tvPropTittle = findViewById(R.id.tvPropTittle)
        tvPropLoc = findViewById(R.id.tvPropLoc)
        tvPropDis = findViewById(R.id.tvPropDis)
        tvPropPrice = findViewById(R.id.tvPropPrice)
        tvPropTenant = findViewById(R.id.tvPropTenant)
    }

    private fun setValuesToViews() {
        tvPropId.text = intent.getStringExtra("PropId")
        tvPropTittle.text = intent.getStringExtra("PropTittle")
        tvPropLoc.text = intent.getStringExtra("PropLoc")
        tvPropDis.text = intent.getStringExtra("PropDis")
        tvPropPrice.text = intent.getStringExtra("PropPrice")
        tvPropTenant.text = intent.getStringExtra("PropTenant")
    }
}