package com.example.adetl

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder


class registration_form : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_form)

        val database = FirebaseDatabase.getInstance().reference


        val join1 = findViewById<TextView>(R.id.join1_link);
        val join2 = findViewById<TextView>(R.id.join2_link);
        val submitBtn = findViewById<Button>(R.id.submitReg_btn);
        val fName = findViewById<TextView>(R.id.firstName_text);
        val mName = findViewById<TextView>(R.id.middleName_text);
        val lName = findViewById<TextView>(R.id.lastName_text);
        val uName = findViewById<TextView>(R.id.userName_text);
        val password = findViewById<TextView>(R.id.passwordReg_text);
        val passwordRetype = findViewById<TextView>(R.id.passwordConfirm_text);

        val errorHandler = findViewById<TextView>(R.id.errorHandlerReg_text);
        var userExistenceArray = mutableListOf<String>()


        join1.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }
        join2.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }
        //check username Existence

        var getData = object : ValueEventListener {
            //error handling
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            //get snapshot
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children) {
                    //fetch username value
                    var userName:String = i.child("userName").getValue().toString()
                    //test username existence

                    userExistenceArray.add(userName)
                }

            }
        }


        //button submit


        submitBtn.setOnClickListener() {
            var fNameVal = fName.text.toString();
            var mNameVal = mName.text.toString();
            var lNameVal = lName.text.toString();
            var uNameVal = uName.text.toString();
            var passwordVal = password.text.toString();
            var passwordRetypeVal = passwordRetype.text.toString();
            var userValid = false;

            // Write a message to the database
            // Write a message to the database
            for (i in userExistenceArray) {
               if(i == uNameVal) {
                   userValid = true;
               }

            }
            if (fNameVal == "" ) {
                errorHandler.text = "Please enter first name";
                errorHandler.setTextColor(Color.RED);
            }
            else if (mNameVal == "" ){
                errorHandler.text = "Please enter middle name ";
                errorHandler.setTextColor(Color.RED);
            }else if ( lNameVal == ""){
                errorHandler.text = "Please enter last name ";
                errorHandler.setTextColor(Color.RED);
            }else if ( uNameVal == ""  ){
                errorHandler.text = "Please enter username ";
                errorHandler.setTextColor(Color.RED);
            }else if (passwordVal == "" ){
                errorHandler.text = "Please enter password ";
                errorHandler.setTextColor(Color.RED);
            }else if ( passwordRetypeVal == ""){
                errorHandler.text = "Please enter password ";
                errorHandler.setTextColor(Color.RED);
            }else if (uNameVal.length <8  ){
                errorHandler.text = "Username should atleast be 3 characters ";
                errorHandler.setTextColor(Color.RED);
            }
            else if (passwordVal != passwordRetypeVal){
                errorHandler.text = "Password does not match";
                errorHandler.setTextColor(Color.RED);
            }else if (passwordVal.length <8) {
                errorHandler.text = "Password should atleast be 8 characters ";
                errorHandler.setTextColor(Color.RED);
            }else if (userValid) {
                errorHandler.text = "Username already existing";
                errorHandler.setTextColor(Color.RED);
            }
            else {
                errorHandler.text = "Success"
                errorHandler.setTextColor(Color.GREEN);
                var uniqueId = "User-"+(1..1000).shuffled().first().toString()

                database.child("Users").child(uNameVal).setValue(userInfo(fNameVal,mNameVal,lNameVal,uNameVal,passwordVal))

                fName.setText("");
                mName.setText("");
                lName.setText("");
                uName.setText("");
                password.setText("");
                passwordRetype.setText("");

                val intent = Intent(this, MainActivity::class.java);
                startActivity(intent);
            }
        }
        database.addValueEventListener(getData);
        database.addListenerForSingleValueEvent(getData);

    }
}