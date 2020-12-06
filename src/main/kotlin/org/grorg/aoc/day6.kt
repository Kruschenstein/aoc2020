package org.grorg.aoc

fun solveDay6p1(input: List<String>): Int {
    val answersByGroup = input.fold(mutableListOf("")) { acc, s ->
        when (s) {
            "" -> acc.add("")
            else -> acc[acc.lastIndex] = "${acc.last()}$s"
        }
        acc
    }

    return answersByGroup
        .asSequence()
        .map { it.toSet().size }
        .sum()
}

fun solveDay6p2(input: List<String>): Int {
    val possibleAnswers = ('a'..'z').toSet()

    return input
        .fold(mutableListOf(possibleAnswers)) { acc, s ->
            when (s) {
                "" -> acc.add(possibleAnswers)
                else -> acc[acc.lastIndex] = acc.last().intersect(s.toSet())
            }
            acc
        }
        .asSequence()
        .map(Set<Char>::size)
        .sum()
}