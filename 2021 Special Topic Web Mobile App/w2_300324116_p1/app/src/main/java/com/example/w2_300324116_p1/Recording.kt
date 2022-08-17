package com.example.w2_300324116_p1

import android.util.Log

class Recording {

    internal var title: String = ""
        get(){
            return if (field.isNullOrBlank()) "no title" else field
        }
        set(value) {
            field = value
        }

    internal var artist: String = ""
        get(){
            return if (field.isNullOrBlank()) "no title" else field
        }
        set(value) {
            field = value
        }

    internal var playTime: Int = 0
        get(){
            return if (field==0) 0 else field
        }
        set(value) {
            field = value
        }

    constructor(title: String, artist: String, playTime:Int){
        this.title = title
        this.artist = artist
        this.playTime = playTime
    }
}