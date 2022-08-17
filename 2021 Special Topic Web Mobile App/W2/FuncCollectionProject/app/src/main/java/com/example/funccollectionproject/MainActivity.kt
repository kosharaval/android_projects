package com.example.funccollectionproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // ===================
    data class Student(val firstName: String, val grade: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initial array
        var assorted = arrayOf(1, 2, 3, "one", "two", "three")
        val students = arrayOf<Student>(
            Student("Alfred", 6),
            Student("Betty", 5)
        )

        val studentMList = mutableListOf<Student>(
            Student("Charles", 1),
            Student("Darwin", 2)
        )

        /*
            can't change the contents or add another entry
            studentList not used in this program
            This is just an example entry
        */
        val studentList = listOf<Student>(
            Student("Charles2", 1),
            Student("Darwin2", 2)
        )

        // =========================
        // Sets
        // removes duplicates
        val fset = mutableSetOf(1, 2, 3, 4, 5)
        val bset = mutableSetOf(5, 4, 3, 2, 1)

        val fset2 = hashSetOf<Student> (
            Student("Lily", 5),
            Student("Pad", 2)
        )


        // ==========================
        // Map
        val m1 = mapOf(1 to "alpha", 2 to "beta", 3 to "gamma", 4 to "delta")
        val m2 = mapOf(4 to "delta", 3 to "gamma", 2 to "beta", 1 to "alpha")

        val studentMMap = mutableMapOf<Int,Student>(
            0 to Student("Eric", 2) ,
            1 to Student("Francis", 3)
        )

        var mapCounter = studentMMap.size

        // displays
        displayArray(assorted,students)
        displayList(studentMList)
        displaySets(fset,bset,fset2)
        displayMap(m1,m2,studentMMap)

        // ==============================================
        btnAdd.setOnClickListener{
            displayArray(assorted,students)

            // =====================
            val grade : Int = gradeTxt.text.toString().toIntOrNull() ?: 0
            val stud = Student(fNameTxt.text.toString(),grade)

            // =====================
            // MutableList
            studentMList.add(stud)
            displayList(studentMList)

            // ======================
            // MutableSet
            fset2.add(stud)
            displaySets(fset,bset,fset2)

            // ======================
            // MutableMap
            val nextKey = mapCounter++
            studentMMap[nextKey] = stud
            displayMap(m1,m2,studentMMap)
        }

        btnReplace.setOnClickListener {
            val index = replaceTxtIndex.text.toString().trim().toIntOrNull() ?: -1
            val grade: Int = gradeTxt.text.toString().toIntOrNull() ?: 0
            val stud = Student(fNameTxt.text.toString(), grade)

            if (index >= 0) {

                if (index  < studentMList.size) {
                    studentMList[index] = stud
                }


                if (index  < studentMMap.size) {
                    studentMMap.replace(index,stud)
                }
                displayList(studentMList)
                displaySets(fset,bset,fset2)
                displayMap(m1,m2,studentMMap)

            }

        }

        btnRemove.setOnClickListener{
            val index = replaceTxtIndex.text.toString().trim().toIntOrNull() ?: -1
            if (index >= 0) {
                if (index  < studentMList.size) {
                    studentMList.removeAt(index)

                }

                if (index < fset2.size) {
                    val stud = fset2.elementAt(index)
                    fset2.remove(stud)
                }
                if (index  < studentMMap.size) {
                    studentMMap.remove(index)   // assuming that the index is used as the key
                }
                displayList(studentMList)
                displaySets(fset,bset,fset2)
                displayMap(m1,m2,studentMMap)

            }



        }
    }


    private fun <T> displayArray(data1: Array<T>, data2 : Array<Student> ) {

        var s : String = ""
        for (d in data1) {
            s += " $d -"
        }
        s += "\n"

        // with Array, you can change the existing contents.
        data2[0] = Student("Mr. Change", 123)

        data2.forEach {
           s += "[$it, ${it.firstName}] "
       }
        s+= "\n"

        for (i in 0 until data2.size) {
            s += "{" + data2[i].firstName + "} "
        }

        txtArray.text = s
    }


    private fun displayList(data : MutableList<Student>) {
        var s : String = ""

        data.forEach {
            s += "${it.firstName} , ${it.grade}\n"
        }

        txtList.text = s
    }

    fun displaySets(d1 : MutableSet<Int>, d2 : MutableSet<Int> , d3 : HashSet<Student>){
        var s : String = ""
        s += "firsts equal? ${d1.first() == d2.first()} \n"
        s += "d1 == d2? ${d1 == d2} \n"
        s += "\n\n"
        s += "added 6? ${d1.add(6)} \n"
        s += "removed 76? ${d1.remove(76)} \n"

        d1.addAll(d2)
        d1.forEach {
            s += "${it} "
        }
        s += "\n\n"

        d3.forEach{
            s += "${it.firstName} , ${it.grade} \n"
        }

        txtSets.text = s
    }

    private fun displayMap(data1 : Map<Int,String> , data2 : Map<Int,String>, data3: MutableMap<Int,Student>) {
        var s : String = ""
        s += "m1 == m2? ${data1 == data2} \n"

        s += "data1[2] = ${data1[2]}\n"
        s += "data1[5] = ${data1.getOrDefault(5, "Not present")} \n\n"

        s += "The {keys,values} are ["
        for (v in data3) {
            s += " { ${v.key}, ${v.value.firstName}, ${v.value.grade} }  "
        }
        s += "] \n\n"

        s += "finding key = 1 , ${1 in data3} \n"
        s += "finding key = 2 , ${data3.containsKey(2)} \n\n"
        s += "finding Francis,  \n"

        val filtered = data3.filterValues {
            it.firstName == "Francis" }

        s += "$filtered \n"
        filtered.forEach {
            s += "${it.key} , ${it.value.firstName} , ${it.value.grade} \n"
        }

       txtMaps.text = s
    }
}