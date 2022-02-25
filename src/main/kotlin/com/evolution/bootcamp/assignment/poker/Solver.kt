package com.evolution.bootcamp.assignment.poker

import com.evolution.bootcamp.assignment.poker.model.Board

object Solver {

    fun process(line: String): String = Board.from(line).sortedByStrength.joinToString(" ")
}