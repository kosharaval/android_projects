package com.example.finalexam_300324116

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView


internal class RecyclerViewAdapter(private var itemsList: MutableList<ListingsAndReview>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewListings = view.findViewById<TextView>(R.id.textViewListings)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_search, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.textViewListings.text = item.description
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}