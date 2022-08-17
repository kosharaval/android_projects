package com.example.readonlinejson.utilities

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
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