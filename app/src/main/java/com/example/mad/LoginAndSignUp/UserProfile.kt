package com.example.mad.LoginAndSignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mad.*
import com.example.mad.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserProfile : AppCompatActivity() {
    private lateinit var dbRef:DatabaseReference
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val userName=findViewById<TextView>(R.id.textView11)
        val logout=findViewById<Button>(R.id.btnLogout)
        val property=findViewById<Button>(R.id.btnDriver3)
        val properties=findViewById<Button>(R.id.btnDriver6)
        val ownedProperties=findViewById<Button>(R.id.btnDriver9)
        val rentedProperties=findViewById<Button>(R.id.btnDriver2)

        dbRef=FirebaseDatabase.getInstance().getReference("Users")
        auth= FirebaseAuth.getInstance()

        dbRef.child(auth.currentUser?.uid.toString()).addValueEventListener(
            object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue(UsersModel::class.java)!!
                   userName.setText(user?.UserName)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@UserProfile,"Error data fetch",Toast.LENGTH_LONG).show()
                }

            }
        )

        rentedProperties.setOnClickListener{
            val intent=Intent(this@UserProfile,FetchPropertyTenant::class.java)
            intent.putExtra("uid",auth.currentUser?.uid.toString())
            startActivity(intent)
        }

        ownedProperties.setOnClickListener{
            val intent=Intent(this@UserProfile,FetchPropertyOwner::class.java)
            intent.putExtra("uid",auth.currentUser?.uid.toString())
            startActivity(intent)
        }

        properties.setOnClickListener{
            val intent=Intent(this@UserProfile,FetchProperty::class.java)
            intent.putExtra("uid",auth.currentUser?.uid.toString())
            startActivity(intent)
        }

        property.setOnClickListener{
            val intent=Intent(this@UserProfile,PropertyInsert::class.java)
            intent.putExtra("uid",auth.currentUser?.uid.toString())
            startActivity(intent)
        }

        logout.setOnClickListener {
            auth.signOut()
            val intent=Intent(this, WelcomePage::class.java)
            startActivity(intent)
        }

    }
}