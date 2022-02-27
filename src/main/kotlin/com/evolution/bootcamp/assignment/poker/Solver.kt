package com.evolution.bootcamp.assignment.poker

import com.evolution.bootcamp.assignment.poker.boards.Board
import com.evolution.bootcamp.assignment.poker.utils.Try

object Solver {

    fun process(line: String): String =
        Try.runSafely { Board.from(line).sortedByStrengthWithWeight }
            .map { handsSorted ->
                handsSorted.groupBy { it.first }.map { entry ->
                    if (entry.value.size > 1) {
                        entry.value.map { it.second }.sortedBy { it.toString() }.joinToString("=")
                    } else {
                        entry.value.first().second.toString()
                    }
                }.joinToString(" ")
            }
            .fold({ it }, { "Error" })
}