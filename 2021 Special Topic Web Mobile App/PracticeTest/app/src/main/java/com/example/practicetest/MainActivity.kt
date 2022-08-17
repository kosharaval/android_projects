package com.example.practicetest

import Movies
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import java.net.URISyntaxException

class MainActivity : AppCompatActivity() {

    var result: String = ""
    var mSocket: Socket? = null
    var userName: String? = null;
    lateinit var roomName: String;
    private val myType = Types.newParameterizedType(List::class.java, Movies::class.java)
    private val socketViewModel: SocketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var string: String?
        try {
            val inputStream: InputStream = assets.open("source.txt")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            string = String(buffer)
            textViewHostMain.setText(string)
            socketViewModel.setData("Main Activity" + string)
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }
    }

    var onDataRequest = Emitter.Listener {
        val data = it[0] as String
        Log.d("data received", data.toString())

        // Using data class Employee2
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter : JsonAdapter<List<Movies>> = moshi.adapter(myType)

        val dataList = adapter.fromJson(data)
        result= ""

        for (e in dataList ?: emptyList() ) {
            Log.i(this.toString(), "${e.title} - ${e.plot}")
            result += ("${e.title} - ${e.plot} \n")
        }
        socketViewModel.setResultMsg(result)
    }

    var onNewUser = Emitter.Listener {
        val message = it[0] as String
        result = message
        socketViewModel.setResultMsg(result)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket?.disconnect()
    }
}