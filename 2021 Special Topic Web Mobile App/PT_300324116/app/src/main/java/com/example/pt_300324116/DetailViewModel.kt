package com.example.pt_300324116

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pt_300324116.data.BookEntity

class DetailViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var bookList = MutableLiveData<List<BookEntity>>()

}