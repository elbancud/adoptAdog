
package com.example.adetl

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.collection.LLRBNode
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val signUp1 = findViewById<TextView>(R.id.signup1_link);
        val signUp2 = findViewById<TextView>(R.id.signup2_link);
        val login_Btn = findViewById<Button>(R.id.login_btn);
        val userName = findViewById<TextView>(R.id.username_text)
        val passwordEntered = findViewById<TextView>(R.id.password_text)

        val errorHandler = findViewById<TextView>(R.id.errorHandler);

        login_Btn.setOnClickListener() {

            if (userName.text.toString() == "" || passwordEntered.text.toString() == "") {
                errorHandler.text = "Please fill in the fields"
                errorHandler.setTextColor(Color.RED);
            } else {
                val database = FirebaseDatabase.getInstance().getReference("Users")
                val intent = Intent(this, MainUi::class.java);

                database.orderByChild("userName").equalTo(userName.text.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val passwordFromDb = snapshot.child(userName.text.toString()).child("passwordFinal").getValue().toString()
                            val firstNameSnapshot = snapshot.child(userName.text.toString()).child("firstName").getValue().toString()

                            if (passwordFromDb.equals(passwordEntered.text.toString())) {
                                var uName = userName.text.toString()

                                intent.putExtra("firstNameIntent", firstNameSnapshot)
                                intent.putExtra("userName", uName)

                                startActivity(intent);
                            } else {
                                errorHandler.text = "Username or password is incorrect"
                                errorHandler.setTextColor(Color.RED);
                            }
                        }else {
                            errorHandler.text = "Username or password is incorrect"
                            errorHandler.setTextColor(Color.RED);
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }
        }
        signUp1.setOnClickListener() {
            val intent = Intent(this, registration_form::class.java);
            startActivity(intent);
        }
        signUp2.setOnClickListener() {
            val intent = Intent(this, registration_form::class.java);
            startActivity(intent);
        }
    }



    }
