package com.example.midterm_300324116

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midterm_300324116.data.Job
import com.example.midterm_300324116.data.JobDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentMain300324116ViewModel (app: Application) : AndroidViewModel(app) {
    // TODO: Implement the ViewModel

    fun addSampleData() {
        viewModelScope.launch {
            // Dispatchers.IO means run this in the background
            withContext(Dispatchers.IO) {
                //val sampleNotes =
            }
        }
    }
}