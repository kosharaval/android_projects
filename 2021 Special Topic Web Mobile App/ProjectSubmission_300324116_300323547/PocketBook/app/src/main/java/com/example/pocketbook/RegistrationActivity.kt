package com.example.pocketbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.pocketbook.entities.SignedInUser
import com.example.pocketbook.entities.User
import com.squareup.moshi.Types
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_registration.*
import java.io.InputStream
import java.net.URISyntaxException
import java.util.ArrayList
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {

    var fname: String = ""
    var lname: String = ""
    var email: String = ""
    var phone: String = ""
    var password: String = ""

    var mSocket: Socket? = null
    private val userType = Types.newParameterizedType(List::class.java, User::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //val txtPD = findViewById<View>(R.id.txtPersD) as TextView
        val editTextFirstName = findViewById<View>(R.id.editTextFirstName) as EditText
        val editTextLastName = findViewById<View>(R.id.editTextLastName) as EditText
        val editTextEmailRegistration = findViewById<View>(R.id.editTextEmailRegistration) as EditText
        val editTextPhone = findViewById<View>(R.id.editTextPhone) as EditText
        val editTextPasswordResgitration = findViewById<View>(R.id.editTextPasswordResgitration) as EditText
        val editTextConfirmPassword = findViewById<View>(R.id.editTextConfirmPassword) as EditText

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
            var isValid = true
            val listControls: MutableList<EditText> = ArrayList()
            listControls.add(editTextFirstName)
            listControls.add(editTextLastName)
            listControls.add(editTextEmailRegistration)
            listControls.add(editTextPhone)
            listControls.add(editTextPasswordResgitration)
            listControls.add(editTextConfirmPassword)
            for (e in listControls) {
                if (e.text.toString().trim { it <= ' ' }.length == 0) {
                    e.error = e.hint.toString().replace("Enter", "  ") + " is invalid"
                    Toast.makeText(applicationContext, "Enter valid data", Toast.LENGTH_LONG)
                    isValid = false
                }
            }
            if (!isValidEmail(editTextEmailRegistration.getText().toString())) {
                editTextEmailRegistration.setError("Invalid email")
                Toast.makeText(applicationContext, "Invalid email format", Toast.LENGTH_LONG)
                isValid = false
            }
            if (editTextPasswordResgitration.getText().toString().trim { it <= ' ' } != editTextConfirmPassword.getText()
                    .toString().trim { it <= ' ' }) {
                editTextConfirmPassword.setError("Password does not match")
                Toast.makeText(applicationContext, "Password does not match", Toast.LENGTH_LONG)
                isValid = false
            }
            if (isValid) {
                val user = SignedInUser(
                    editTextFirstName.text.toString(),
                    editTextLastName.text.toString(),
                    editTextEmailRegistration.text.toString(),
                    editTextPhone.text.toString(),
                    editTextPasswordResgitration.text.toString(),
                    0.0
                )
                if (user != null) {
                    fname = editTextFirstName.text.toString()
                    lname = editTextLastName.text.toString()
                    email = editTextEmailRegistration.text.toString()
                    phone = editTextPhone.text.toString()
                    password = editTextPasswordResgitration.text.toString()
                    mSocket?.emit("addUser", fname,lname,email,phone,password,0)
                    startActivity(Intent(this@RegistrationActivity, NavigationDrawerActivity::class.java))
                }
            }
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