package com.evolution.bootcamp.assignment.poker.model.cards

import com.evolution.bootcamp.assignment.poker.model.Parser

sealed class Rank(private val weakerThen: Set<Rank>) : Comparable<Rank> {

    override fun compareTo(other: Rank): Int =
        when {
            this == other -> 0
            weakerThen.contains(other) -> -1
            weakerThen.isEmpty() || !weakerThen.contains(other) -> 1
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

    operator fun minus(rank: Rank): Int = rank.weakerThen
        .indexOfFirst { it == this }
        .takeIf { it != -1 }
        ?.let { it + 1 }
        ?: -(weakerThen.indexOfFirst { it == rank } + 1)

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

object Two : Rank(setOf(Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace))
object Three : Rank(setOf(Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace))
object Four : Rank(setOf(Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace))
object Five : Rank(setOf(Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace))
object Six : Rank(setOf(Seven, Eight, Nine, Ten, Jack, Queen, King, Ace))
object Seven : Rank(setOf(Eight, Nine, Ten, Jack, Queen, King, Ace))
object Eight : Rank(setOf(Nine, Ten, Jack, Queen, King, Ace))
object Nine : Rank(setOf(Ten, Jack, Queen, King, Ace))
object Ten : Rank(setOf(Jack, Queen, King, Ace))
object Jack : Rank(setOf(Queen, King, Ace))
object Queen : Rank(setOf(King, Ace))
object King : Rank(setOf(Ace))
object Ace : Rank(setOf())