package com.example.adetl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RecyclerAdapter(var nameArray: MutableList<String>, var detailArray: MutableList<String>, var imageArray: MutableList<Int>):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemName.text = nameArray[position]
        holder.itemDetail.text = detailArray[position]
        holder.itemImage.setImageResource(imageArray[position])
    }

    override fun getItemCount(): Int {
        return  nameArray.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemName: TextView
        var itemDetail: TextView
        init{
            itemImage = itemView.findViewById(R.id.item_image)
            itemName= itemView.findViewById(R.id.itemName)
            itemDetail= itemView.findViewById(R.id.itemDetail)

        }


    }

}