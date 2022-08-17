package com.example.pocketbook.entities

import kotlin.jvm.JvmOverloads
import com.example.pocketbook.entities.Budget

class Budget @JvmOverloads constructor(
    var id: String = "",
    var month: String = "",
    var year: String = "",
    var amount: String = "",
    var userId: String = ""
) {

    constructor(budget: Budget) : this(
        budget.id,
        budget.month,
        budget.year,
        budget.amount,
        budget.userId
    ) {
    }
}