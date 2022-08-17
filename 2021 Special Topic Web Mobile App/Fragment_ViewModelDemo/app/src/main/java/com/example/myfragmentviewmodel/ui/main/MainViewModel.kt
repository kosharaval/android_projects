package com.example.myfragmentviewmodel.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val CAD_TO_USD =  .78
    private var dollarTxt : String = ""
    private var result = MutableLiveData<Double> (0.0)

    fun setAmount(amt: String) {
        dollarTxt = amt
        // the next line would change the contents of result (MutableLiveData) and
        // "calls" the observer. checkout MainFragment.kt (viewModel.getAmount ... )
        result.value = dollarTxt.toDouble() * CAD_TO_USD
    }

    fun getAmount() : MutableLiveData<Double> {
        return result
    }


}