package com.example.pocketbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SocketIOModel: ViewModel() {

    var hostString = ""

    fun setData(message: String){
        hostString = message
    }

  }