package com.example.practicetest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SocketViewModel: ViewModel() {

    val hostString = MutableLiveData<String>()
    val resultString = MutableLiveData<String>()

    fun setData(message: String){
        hostString.value = message
    }

    fun setResultMsg(message: String){
        resultString.value = message
    }
}