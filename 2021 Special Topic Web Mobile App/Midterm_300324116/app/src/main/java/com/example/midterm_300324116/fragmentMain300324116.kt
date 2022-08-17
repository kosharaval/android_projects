package com.example.midterm_300324116

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.midterm_300324116.databinding.FragmentMain300324116FragmentBinding

class fragmentMain300324116 : Fragment() {

    companion object {
        fun newInstance() = fragmentMain300324116()
    }

    private lateinit var viewModel: FragmentMain300324116ViewModel
    private lateinit var binding: FragmentMain300324116FragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMain300324116FragmentBinding.inflate(inflater, container, false)
        binding.buttonLogin.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_fragmentMain300324116_to_fragmentLogin300324116)
        }

        binding.buttonRegistration.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_fragmentMain300324116_to_fragmentRegistration300324116)
        }

        return binding.root
    }

}
