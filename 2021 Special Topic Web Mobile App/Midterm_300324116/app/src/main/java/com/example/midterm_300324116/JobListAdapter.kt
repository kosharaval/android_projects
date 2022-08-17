package com.example.midterm_300324116

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midterm_300324116.data.Job
import com.example.midterm_300324116.databinding.ListItemBinding

class JobListAdapter (private val jobList: List<Job>, private val listener: ListItemListener) : RecyclerView.Adapter<JobListAdapter.ViewHolder>(){

    val selectedNotes = arrayListOf<Job>()

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = jobList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = jobList[position]
        with(holder.binding) {
            textViewBookTitle.text = job.businessTitle
            root.setOnClickListener {
                listener.bookDetail(job.id)
            }
        }
    }
    interface ListItemListener {
        fun bookDetail(bookID: Int)
    }
}