package com.example.pt_300324116

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moshidemo2.utilities.FileHelper
import com.example.pt_300324116.data.BookEntity
import com.example.pt_300324116.databinding.DisplayAllFragmentBinding
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class DisplayAllFragment : Fragment(), BookListAdapter.ListItemListener {

    companion object {
        fun newInstance() = DisplayAllFragment()
    }

    private lateinit var viewModel: DisplayAllViewModel
    private lateinit var bindingDisplayAll: DisplayAllFragmentBinding
    private lateinit var adapter: BookListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        bindingDisplayAll = DisplayAllFragmentBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(DisplayAllViewModel::class.java)
        viewModel.addBookData()

        with (bindingDisplayAll.recyclerViewBookList) {
            setHasFixedSize(true) // all rows would have a fix size regardless of its contents

            // to create a divider to separate each row
            val divider = DividerItemDecoration (context, LinearLayoutManager(context).orientation)
            addItemDecoration(divider)
        }
        viewModel.bookListDB?.observe(viewLifecycleOwner, Observer {

            adapter = BookListAdapter(it, this@DisplayAllFragment)
            bindingDisplayAll.recyclerViewBookList.adapter = adapter
            bindingDisplayAll.recyclerViewBookList.layoutManager = LinearLayoutManager(activity)

            val selectedBooks = savedInstanceState?.getParcelableArrayList<BookEntity>(
                SELECTED_BOOK_KEY)

            adapter.selectedNotes.addAll(selectedBooks?: emptyList())
        })

        return bindingDisplayAll.root
    }

    override fun bookDetail(bookID: Int) {
        TODO("Not yet implemented")
        val action = DisplayAllFragmentDirections.actionDisplayAllFragmentToDetailFragment(bookID)

    }
}