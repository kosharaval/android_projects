package com.example.pt_300324116

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pt_300324116.data.BookEntity
import com.example.pt_300324116.databinding.ListItemBinding

class BookListAdapter (private val bookList: List<BookEntity>,  private val listener: ListItemListener) : RecyclerView.Adapter<BookListAdapter.ViewHolder>(){

    val selectedNotes = arrayListOf<BookEntity>()

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = bookList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]
        with(holder.binding) {
            textViewBookTitle.text = book.title
            root.setOnClickListener {
                listener.bookDetail(book.id)
            }
        }
    }
    interface ListItemListener {
        fun bookDetail(bookID: Int)
    }
}