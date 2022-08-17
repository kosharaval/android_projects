package com.example.midterm_300324116

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class fragmentJobPosting300324116Fragment : Fragment() {

    companion object {
        fun newInstance() = fragmentJobPosting300324116Fragment()
    }

    private lateinit var viewModel: FragmentJobPosting300324116ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_job_posting300324116_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentJobPosting300324116ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}