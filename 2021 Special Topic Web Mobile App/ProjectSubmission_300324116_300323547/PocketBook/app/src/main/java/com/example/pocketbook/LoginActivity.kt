package com.example.pocketbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.pocketbook.entities.DailyTransactions
import com.example.pocketbook.entities.SignedInUser
import com.example.pocketbook.entities.Transaction
import com.example.pocketbook.entities.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.InputStream
import java.net.URISyntaxException
import java.util.*

class LoginActivity : AppCompatActivity() {

    var mSocket: Socket? = null
    private val userType = Types.newParameterizedType(List::class.java, User::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonRegister = findViewById<View>(R.id.buttonRegister) as Button
        val editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        val editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        val buttonLogin = findViewById<View>(R.id.buttonLogin) as Button
        val textViewHostProfile = findViewById<TextView>(R.id.textViewHostProfile)
        val textViewResultProfile =findViewById<TextView>(R.id.textViewResultProfile)

        var string: String?
        try {
            val inputStream: InputStream = assets.open("source.txt")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            string = String(buffer)
            textViewHostProfile.text = string
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }

        try {
            mSocket = IO.socket(textViewHostProfile.text.toString())

        } catch (e: URISyntaxException) {
            Log.d("URI error", e.message.toString())
        }


        try {
            mSocket?.connect()
            textViewResultProfile.text = "connected to " + textViewHostProfile.text.toString() + " " + mSocket?.connected()
        } catch (e: Exception) {
            textViewResultProfile.text = " Failed to connect. " + e.message
        }

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            textViewResultProfile.text = "sending"
            mSocket?.emit("messages", "hi")
        });


        buttonRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }

        buttonLogin.setOnClickListener {
            var email = editTextEmail.text.toString()
            var password = editTextPassword.text.toString()

            if(email.equals("") && password.equals("")){
                editTextEmail.setError("Invalid Email")
                editTextPassword.setError("Invalid Password")
                Toast.makeText(this, "Blank Email and Password", Toast.LENGTH_LONG)
            }
            else {
                mSocket?.emit("getUserData", email, password)
                mSocket?.on("userData", getUserData)

                startActivity(Intent(this@LoginActivity, NavigationDrawerActivity::class.java))
            }
        }
    }

    var getUserData = Emitter.Listener {
        val data = it[0] as String
        Log.d("data received", data.toString())

        // Using data class Employee2
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter : JsonAdapter<List<User>> = moshi.adapter(userType)

        val dataList = adapter.fromJson(data)

        if(dataList?.isEmpty() == true){
            Toast.makeText(applicationContext, "Invalid Email and Password", Toast.LENGTH_LONG)
        }
        else{
            for (e in dataList ?: emptyList() ) {
                Log.i(this.toString(), "${e.fname} - ${e.lname}")
                var user = SignedInUser()
                user.fname = e.fname
                user.lname = e.lname
                user.email = user.email
                user.phone = e.phone
                user.password = e.password
                user.budget = e.budget
            }
        }

    }
}