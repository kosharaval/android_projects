package com.example.pt_300324116

data class Book(var title: String,
                var author: String,
                var rating: String,
                var price: String,
                var year: String,
                var genre: String,) {
    constructor() : this("","","","","","")
}
