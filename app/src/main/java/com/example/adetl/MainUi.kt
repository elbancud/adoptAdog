package com.example.adetl

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainUi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ui)

        setHeader()
        adopt()
        displayAdopt()
    }
    fun adopt(){
        val adoptBtn = findViewById<Button>(R.id.adopt_btn)
        val uName = intent.getStringExtra("userName");

        adoptBtn.setOnClickListener() {
            val intent = Intent(this, adoptionUi::class.java);
            intent.putExtra("uNamePass", uName )
            startActivity(intent);
        }
    }
    fun displayAdopt(){
        val viewAdoption = findViewById<Button>(R.id.viewAdoptions_btn)

        viewAdoption.setOnClickListener() {

            var intentFetch = intent.getStringExtra("userName");

            val mContext = this

            val database = FirebaseDatabase.getInstance().getReference("Users").child(intentFetch).child("adoption")
            var getData = object : ValueEventListener {
                //error handling
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
                //get snapshot
                override fun onDataChange(snapshot: DataSnapshot) {

                    var nameStore = StringBuilder()
                    var breedStore = StringBuilder()

                    for (i in snapshot.children) {

                        var name: String = i.child("name").getValue().toString()
                        var breed: String = i.child("userName").getValue().toString()
                        nameStore.append(name+ " ")
                        breedStore.append(name+ " ")


                    }

                    val intent = Intent(mContext, displayAdoptionUi::class.java)
                    intent.putExtra("nameArray", nameStore.toString())
                    intent.putExtra("breedArray", breedStore.toString())

                    startActivity(intent)
                }

            }
            database.addValueEventListener(getData);
            database.addListenerForSingleValueEvent(getData);
        }
    }

    fun setHeader(){
        val header = findViewById<TextView>(R.id.header);

        val firstNameIntentFetch = intent.getStringExtra("firstNameIntent");
        header.text = "What's good " +firstNameIntentFetch.toString();

    }




}

