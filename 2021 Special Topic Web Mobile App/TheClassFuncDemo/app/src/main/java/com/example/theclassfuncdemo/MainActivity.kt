package com.example.theclassfuncdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFunc.setOnClickListener {
            var s = ""

            s += display("Albert\n")
            s += display2("Bobby\n")
            s += display3("Carol\n")

            s += "\nvolume(10,20,30) \n ${volume(10,20,30)}"
            s += "\nvolume(10,20) \n ${volume(10,20)}"
            s += "\nvolume(width=10) \n ${volume(width=10)}"
            s += "\nvolume2(width=10) = ${volume2(width=10)}"

            txtResult.text = s
        }

        // ========================
        btnLambda.setOnClickListener{
            var s = ""

            // val lambda_name : Data_type = { argument_List -> code_body }
            // length: Int, width: Int, height: Int are the parameters
            val vol =  { length: Int, width: Int, height: Int  -> length * width * height }
            val vol2 : (Int, Int, Int) -> Int = { length, width, height -> length * width * height}

            s += "\nvol(10,20,30) = ${vol(10,20,30)} \n"
            s += "\nvol2(2,3,4) = ${vol2(2,3,4)} \n"

            s += "\ncallingAnother(10,25,\"ABC\",::display2) \n${callingAnother(10,25,"ABC",::display2)}\n"

            s += callingAnother2(3,5,2,vol2)

            val numbers = arrayOf(1,-2,3,-4,5)
            // it: implicit name of a single parameter
            s += numbers.filter { it > 0}
            s += "\n"
            s += numbers.filter { theItem -> theItem > 0}
            s += "\n"


            txtResult.text = s
        }

        // ==============================
        btnClass.setOnClickListener{
            val course1 = Course("Do Kotlin")
            val course2 = Course("")


            var s = ""
            s += course1.title + ", " + course1.description + "," + course1.classSize + "\n"

            course2.description = "Kotlin my Android"
            course2.classSize = -10
            s += "${course2.title} , ${course2.description} , ${course2.classSize} \n"

            course2.classSize = 20
            s += "${course2.title} , ${course2.description} , ${course2.classSize} \n"

            s += "\n"

            val car = Vehicle("Chevrolet", "Volt", 2018)
            println(car)
            s += "$car \n"

            val car2 = Vehicle(state = "NV", year = 2019, make = "Ford", model = "Mustang")
            println(car2)
            s += "$car2 \n"

            val car3 = Vehicle("Tesla", "Model S", 2019, "RI", "P100D")
            println(car3)
            s += "$car3 \n"

            val car4 = Vehicle("Tesla", "Model 3", 2019, "CA",
                "Performance", "New")
            println(car4)
            s += "$car4 \n"

            s += "\n\n"

            val students = mutableListOf<Student>(
                Student("arnold", "smith", 11),
                Student("arnold", "smith", 11),
                Student("bobby", "flee", 12)
            )

            s += "students[0] = ${students[0]} + \n"
            s += "Does ${students[0]} == ${students[1]}: ${students[0] == students[1]} \n"

            val sibling = students[0].copy(firstName = "darren")  // copy all except change firstname
            students.add(sibling)

            s += "\n"
            for(student in students){
                s += "$student, ${student.firstName} \n"
            }

            txtResult.text = s

        }
    }


    // ==========================================
    // function examples
    // ==========================================
    fun display(name: String) : String {
        return "Hello $name"
    }

    fun display2(name: String) : String = "Hello $name"
    fun display3(name: String) = "Hello $name"


    fun volume(length: Int =1, width: Int, height: Int = 3) : String {
        // this is to illustrate default values
        var str = ""
        str += "length = ${length}\n"
        str += " width = ${width}\n"
        str += " height = ${height}\n"
        str += " result is ${length * width * height}\n"

        return str;

    }

    // if you only have statement to perform
    fun volume2(length: Int =1, width: Int, height: Int = 3) = length * width * height

    // ==========================================
    // lambda examples
    // ==========================================

    fun callingAnother( x: Int, y: Int, s : String,
                        AFun : (String) -> String ) : String
    {
        val r = x + y
        val result = AFun(s)

        return "The combined result: ${r} , ${result} \n"
    }

    // v : (Int,Int,Int) -> Int refers to the lambda or function that would be passed
    fun callingAnother2 ( l: Int, w : Int, h : Int, v : (Int,Int,Int) -> Int ) : String
    {
        var s = " (length = ${l}, width = ${w}, height = ${h}) = ${v(l,w,h)} \n"
        return s
    }
}