package com.example.readonlinejson


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.readonlinejson.data.DataRepository
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drepo = DataRepository(this.application)

        val drepoMonsterData = drepo.getMonsterData()
        Log.i("Main Frm DataRepo", "${drepoMonsterData}")


        drepo.getMonsterData().observe(this, Observer
        {
            Log.i("MainAct", "inside")
            var s = ""
            for (m in it ?: emptyList()) {
                s += "${m.monsterName} (\$${m.price}) (${m.imageUrl})\n"
            }

            txtResult.text = s
            Log.i("MainAct", "done")
        })
    }


}