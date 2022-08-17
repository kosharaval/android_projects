package com.example.pocketbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.util.ArrayList
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val txtPD = findViewById<View>(R.id.txtPersD) as TextView
        val edtxtFName = findViewById<View>(R.id.edtxtName) as EditText
        val edtxtLstName = findViewById<View>(R.id.edtxtLstName) as EditText
        val edtxtEmail = findViewById<View>(R.id.edtxtEmail) as EditText
        val edtxtPhno = findViewById<View>(R.id.edtxtPhno) as EditText
        val edtxtRegPass = findViewById<View>(R.id.edtxtRegPass) as EditText
        val edtxtConPass = findViewById<View>(R.id.edtxtConPass) as EditText

        val btnRegSub = findViewById<View>(R.id.btnRegSub) as Button
        btnRegSub.setOnClickListener {

            startActivity(Intent(this@RegistrationActivity, NavigationDrawerActivity::class.java))

//            var isValid = true
//            val listControls: MutableList<EditText> =
//                ArrayList()
//            listControls.add(edtxtFName)
//            listControls.add(edtxtLstName)
//            listControls.add(edtxtEmail)
//            listControls.add(edtxtPhno)
//            listControls.add(edtxtRegPass)
//            listControls.add(edtxtConPass)
//            for (e in listControls) {
//                if (e.text.toString().trim { it <= ' ' }.length == 0) {
//                    e.error = e.hint.toString().replace("Enter", "  ") + " is invalid"
//                    isValid = false
//                }
//            }
//            if (!isValidEmail(edtxtEmail.text.toString())) {
//                edtxtEmail.error = "Invalid email"
//                isValid = false
//            }
//            if (edtxtRegPass.text.toString().trim { it <= ' ' } != edtxtConPass.text.toString()
//                    .trim { it <= ' ' }) {
//                edtxtConPass.error = "Password does not match"
//                isValid = false
//            }
//            //                if(radioId == -1)
//            //                {
//            //                    Toast.makeText(Registration.this,"Please select gender",Toast.LENGTH_LONG).show();
//            //                    isValid = false;
//            //                }
//            //else{ isValid = true; }
//            if (isValid) {
//
////                val user = User(
////                    edtxtFName.text.toString(),
////                    edtxtLstName.text.toString(),
////                    edtxtEmail.text.toString(),
////                    edtxtPhno.text.toString(),
////                    edtxtRegPass.text.toString(),
////                    "female"
////                )
////                val x: Long = db.insertUser(user)
//                if (x > 0) {
//                    finish()
//                    startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
//                }
//            }
        }
    }

    fun isValidEmail(email: String?): Boolean {
        val regex = "^(.+)@(.+)$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}