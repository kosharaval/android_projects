package com.example.pocketbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.pocketbook.entities.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_registration.*
import java.io.InputStream
import java.net.URISyntaxException
import java.util.regex.Pattern
import kotlin.math.ln

class RegistrationActivity : AppCompatActivity() {

    var fname: String = ""
    var lname: String = ""
    var email: String = ""
    var phone: String = ""
    var password: String = ""

    var mSocket: Socket? = null
    var userName: String? = null;
    lateinit var roomName: String;
    private val myType = Types.newParameterizedType(List::class.java, User::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //val txtPD = findViewById<View>(R.id.txtPersD) as TextView
        val edtxtFName = findViewById<View>(R.id.edtxtName) as EditText
        val edtxtLstName = findViewById<View>(R.id.edtxtLstName) as EditText
        val edtxtEmail = findViewById<View>(R.id.edtxtEmail) as EditText
        val edtxtPhno = findViewById<View>(R.id.edtxtPhno) as EditText
        val edtxtRegPass = findViewById<View>(R.id.edtxtRegPass) as EditText
        //val edtxtConPass = findViewById<View>(R.id.edtxtConPass) as EditText

        val buttonRegistration = findViewById<View>(R.id.buttonRegistration) as Button

        var string: String?
        try {
            val inputStream: InputStream = assets.open("source.txt")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            string = String(buffer)
            hostTxt.setText(string)
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }


        try {
            mSocket = IO.socket(hostTxt.text.toString())

        } catch (e: URISyntaxException) {
            Log.d("URI error", e.message.toString())
        }


        try {
            mSocket?.connect()
            result.text = "connected to " + hostTxt.text.toString() + " " + mSocket?.connected()
        } catch (e: Exception) {
            result.text = " Failed to connect. " + e.message
        }

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            result.text = "sending"
            mSocket?.emit("messages", "hi")
        });

        buttonRegistration.setOnClickListener {

            fname = edtxtFName.text.toString()
            lname = edtxtLstName.text.toString()
            email = edtxtEmail.text.toString()
            phone = edtxtPhno.text.toString()
            password = edtxtRegPass.text.toString()
            mSocket?.emit("addUser", fname,lname,email,phone,password)
            startActivity(Intent(this@RegistrationActivity, NavigationDrawerActivity::class.java))
        }
    }


    var onNewUser = Emitter.Listener {
        val message = it[0] as String
        result.text = message
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket?.disconnect()
    }

    fun isValidEmail(email: String?): Boolean {
        val regex = "^(.+)@(.+)$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}