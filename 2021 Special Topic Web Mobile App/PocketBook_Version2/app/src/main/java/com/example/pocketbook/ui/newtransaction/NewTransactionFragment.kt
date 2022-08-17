package com.example.pocketbook.ui.newtransaction

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pocketbook.R

class NewTransactionFragment : Fragment() {

    companion object {
        fun newInstance() = NewTransactionFragment()
    }

    private lateinit var viewModel: NewTransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_transaction, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewTransactionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}