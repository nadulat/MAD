package com.example.mad.LoginAndSignUp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mad.UsersModel
import com.example.mad.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        binding.register.setOnClickListener {
            val Email = binding.etEmail.text.toString()
            val Password = binding.etPassword.text.toString()
            val userEmail = binding.etEmail.text.toString()
            val userMobile = binding.etPhone.text.toString()
            val name = binding.etName.text.toString()

            if (Email.isNotEmpty() && Password.isNotEmpty() && userEmail.isNotEmpty()  && userMobile.isNotEmpty() && name.isNotEmpty() && (userMobile.length==10)  && (Password.length>5)) {

                firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(this) {task->

                    if (task.isSuccessful) {
                        Toast.makeText(this,"SIGN UP SUCCESS", Toast.LENGTH_LONG).show()

                        val auth1=firebaseAuth.currentUser
                        val userId=auth1?.uid

                        val dataInsert= UsersModel(userId,name,userMobile,userEmail)

                        val intent= Intent(this, ActivitySignIn::class.java)
                        intent.putExtra("UID",userId)
                        startActivity(intent)

                        dbRef.child(userId!!).setValue(dataInsert).addOnSuccessListener {
                            Toast.makeText(this,"AthorData Added", Toast.LENGTH_LONG).show()
                        }.addOnFailureListener {err->
                            Toast.makeText(this,"AthorData not Added ${err}", Toast.LENGTH_LONG).show()
                        }

                    } else {
                        Toast.makeText(this, "Login fail", Toast.LENGTH_LONG).show()
                    }

                }

            } else {
                if (Email.isEmpty()){
                    Toast.makeText(this,"Please fill Email", Toast.LENGTH_LONG).show()
                }
                if (Password.isEmpty()){
                    Toast.makeText(this,"Please fill password", Toast.LENGTH_LONG).show()
                }
                if(userEmail.isEmpty()){
                    Toast.makeText(this,"Please fill Email", Toast.LENGTH_LONG).show()
                }
                if(userMobile.isEmpty()){
                    Toast.makeText(this,"Please fill Mobile number", Toast.LENGTH_LONG).show()
                }
                if((userMobile.length>10) && (userMobile.length<10)){
                    Toast.makeText(this,"Please enter valid mobile number", Toast.LENGTH_LONG).show()
                }
                if( Password.length<6){
                    Toast.makeText(this,"password length max for 6", Toast.LENGTH_LONG).show()
                }
            }


        }



    }

}