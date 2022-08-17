package com.example.castsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtResult.text = ""

        display()

    }

    fun display() {

        var s : String = ""

        /*
        var greeting: String
        greeting = null         // by default, you are not allowed to assign null
        */


        // If you want to assign null to a variable, you need to declare it as nullable using ?
        var greeting: String?       // observer: the ? at the end of the data type
        //greeting = null

        greeting = "Hello there"
        if (greeting != null) {
            s += "Greeting = '$greeting' \n"
        }

        // This would give an error if greeting = null
        // s += "greeting length = ${greeting.length} \n"
        s += "greeting length = ${greeting?.length} \n"     // observe: ? would allow you to call
                                                            // .length if greeting is null

        // Elvis operator ?: returns its first expression if it is not null,
        // otherwise it returns the second expression.
        val len = greeting?.length ?: 0     // in case the value is null, it would receive 0
        s += "Length is $len \n"


        // exception when greeting is null
        val badLen = greeting!!.length      // observe: !! means don't give any warnings bec
                                            // the programmer knows what he/she is doing
                                            // Try: change greeting = null and see what happens when you compile

        s += "badLength is $badLen \n"

        // To avoid an exception being thrown, one can use a safe cast operator as?
        // that returns null on failure
        val safeGreeting: String? = greeting as? String
        s += "safeGreeting is $safeGreeting \n"

        s += "\n\n --- Smart cast -- \n\n"

        var x : Any = 123     // this would make variable x as int

          x = "547"  // this would make variable x as String
        //var x : Any = IntArray(4)  // this would make variable x as an IntArray, size of 4 w/ value of zero
        //var x : Any = IntArray(4, {1})  // this would make variable x as an IntArray, size of 4 w/ value of 1
        //var x : Any = IntArray(4, {it})  // this would make variable x as an IntArray, size of 4 w/ value of 0 to 3
        //var x : Any = IntArray(4, { a -> a * 3 + 2})  // this would make variable x as an IntArray, size of 4 w/ value of 2,5,8,11


        s +=
            when (x)  {
                is Int ->  "x is Int w/ value $x++ , ${++x} \n"
                is String -> x + 10
                is IntArray -> x.sum()
                else -> "don't know"
        }


        txtResult.text = s
    }
}