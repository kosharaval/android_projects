package com.example.mynavcompdemo1
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynavcompdemo1.databinding.ListItemBinding


class NotesListAdapter(private val notesList: MutableList<NoteEntity>) :
    RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView) // ListItemBinding refers to the list_item.xml

        // this is not required. This is only to show the flow
        init {
            println("ViewHolder")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)

        println("onCreateViewHolder") // just to show the flow

        return ViewHolder(view)
    }

    override fun getItemCount() = notesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]

        println("onBindViewHolder $position")  // just to show the flow

        with(holder.binding) {
            theNoteTxt.text = note.text // theNoteTxt is an item in list_item.xml
        }
    }
}