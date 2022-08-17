package com.example.w4_300324116_p1

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ViewBalanceFragment : Fragment() {

    companion object {
        fun newInstance() = ViewBalanceFragment()
    }

    private lateinit var viewModel: ViewBalanceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_balance_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewBalanceViewModel::class.java)
        // TODO: Use the ViewModel
    }

}