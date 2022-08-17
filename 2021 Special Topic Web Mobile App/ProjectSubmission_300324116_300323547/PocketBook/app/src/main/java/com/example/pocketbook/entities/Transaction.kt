package com.example.pocketbook.entities

import java.util.*

class Transaction {

    var category_name: String = ""
    var category_type: String = ""
    var amount: Double = 0.0
    var description: String = ""
    var date: String= ""
    var userEmail: String = ""

    constructor(
        category_name: String,
        category_type: String,
        amount: Double,
        description: String,
        date: String,
        userEmail: String
    ) {
        this.category_name = category_name
        this.category_type = category_type
        this.amount = amount
        this.description = description
        this.date = date
        this.userEmail = userEmail
    }

    constructor()
}