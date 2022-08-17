package com.example.finalexam_300324116

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class fragmentRegistration300324116 : Fragment() {

    companion object {
        fun newInstance() = fragmentRegistration300324116()
    }

    private lateinit var viewModel: FragmentRegistration300324116ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration300324116_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentRegistration300324116ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}