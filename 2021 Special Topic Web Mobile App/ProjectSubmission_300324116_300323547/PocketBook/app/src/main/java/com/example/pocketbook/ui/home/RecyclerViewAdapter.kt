package com.example.pocketbook.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.R
import com.example.pocketbook.entities.Transaction

internal class RecyclerViewAdapter(private var itemsList: MutableList<Transaction>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvCategoryName = view.findViewById<TextView>(R.id.tvCategoryName)
        val tvCategoryType = view.findViewById<TextView>(R.id.tvCategoryType)
        val tvAmount = view.findViewById<TextView>(R.id.tvAmount)
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_transaction, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.tvDescription.text = item.description
        holder.tvCategoryName.text = item.category_name
        holder.tvCategoryType.text = item.category_type
        holder.tvAmount.text = item.amount.toString()
        holder.tvDate.text = item.date.toString()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}