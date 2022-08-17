package com.example.pocketbook.entities

class DailyTransactions {
    var id = 0
    var category: String? = null
    var amount: Double? = null
    var type: String? = null
    var date = 0
    var receipt: String? = null
    var userId = 0
    var title: String? = null

    constructor(
        id: Int,
        category: String?,
        amount: Double?,
        type: String?,
        date: Int,
        receipt: String?,
        title: String?,
        userId: Int
    ) {
        this.id = id
        this.category = category
        this.amount = amount
        this.type = type
        this.date = date
        this.receipt = receipt
        this.userId = userId
        this.title = title
    }

    constructor() {}
}