package com.example.theclassfuncdemo

import android.util.Log

class Course(courseTitle: String = "The Only Course") {
    internal val title = courseTitle
        get() {
            return if (field.isNullOrBlank()) "no title" else "$field!"
        }

    internal var description = ""
        get() {
            return if (field.isNullOrBlank()) "no description" else "$field!"
        }

    internal var classSize: Int = 0
        set(v) {
            Log.d("value", "${v}")
            if (v < 0)
                0
            else
                field = v
        }
}

// ===================================
class Vehicle(
    val make: String, val model: String,
    val year: Int, val state: String = "CA")
{
    override fun toString(): String {
        return "$year $make $model ($state)"
    }

    init {
        println("First initializer block - $this")
    }

    constructor(
        make: String, model: String,
        year: Int, state: String, style: String
    ) : this(make, model, year, state) {
        this.style = style
    }

    constructor(
        make: String, model: String,
        year: Int, state: String, style: String,
        status: String
    ) : this(make, model, year, state, style) {
        this.status = status
    }

    private var style = ""
    private var status = ""


}

// ========================================
// class student (below) would be simplified and written as data class Student
//class Student(val firstName: String, val lastName: String, val grade: Int) {
//    override fun toString(): String {
//        return "($firstName $lastName $grade)"
//    }
//
//    override fun equals(other: Any?): Boolean {
//        if (other is Student) {
//            return lastName == other.lastName &&
//                    firstName == other.firstName &&
//                    grade == other.grade
//        }
//        return false
//    }
//
//    override fun hashCode(): Int {
//        var result = firstName.hashCode()
//        result = 31 * result + lastName.hashCode()
//        result = 31 * result + grade
//        return result
//    }
//}

data class Student(val firstName: String, val lastName: String, val grade: Int)
