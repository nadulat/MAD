package com.example.mad.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.Adapter.inquiryAdpter
import com.example.mad.ModelClassInquiry
import com.google.firebase.database.*
import com.example.mad.R

class ShowdataActivityOwner : AppCompatActivity() {

    private lateinit var employeeItem: RecyclerView
    private lateinit var empList:ArrayList<ModelClassInquiry>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showdata_owner)

        employeeItem=findViewById(R.id.rId)
        employeeItem.layoutManager= LinearLayoutManager(this,)
        employeeItem.setHasFixedSize(true)
        empList= arrayListOf<ModelClassInquiry>()

        getEmployeeData()
    }
    private fun getEmployeeData(){
        employeeItem.visibility= View.GONE
        dbRef= FirebaseDatabase.getInstance().getReference("Inquiry")

        val propId = intent.getStringExtra("PropId")

        val fetch = dbRef.orderByChild("prop").equalTo(propId)

        fetch.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if(snapshot.exists()){
                    for (inquiry in snapshot.children){
                        val inqData=inquiry.getValue(ModelClassInquiry::class.java)
                        empList.add(inqData!!)
                    }
                    val mAdapter= inquiryAdpter(empList)

                    mAdapter.setOnItemClickListener(object:inquiryAdpter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent=Intent(this@ShowdataActivityOwner, InquiryOwner::class.java)
                            //put extras
                            intent.putExtra("id",empList[position].Yourid)
                            intent.putExtra("inquiry",empList[position].inquiry)
                            intent.putExtra("prop", empList[position]. prop)
                            startActivity(intent)
                        }
                    })

                    employeeItem.adapter=mAdapter
                    employeeItem.visibility= View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }

        })
    }
}