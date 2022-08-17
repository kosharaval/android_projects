package com.example.midterm_300324116

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.midterm_300324116.databinding.FragmentLogin300324116FragmentBinding
import com.example.midterm_300324116.databinding.FragmentRegistration300324116FragmentBinding

class fragmentLogin300324116 : Fragment() {

    companion object {
        fun newInstance() = fragmentLogin300324116()
    }

    private lateinit var viewModel: FragmentLogin300324116ViewModel
    private lateinit var binding: FragmentLogin300324116FragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLogin300324116FragmentBinding.inflate(inflater,container,false)

        binding.buttonCancel.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_fragmentLogin300324116_to_fragmentMain300324116)
        }
        binding.buttonLoginTo.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_fragmentLogin300324116_to_fragmentJobPosting300324116Fragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentLogin300324116ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}