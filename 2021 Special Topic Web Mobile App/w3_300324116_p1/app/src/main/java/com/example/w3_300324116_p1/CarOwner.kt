package com.example.w3_300324116_p1

data class CarOwner(
    val id:Int?,
    val first_name:String,
    val last_name:String,
    val cars:MutableList<CarModel> = mutableListOf<CarModel>()
)
