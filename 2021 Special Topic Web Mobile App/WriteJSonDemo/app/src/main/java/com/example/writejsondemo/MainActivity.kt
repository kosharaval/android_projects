package com.example.writejsondemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val blackjackHand = BlackjackHand(
            Card('6', Suit.SPADES),
                Arrays.asList(
                    Card('4', Suit.CLUBS),
                    Card('A', Suit.HEARTS)
                )
        )

        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<BlackjackHand> = moshi.adapter(BlackjackHand::class.java)

        val json: String = jsonAdapter.toJson(blackjackHand)
        txtResult.setText(json)
        txtResult.append("\n\n")

        val hand = jsonAdapter.fromJson(json)
        txtResult.append(hand?.visible_cards.toString())
    }
}