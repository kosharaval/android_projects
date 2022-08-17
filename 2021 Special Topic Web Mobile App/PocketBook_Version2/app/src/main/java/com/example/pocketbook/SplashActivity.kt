package com.example.pocketbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val task: TimerTask = object : TimerTask() {
            override fun run() {
                finish()
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
        }
        val opening = Timer()
        opening.schedule(task, 1500)
    }
}