package com.example.myfragmentviewmodel.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.myfragmentviewmodel.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    /*
        lateinit only works with var

        MainViewModel extends ViewModel (which happens to be an abstract class)
        "A ViewModel is always created in association with a scope (a fragment or an activity) and
        will be retained as long as the scope is alive. E.g. if it is an Activity,
        until it is finished.

        In other words, this means that a ViewModel will not be destroyed if its owner is destroyed
        for a configuration change (e.g. rotation). The new owner instance just re-connects
        to the existing model.

        The purpose of the ViewModel is to acquire and keep the information that is necessary
        for an Activity or a Fragment.
        The Activity or the Fragment should be able to observe changes in the ViewModel.
        ViewModels usually expose this information via LiveData or Android Data Binding.
        You can also use any observability construct from your favorite framework.

        ViewModel's only responsibility is to manage the data for the UI.
        It should never access your view hierarchy or hold a reference back to
        the Activity or the Fragment."
     */

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel


        // This would be called automatically when MutableLiveData changes
        // checkout MainViewModel.kt , setAmount function
        viewModel.getAmount().observe(viewLifecycleOwner, Observer {
                resultText.text = String.format("USD %.2f",it)
            }
        )

        btnConvert.setOnClickListener {
            if (!dollarText.text.toString().equals("")) {
                viewModel.setAmount(dollarText.text.toString())
            } else {
              resultText.text = "No Value"
            }
        }
    }


}