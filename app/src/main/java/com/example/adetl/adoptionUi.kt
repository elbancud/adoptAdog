

package com.example.adetl

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder

class adoptionUi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adoption_ui)
        jemAdoption()
        tarubAdoption()
        centVinceAdoption()
        aiceAdoption()
    }
    fun jemAdoption(){
        val jemBtn = findViewById<Button>(R.id.adoptJem_btn);
        jemBtn.setOnClickListener() {
            checkExistence("jem", "Affenpinscher", "male", "1", "2020-02-02")
        }
    }
    fun tarubAdoption(){

        val tarubBtn = findViewById<Button>(R.id.adoptTarub_btn);

        tarubBtn.setOnClickListener() {
            checkExistence("tarub", "foxhound", "male", "2", "2019-09-01")
        }
    }

    fun centVinceAdoption(){
        val centBtn = findViewById<Button>(R.id.adoptCentVince_btn);
        centBtn.setOnClickListener() {
            checkExistence("centvince", "malamute", "female", "3", "2018-02-15")
        }
    }

    fun aiceAdoption(){
        val aiceBtn = findViewById<Button>(R.id.adoptAice_btn);
        aiceBtn.setOnClickListener() {
            checkExistence("aice", "siberian", "female", "4m", "2021-12-15")
        }
    }
    //alert
    fun dialogUpdate(message: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Adoption status");

        builder.setMessage(message)

        builder.setPositiveButton("Continue", DialogInterface.OnClickListener { dialog, which ->

            var intentFetch = intent.getStringExtra("uNamePass");

            val mContext = this

            val sbName = ""
            var sbBreed = ""
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



        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    fun checkExistence(dogName: String, breed: String, gender: String, age: String, dob: String) {
        var intentFetch = intent.getStringExtra("uNamePass");
        val database = FirebaseDatabase.getInstance().getReference("Users").child(intentFetch).child("adoption")

        database.orderByChild("name").equalTo(dogName).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    dialogUpdate("Oops! You already adopted this dog")
                } else {
                    val database = FirebaseDatabase.getInstance().reference
                    var intentFetch = intent.getStringExtra("uNamePass");

                    database.child("Users").child(intentFetch).child("adoption").child(dogName).setValue(dogInfo(dogName, breed, gender, age, dob))

                    dialogUpdate("Success! You are now a pet owner, continue to display")

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }



}