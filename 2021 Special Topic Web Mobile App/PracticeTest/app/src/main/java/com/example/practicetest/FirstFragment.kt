package com.example.practicetest

import Movies
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.practicetest.databinding.FirstFragmentBinding
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.first_fragment.*
import java.net.URISyntaxException

class FirstFragment : Fragment() {

    private lateinit var socketViewModel: SocketViewModel
    private lateinit var viewModel: FirstViewModel
    private lateinit var binding: FirstFragmentBinding

    var mSocket: Socket? = null
    private val myType = Types.newParameterizedType(List::class.java, Movies::class.java)

    companion object {
        fun newInstance() = FirstFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        socketViewModel = ViewModelProvider(this).get(SocketViewModel::class.java)
        viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)

        binding = FirstFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textViewHost = root.findViewById<TextView>(R.id.textViewHost)
        val textViewResult = root.findViewById<TextView>(R.id.textViewResult)

        socketViewModel.hostString.observe(viewLifecycleOwner,{
            textViewHost.text = it
        })

//        try {
//            mSocket = IO.socket(textViewHost.text.toString())
//
//        } catch (e: URISyntaxException) {
//            Log.d("URI error", e.message.toString())
//        }
//
//        try {
//            mSocket?.connect()
//            textViewResult.text = "connected to " + textViewHostMain.text.toString() + " " + mSocket?.connected()
//
//        } catch (e: Exception) {
//            textViewResult.text = " Failed to connect. " + e.message
//        }
//
//        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
//            textViewResult.text = "sending"
//            mSocket?.emit("messages", "hi")
//        });
//
//        //listening and opportunity for the other to talk
//        //next 2 lines are listening part
//        mSocket?.on("notification", onNewUser) // To know if the new user entered the room.
//        mSocket?.on("datasent", onDataRequest)
//        mSocket?.emit("getTheData")

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)
        // TODO: Use the ViewModel
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
        textViewResult.text = ""

        for (e in dataList ?: emptyList() ) {
            Log.i(this.toString(), "${e.title} - ${e.plot}")
            textViewResult.append("${e.title} - ${e.plot} \n")
        }
    }

    var onNewUser = Emitter.Listener {
        val message = it[0] as String
        textViewResult.text = message
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket?.disconnect()
    }

}