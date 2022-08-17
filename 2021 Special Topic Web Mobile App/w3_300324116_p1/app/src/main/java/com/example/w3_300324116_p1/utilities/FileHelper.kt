package com.example.w3_300324116_p1.utilities
import android.content.Context
import java.io.BufferedReader

class FileHelper {

    companion object {

        fun getDataFromAssets(context: Context, fileName: String) : String {
           return context.assets.open(fileName).use {
               it.bufferedReader().use { bf: BufferedReader ->
                   bf.readText()
               }
           }
        }
    }
}