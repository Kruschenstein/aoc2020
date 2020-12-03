package org.grorg.aoc

fun solveDay3p1(input: List<String>): Int {
    return countTreesForMove(move = Pair(3, 1), patternLength = input[0].length, input)
}

private fun countTreesForMove(move: Pair<Int, Int>, patternLength: Int, input: List<String>) =
    generateSequence(Pair(0, 0)) { (x, y) -> Pair(x + move.first, y + move.second) }
        .map { (x, y) -> Pair(x % patternLength, y) }
        .takeWhile { (_, y) -> y < input.size }
        .map { (x, y) -> input[y][x] }
        .count { it == '#' }

fun solveDay3p2(input: List<String>): Int {
    val moves = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
    return moves.map { countTreesForMove(move = it, patternLength = input[0].length, input) }
        .reduce(Int::times)
}
