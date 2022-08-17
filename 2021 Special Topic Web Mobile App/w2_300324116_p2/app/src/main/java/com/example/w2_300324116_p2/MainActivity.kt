package com.example.w2_300324116_p2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var activePlayer = 1
    fun buttonOnclick(view: View){

        val selectButton = view as Button
        var cellId = 0
        when(selectButton.id){
            R.id.buttonR1C1 -> cellId = 1
            R.id.buttonR1C2 -> cellId = 2
            R.id.buttonR1C3 -> cellId = 3
            R.id.buttonR2C1 -> cellId = 4
            R.id.buttonR2C2 -> cellId = 5
            R.id.buttonR2C3 -> cellId = 6
            R.id.buttonR3C1 -> cellId = 7
            R.id.buttonR3C2 -> cellId = 8
            R.id.buttonR3C3 -> cellId = 9
        }
        playGame(cellId, selectButton)
    }

    private fun playGame(cellId: Int, selectButton: Button) {

        if(activePlayer == 1){
            selectButton.text = "O"
            player1.add(cellId)
            activePlayer = 2

        }else{
            selectButton.text = "X"
            player2.add(cellId)
            activePlayer = 1
        }
        selectButton.isEnabled = false

        checkWinner()
    }

    private fun checkWinner() {

        val textViewWinner = findViewById<TextView>(R.id.textViewWinner)
        var winner = -1
        //check row combination
        if(player1.contains((1)) && player1.contains(2) && player1.contains(3))
        {
            winner = 1
        }
        if(player2.contains((1)) && player2.contains(2) && player2.contains(3))
        {
            winner = 2
        }
        if(player1.contains((4)) && player1.contains(5) && player1.contains(6))
        {
            winner = 1
        }
        if(player2.contains((4)) && player2.contains(5) && player2.contains(6))
        {
            winner = 2
        }
        if(player1.contains((7)) && player1.contains(8) && player1.contains(9))
        {
            winner = 1
        }
        if(player2.contains((7)) && player2.contains(8) && player2.contains(9))
        {
            winner = 2
        }

        //check column combination
        if(player1.contains((1)) && player1.contains(4) && player1.contains(7))
        {
            winner = 1
        }
        if(player2.contains((1)) && player2.contains(4) && player2.contains(7))
        {
            winner = 2
        }
        if(player1.contains((2)) && player1.contains(5) && player1.contains(8))
        {
            winner = 1
        }
        if(player2.contains((2)) && player2.contains(5) && player2.contains(8))
        {
            winner = 2
        }
        if(player1.contains((3)) && player1.contains(6) && player1.contains(9))
        {
            winner = 1
        }
        if(player2.contains((3)) && player2.contains(6) && player2.contains(9))
        {
            winner = 2
        }

        //checking diagonal combination
        if(player1.contains((1)) && player1.contains(5) && player1.contains(9))
        {
            winner = 1
        }
        if(player2.contains((3)) && player2.contains(5) && player2.contains(7))
        {
            winner = 2
        }

        //when game is draw
        if(winner !=-1){
            if(winner == 1){
                textViewWinner.setText("WINNER is PLAYER 1")
                //Toast.makeText(this,"WINNER is PLAYER 1" , Toast.LENGTH_LONG).show()
            }else{
                textViewWinner.setText("WINNER is PLAYER 2")
                //Toast.makeText(this,"WINNER is PLAYER 2" , Toast.LENGTH_LONG).show()
            }
        }
        else{
            textViewWinner.setText("Game is Draw")
        }
    }
}