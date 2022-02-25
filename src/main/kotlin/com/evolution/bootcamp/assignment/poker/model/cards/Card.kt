package com.evolution.bootcamp.assignment.poker.model.cards

import com.evolution.bootcamp.assignment.poker.model.Parser

data class Card(val rank: Rank, val suit: Suit) {
    companion object : Parser<String, Card> {

        override fun from(value: String): Card {
            val rank = Rank.from(value.first())
            val suit = Suit.from(value.last())
            return Card(rank, suit)
        }
    }
}