package com.example.finalexam_300324116

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.socket.client.Socket
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    var mSocket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var string: String?

        val textViewHost = findViewById<TextView>(R.id.textViewHost)
        try {
            val inputStream: InputStream = assets.open("source.txt")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            string = String(buffer)
            textViewHost.setText(string)
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }
    }
}