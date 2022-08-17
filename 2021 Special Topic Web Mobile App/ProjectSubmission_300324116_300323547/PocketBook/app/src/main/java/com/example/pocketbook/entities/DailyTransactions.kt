package com.example.pocketbook.entities

import java.util.*

data class DailyTransactions(
    var category_name: String,
    var category_type: String,
    var amount: Double,
    var description: String,
    var date: String,
    var userEmail: String
)
