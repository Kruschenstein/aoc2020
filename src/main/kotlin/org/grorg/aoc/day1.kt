package org.grorg.aoc

import java.lang.Exception

const val TARGET = 2020;

class SolutionNotFound: Exception("no solution have been found")

private fun sortedInput(input: List<String>) = input
    .map { it.toLong() }
    .toCollection(sortedSetOf())

fun solveDay1p1(input: List<String>): Long {
    val sortedInputs = sortedInput(input)

    return sortedInputs
        .find { sortedInputs.contains(TARGET - it) }
        ?.let { it * (TARGET - it) }
        ?: throw SolutionNotFound()
}

fun solveDay1p2(input: List<String>): Long {
    val sortedInput = sortedInput(input)
    val firstPart = sortedInput.map { TARGET - it }

    return firstPart
       .map { j ->
            sortedInput
                .takeWhile { j - it >= it }
                .find { sortedInput.contains(j - it) }
                ?.let { (TARGET - j) * it * (j - it) }
        }
        .find { it != null }
        ?: throw SolutionNotFound()
}