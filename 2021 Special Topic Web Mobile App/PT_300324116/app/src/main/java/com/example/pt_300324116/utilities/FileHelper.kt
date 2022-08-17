package com.example.moshidemo2.utilities
import android.content.Context
import java.io.BufferedReader

class FileHelper {
    // this is similar to static method
    companion object {
        // accepts Context and filename
        fun getDataFromAssets(context: Context, fileName: String) : String {
            return context.assets.open(fileName).use {
                it.bufferedReader().use { bf: BufferedReader ->
                    bf.readText()
                }
            }
        }

    }
}