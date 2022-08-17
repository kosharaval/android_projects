package com.example.pt_300324116

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.pt_300324116.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var bindingMain : MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        bindingMain = MainFragmentBinding.inflate(inflater, container, false)

        bindingMain.buttonDisplayAll.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_displayAllFragment)
        }

        bindingMain.buttonSearch.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_searchFragment)
        }

        return bindingMain.root
    }
}