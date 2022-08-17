package com.example.w3_300324116_p1

class Car {
    internal var id: Int = 0
        get(){
            return if (field==0) 0 else field
        }
        set(value) {
            field = value
        }

    internal var firstName: String = ""
        get(){
            return if (field.isNullOrBlank()) "no title" else field
        }
        set(value) {
            field = value
        }

    internal var lastName: String = ""
        get(){
            return if (field.isNullOrBlank()) "no title" else field
        }
        set(value) {
            field = value
        }

    internal var carModel: String = ""
        get(){
            return if (field.isNullOrBlank()) "no title" else field
        }
        set(value) {
            field = value
        }

    constructor(id:Int , fName: String, lName: String, cModel:String){
        this.id = id
        this.firstName = fName
        this.lastName = lName
        this.carModel = cModel
    }
}