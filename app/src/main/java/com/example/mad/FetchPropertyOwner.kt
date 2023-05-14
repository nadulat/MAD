package com.example.mad


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FetchPropertyOwner : AppCompatActivity() {
    private lateinit var propRecyclerView: RecyclerView
    private lateinit var tvNoData: TextView
    private lateinit var propList: ArrayList<PropertyModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.property_fetching)

        propRecyclerView = findViewById(R.id.rvProp)
        propRecyclerView.layoutManager = LinearLayoutManager(this)
        propRecyclerView.setHasFixedSize(true)
        tvNoData = findViewById(R.id.tvNoData)

        propList = arrayListOf<PropertyModel>()

        getPropertyData()
    }

    private fun getPropertyData(){
        propRecyclerView.visibility = View.GONE
        tvNoData.visibility = View.VISIBLE

        val oId = intent.getStringExtra("uid")

        dbRef = FirebaseDatabase.getInstance().getReference("Properties")

        val fetch = dbRef.orderByChild("propOwner").equalTo(oId)

        fetch.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                propList.clear()
                if (snapshot.exists()){
                    for(propSnap in snapshot.children){
                        val propData = propSnap.getValue(PropertyModel::class.java)
                        propList.add(propData!!)
                    }
                    val mAdapter = PropertyAdapter(propList)
                    propRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object: PropertyAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchPropertyOwner,  PropertyDetailsActivityOwner::class.java)
                            intent.putExtra("PropId", propList[position].propId)
                            intent.putExtra("PropTittle", propList[position].propTitle)
                            intent.putExtra("PropLoc", propList[position].propLoc)
                            intent.putExtra("PropDis", propList[position].propDis)
                            intent.putExtra("PropPrice", propList[position].propPrice)
                            intent.putExtra("PropOwner", propList[position].propOwner)
                            startActivity(intent)
                        }
                    })
                    propRecyclerView.visibility = View.VISIBLE
                    tvNoData.visibility = View.GONE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, "onCancelled: ${error.message}")
            }
        })
    }
}