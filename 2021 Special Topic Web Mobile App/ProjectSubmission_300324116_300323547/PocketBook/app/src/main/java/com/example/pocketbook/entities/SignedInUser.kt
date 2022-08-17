package com.example.pocketbook.entities

class SignedInUser {
    var fname: String = ""
    var lname: String = ""
    var email: String = ""
    var phone: String = ""
    var password: String = ""
    var budget: Double = 0.0

    constructor()

    constructor(
        fname: String,
        lname: String,
        email: String,
        phone: String,
        password: String,
        budget: Double
    ) {
        this.fname = fname
        this.lname = lname
        this.email = email
        this.phone = phone
        this.password = password
        this.budget = budget
    }


}