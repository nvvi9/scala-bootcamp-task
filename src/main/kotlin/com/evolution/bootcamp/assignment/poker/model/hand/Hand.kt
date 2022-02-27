package com.evolution.bootcamp.assignment.poker.model.hand

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.utils.Parser

class Hand private constructor(val cards: List<Card>) {

    override fun toString(): String = cards.joinToString("") { "${it.rank}${it.suit}" }

    companion object : Parser<String, Hand> {

        override fun from(value: String): Hand {
            val cards = value.chunked(2).map {
                Card.from(it)
            }

            return Hand(cards)
        }
    }
}