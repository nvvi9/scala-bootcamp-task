package com.evolution.bootcamp.assignment.poker.model.cards

import com.evolution.bootcamp.assignment.poker.utils.Parser

sealed class Suit {

    override fun toString(): String = when (this) {
        Clubs -> "c"
        Diamonds -> "d"
        Hearts -> "h"
        Spades -> "s"
    }

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