package com.example.writejsondemo

data class BlackjackHand (val hidden_card: Card, val visible_cards : List<Card>)
{
    override fun toString(): String {
        return "hidden=" + hidden_card + ",visible=" + visible_cards;
    }

}




