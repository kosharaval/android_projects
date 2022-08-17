package com.example.writejsondemo

data class Card(val rank: Char, val suit: Suit) {
    override fun toString(): String {
        return String.format("%s%s", rank, suit)
    }

}