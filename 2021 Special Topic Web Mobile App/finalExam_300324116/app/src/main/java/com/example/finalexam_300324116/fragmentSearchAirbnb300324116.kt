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


class fragmentSearchAirbnb300324116 : Fragment() {

    private lateinit var binding: FragmentSearchAirbnb300324116FragmentBinding

    companion object {
        fun newInstance() = fragmentSearchAirbnb300324116()
    }

    private lateinit var viewModel: FragmentSearchAirbnb300324116ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchAirbnb300324116FragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonGetSearch = root.findViewById<Button>(R.id.buttonGetSearch)
        val buttonSaveSearch = root.findViewById<Button>(R.id.buttonSaveSearch)
        val buttonCancelSearch = root.findViewById<Button>(R.id.buttonCancelSearch)


        buttonGetSearch.setOnClickListener {

        }

        buttonSaveSearch.setOnClickListener {

        }

        buttonCancelSearch.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_fragmentSearchAirbnb300324116_to_fragmentMain300324116)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentSearchAirbnb300324116ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}