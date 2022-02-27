package com.evolution.bootcamp.assignment.poker.model.cards

import com.evolution.bootcamp.assignment.poker.utils.Parser

sealed class Rank(private val strength: Int) : Comparable<Rank> {

    override fun compareTo(other: Rank): Int =
        when {
            strength == other.strength -> 0
            strength < other.strength -> -1
            strength > other.strength -> 1
            else -> throw IllegalStateException()
        }

    override fun toString(): String = when (this) {
        Two -> "2"
        Three -> "3"
        Four -> "4"
        Five -> "5"
        Six -> "6"
        Seven -> "7"
        Eight -> "8"
        Nine -> "9"
        Ten -> "T"
        Jack -> "J"
        Queen -> "Q"
        King -> "K"
        Ace -> "A"
    }

    operator fun minus(rank: Rank): Int = strength - rank.strength

    companion object : Parser<Char, Rank> {

        override fun from(value: Char) = when (value) {
            '2' -> Two
            '3' -> Three
            '4' -> Four
            '5' -> Five
            '6' -> Six
            '7' -> Seven
            '8' -> Eight
            '9' -> Nine
            'T' -> Ten
            'J' -> Jack
            'Q' -> Queen
            'K' -> King
            'A' -> Ace
            else -> throw IllegalStateException("Unknown rank: $value")
        }
    }
}

object Two : Rank(0)
object Three : Rank(1)
object Four : Rank(2)
object Five : Rank(3)
object Six : Rank(4)
object Seven : Rank(5)
object Eight : Rank(6)
object Nine : Rank(7)
object Ten : Rank(8)
object Jack : Rank(9)
object Queen : Rank(10)
object King : Rank(11)
object Ace : Rank(12)