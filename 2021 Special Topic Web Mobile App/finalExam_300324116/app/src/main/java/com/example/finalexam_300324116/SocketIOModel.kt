package com.example.finalexam_300324116

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SocketIOModel: ViewModel() {

    var hostString = ""

    fun setData(message: String){
        hostString = message
    }

  }