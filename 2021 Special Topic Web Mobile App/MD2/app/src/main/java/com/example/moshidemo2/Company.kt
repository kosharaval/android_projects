package com.example.moshidemo2

// Sample data : {"id":8,"Companies":[{"dept":"Services"},{"dept":"Marketing"},{"dept":"Legal"}
data class Company(
    val id: Int?,
    val Companies : MutableList<Department> = mutableListOf<Department>()
    // Since "Companies" contains 1 or more key/value pair entries, you can use
    // MutableList.
)
