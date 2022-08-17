package com.example.finalexam_300324116

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.example.finalexam_300324116.databinding.FragmentSearchAirbnb300324116FragmentBinding
import com.example.finalexam_300324116.databinding.FragmentViewBookings300324116FragmentBinding

class fragmentViewBookings300324116 : Fragment() {

    companion object {
        fun newInstance() = fragmentViewBookings300324116()
    }

    private lateinit var binding: FragmentViewBookings300324116FragmentBinding
    private lateinit var viewModel: FragmentViewBookings300324116ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewBookings300324116FragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonGetBookings = root.findViewById<Button>(R.id.buttonGetBookings)
        val buttonCancelViewBooking = root.findViewById<Button>(R.id.buttonCancelViewBooking)

        buttonGetBookings.setOnClickListener {

        }

        buttonCancelViewBooking.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_fragmentViewBookings300324116_to_fragmentMain3003241162)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentViewBookings300324116ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}