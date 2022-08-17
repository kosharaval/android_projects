package com.example.pocketbook.entities

data class User(
    var fname: String = "",
    var lname: String = "",
    var email: String = "",
    var phone: String = "",
    var password: String = "",
    var budget: Double = 0.0
)
