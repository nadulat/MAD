package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.databinding.ActivityViewFeedbcksUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewAllFeedbackUser : AppCompatActivity() {

    private lateinit var binding: ActivityViewFeedbcksUserBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<Feedback>()
    private lateinit var adapter: FeedbackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewFeedbcksUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("feedback")


        recyclerView = binding.recyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        retrieveData()
        adapter = FeedbackAdapter(mList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object: FeedbackAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, ViewFeedback::class.java).also {
                    it.putExtra("id", mList[position].id)
                    it.putExtra("des", mList[position].dis)
                    it.putExtra("rating", mList[position].ratingScore)
                    startActivity(it)
                }
            }
        })
    }


    private fun retrieveData() {
        val uid = intent.getStringExtra("uid").toString()
        val fetch = databaseRef.orderByChild("uid").equalTo(uid)
        fetch.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val req = snapshot.getValue(Feedback::class.java)!!
                    if( req != null){
                        mList.add(req)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewAllFeedbackUser, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}