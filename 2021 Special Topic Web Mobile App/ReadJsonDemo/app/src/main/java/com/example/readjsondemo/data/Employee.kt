package com.example.readjsondemo.data

import com.squareup.moshi.Json

//Sample data = "id":1,"first_name":"Horatio","last_name":"Caley","gender":"Male"
data class Employee (
    val id : Int = 0,
    val first_name : String = "",
    val last_name : String = "" ,
    val gender : String?
)

// What if the field name is not the same as the source? Will change first_name to fn
// @Json (name = "first_name") means it represents "first_name"
data class Employee2 (
    val id : Int = 0,

    val last_name : String = "",
    @Json (name = "first_name") val fn : String = "", // intentionally moved
    val gender : String?
)