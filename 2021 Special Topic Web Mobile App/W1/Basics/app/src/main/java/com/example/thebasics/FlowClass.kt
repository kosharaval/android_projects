package com.example.thebasics

import android.widget.TextView

enum class Suit {
    Club, Diamond, Heart, Spade
}

fun showFlow(txtResult : TextView) {

    var s : String = ""

    val number = 121
    val bucket: Any = if (number > 100) {
        "alpha"
    } else if (number > 90) {
        println("Less than 90")
        2
    } else if (number > 80) {
        println("Less than 80")
    } else {
        1
    }

    s += "bucket = $bucket \n"


    val x = 2
    when(x) {
        1 -> s += "x == 1 \n"
        2 -> s += "x == 2 \n"
        3,4 -> s += "x == 3 or 4 \n"
    }

    val card = Suit.Spade
    val y = when(card) {
        Suit.Club, Suit.Spade -> "black card"
        Suit.Diamond, Suit.Heart -> "red card"
    }

    s += "card = $y \n"

    val name = "Troy"
    val lastName = when (name) {
        "Hattan" -> "Shobokshi"
        "Todd" -> "Miller"
        "Troy" -> "Miles"
        else -> {
            "Unknown"
        }
    }

    s += "lastName: $name $lastName \n"

    val ageType = when (x) {
        in 0..1 -> "baby"
        in 2..4 -> "toddler"
        in 5..12 -> "kid"
        in 13..19 -> "teenager"
        in 20..64 -> "adult"
        else -> "senior"
    }

    s += "ageType: You are a $ageType \n"

    s += "["
    for (i in 1..10) {
       s += "$i "
    }
    s += "]\n"


    s += "The Students : ["
    val students = listOf("Janet", "Daisy", "Veronica", "Fernanda")
    for (student in students) {
        s += " $student -"
    }
    s = s.trim('-')
    s += "]\n"

    s += "Student and index: ["
    for ((index, student) in students.withIndex()) {
        s += " #${index + 1} is $student -"
    }
    s = s.trim('-')
    s += "]\n"


    /* example of while
    var count = 5
    while (count > 0) {
        println(count)
        count--
    } */

    /* example of do - while
    println("do while")
    do {
        println(count)
        count++
    } while (count < 5)
    */


    txtResult.text = s
}