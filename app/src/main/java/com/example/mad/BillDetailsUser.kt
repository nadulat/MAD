package com.example.mad


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Currency

class BillDetailsUser : AppCompatActivity() {

        private lateinit var tvBillId: TextView
        private lateinit var tvBillMonth: TextView
        private lateinit var tvCurrent: TextView
        private lateinit var tvWater: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.bill_details_user)

            initView()
            setValuesToViews()
        }

        private fun initView() {
            tvBillId = findViewById(R.id.tvBillId)
            tvBillMonth = findViewById(R.id.tvBillMonth)
            tvCurrent = findViewById(R.id.tvCurrent)
            tvWater = findViewById(R.id.tvWater)
        }

        private fun setValuesToViews() {
            tvBillId.text = intent.getStringExtra("BillId")
            tvBillMonth.text = intent.getStringExtra("Month")
            tvCurrent.text = intent.getStringExtra("Current")
            tvWater.text = intent.getStringExtra("Water")

        }
}