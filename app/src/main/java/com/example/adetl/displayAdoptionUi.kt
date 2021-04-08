package com.example.adetl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder


class displayAdoptionUi : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_adoption_ui)

        this.layoutManager = LinearLayoutManager(this)




        dogList()

    }
    fun dogList(){

        var intentNameFetch = intent.getStringExtra("nameArray").split(" ")
        var intentBreedFetch = intent.getStringExtra("breedArray").split(" ")
        var image = mutableListOf<Int>()

        var intentBreedFetchToArray = mutableListOf<String>()
        var intentNameFetchToArray = mutableListOf<String>()

        for (dataName in intentNameFetch) {
            if (dataName == "jem") {
                intentNameFetchToArray.add("jem")
            }else if(dataName== "aice") {
                intentNameFetchToArray.add("aice")
            }else if(dataName == "tarub") {
                intentNameFetchToArray.add("tarub")
            }else if(dataName == "centvince") {
                intentNameFetchToArray.add("centvince")
            }
        }
        for (dataBreed in intentBreedFetch) {
            if (dataBreed == "jem") {
                intentBreedFetchToArray.add("jem")
            }else if(dataBreed== "aice") {
                intentBreedFetchToArray.add("aice")
            }else if(dataBreed == "tarub") {
                intentBreedFetchToArray.add("tarub")
            }else if(dataBreed == "centvince") {
                intentBreedFetchToArray.add("centvince")
            }
        }


        for (i in intentNameFetch) {
            if (i == "jem") {
                image.add(R.drawable.pincher)
            }else if(i == "aice") {
                image.add(R.drawable.aice)
            }else if(i == "tarub") {
                image.add(R.drawable.foxhound)
            }else if(i == "centvince") {
                image.add(R.drawable.syberian)
            }

        }


        val recyclerView = this.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter(intentBreedFetchToArray, intentNameFetchToArray , image)
        recyclerView.adapter = adapter

    }

}