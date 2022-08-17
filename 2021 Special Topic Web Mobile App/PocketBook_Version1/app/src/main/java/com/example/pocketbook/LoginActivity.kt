package com.example.pocketbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnReg = findViewById<View>(R.id.btnReg) as Button
        val edtxtUser = findViewById<View>(R.id.edtxtUser) as EditText
        val edtxtPass = findViewById<View>(R.id.edtxtPass) as EditText
        val btnLogin = findViewById<View>(R.id.btnLogin) as Button
        val txtNewUser = findViewById<View>(R.id.txtNewUser) as TextView

        btnReg.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java)
            )
        }

        btnLogin.setOnClickListener {

            startActivity(Intent(this@LoginActivity, NavigationDrawerActivity::class.java))

//            val userInput = edtxtUser.text.toString().trim { it <= ' ' }
//            val passwordInput = edtxtPass.text.toString().trim { it <= ' ' }
//            val user: User = db.getUserByEmailAndPassword(userInput, passwordInput)
//            if (user == null) {
//                Toast.makeText(applicationContext, "Invalid Credentials", Toast.LENGTH_LONG).show()
//            } else {
//                LoggedInUser.setLoggedInUser(user)
//                val month: String
//                val year: String
//                month = getMonth(Calendar.getInstance()[Calendar.MONTH])
//                year = Calendar.getInstance()[Calendar.YEAR].toString() + ""
//                val budget =
//                    Budget(db.getCurrentBudget(LoggedInUser.getLoggedInUser().getID(), year, month))
//                LoggedInUser.setCurrentBudget(budget.getAmount())
//                if (LoggedInUser.getCurrentBudget().equals("")) {
//                    db.insertBudget(month, year, "0", LoggedInUser.getLoggedInUser().getID())
//                    LoggedInUser.setCurrentBudget("0")
//                } else {
//                    LoggedInUser.setCurrentBudget(
//                        db.getCurrentBudget(
//                            LoggedInUser.getLoggedInUser().getID(), year, month
//                        ).getAmount()
//                    )
//                }
//                finish()
//                startActivity(Intent(this@LoginActivity, NavigationDrawerActivity::class.java))
//            }
        }
    }
}