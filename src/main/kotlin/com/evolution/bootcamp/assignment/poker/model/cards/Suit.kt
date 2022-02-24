package com.evolution.bootcamp.assignment.poker.model.cards

import com.evolution.bootcamp.assignment.poker.model.Parser

sealed class Suit {

    companion object : Parser<Char, Suit> {

        override fun from(value: Char) = when (value) {
            's' -> Spades
            'h' -> Hearts
            'd' -> Diamonds
            'c' -> Clubs
            else -> throw IllegalStateException("Unknown suit: $value")
        }
    }
}

object Spades : Suit()
object Hearts : Suit()
object Diamonds : Suit()
object Clubs : Suit()