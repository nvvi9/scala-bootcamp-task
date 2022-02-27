package com.evolution.bootcamp.assignment.poker.model.cards

import com.evolution.bootcamp.assignment.poker.utils.Parser

class Card private constructor(val rank: Rank, val suit: Suit) {

    override fun toString(): String = "$rank$suit"

    companion object : Parser<String, Card> {

        override fun from(value: String): Card {
            val rank = Rank.from(value.first())
            val suit = Suit.from(value.last())
            return Card(rank, suit)
        }
    }
}