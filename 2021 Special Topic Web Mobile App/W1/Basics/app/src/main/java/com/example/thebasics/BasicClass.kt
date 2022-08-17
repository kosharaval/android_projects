package com.example.thebasics

import android.widget.TextView



    fun showValues(txtResult : TextView) {
        var count: Int = 0
        count += 20                 // mutable

        val number: Int = 42

        val otherNumber = 43
        // otherNumber = 50         // immutable

        val myLong: Long = 3000000000L
        val myByte: Byte = 127
        val myShort: Short = 32767

        val myDouble: Double = 98.6
        val myFloat: Float = 12.2F
        val alsoDouble = 101.5

        val asInt = alsoDouble.toInt()  // you can display this yourself
        val asFloat = myLong.toFloat()  // try to display

        val maxInt = 2_147_483_648

        var s = String.format("count = %2d\n",count)
        s += String.format("number = %2d\n",number)
        s += String.format("otherNumber = %2d\n",otherNumber)
        s += String.format("myLong = %2d\n",myLong)
        s += String.format("myByte = %2d\n",myByte)
        s += String.format("myShort = %2d\n",myShort)
        s += String.format("myDouble = %.2f, alsoDouble = %.2f\n",myDouble,alsoDouble)
        s += String.format("myFloat = %.2f\n",myFloat)
        s += String.format("maxInt = %2d\n",maxInt)
        s += "\n\n"
        // boolean
        val willStudy = true
        val bigNumber = 1_456
        val smallNumber = 2
        s += "is big bigger = ${bigNumber > smallNumber} \n"
        s += "Are you going to study? ${!willStudy}\n"

        s += "\n\n"
        //string
        val greeting: String = "Hello there"
        val owe = 500

        val oweMoney = "I owe you \$$owe dollars"
        s += "${oweMoney}\n"

        val infinity = "The infinity symbol is \u221E"
        s += "${infinity}\n"

        s += "[$greeting] is ${greeting.length} characters long \n"

        val letter = greeting[1]
        s += "letter = $letter \n"

        val compare1 = "D"
        val compare2 = "A"
        s += "comparing compare1 with compare2 ... ${compare1.compareTo(compare2)} \n"

        val sub = greeting.subSequence(6, 9) // (start, endIndex - 1)
        s += "sub = $sub\n"

        for(single in greeting) {
            s += "$single - "
        }

        txtResult.text = s
    }
