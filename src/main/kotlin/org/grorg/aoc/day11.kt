package org.grorg.aoc

import kotlin.math.abs

fun solveDay11p1(input: List<String>): Int {
    val grid = input.toTypedArray()
    val newGrid = Array(input.size) { "" }
    while (true) {
        // val newGrid = mutableListOf<String>()
        for (y in input.indices) {
            val lineBuilder = StringBuilder()
            for (x in input[y].indices) {
                val cell = grid[y][x]
                val neighbors = sequenceOf(-1, 0, 1)
                    .flatMap { a -> sequenceOf(-1, 0, 1).map { b -> Pair(a, b) } }
                    .filter { it != Pair(0, 0) }
                    .map { (a, b) -> grid.getOrNull(y + a)?.getOrElse(x + b) { '.' } ?: '.' }
                    .filter { it == '#' }
                    .count()
                if (cell == '#' && neighbors > 3) {
                    lineBuilder.append('L')
                } else if (cell == 'L' && neighbors == 0) {
                    lineBuilder.append('#')
                } else {
                    lineBuilder.append(cell)
                }
            }
            newGrid[y] = lineBuilder.toString()
        }
        if (newGrid.contentEquals(grid)) {
            break
        } else {
            for (i in newGrid.indices) {
                grid[i] = newGrid[i]
            }
        }
    }

    return grid.map { it.count { c -> c == '#' } }.sum()
}

fun next(value: Int) = if (value == 0) 0 else value + (value / abs(value))

fun solveDay11p2(input: List<String>): Int {
    val height = input.size
    val weight = input[0].length
    val neighborSequence = sequenceOf(-1, 0, 1)
        .flatMap { a -> sequenceOf(-1, 0, 1).map { b -> Pair(a, b) } }
        .filter { it != Pair(0, 0) }

    val grid = input.toTypedArray()
    val newGrid = Array(input.size) { "" }

    while (true) {
        for (y in input.indices) {
            val lineBuilder = StringBuilder()
            for (x in input[y].indices) {
                val cell = grid[y][x]
                val neighbors = neighborSequence
                    .map { coords ->
                        generateSequence(coords) { (a, b) -> Pair(next(a), next(b)) }
                            .map { (a, b) -> Pair(y + a, x + b) }
                            .filter { (a, b) -> a <= -1 || a >= height || b <= -1 || b >= weight || grid[a][b] != '.' }
                            .first()
                    }
                    .filter { (a, b) -> grid.getOrNull(a)?.getOrNull(b) == '#' }
                    .count()

                if (cell == '#' && neighbors > 4) {
                    lineBuilder.append('L')
                } else if (cell == 'L' && neighbors == 0) {
                    lineBuilder.append('#')
                } else {
                    lineBuilder.append(cell)
                }
            }
            newGrid[y] = lineBuilder.toString()
        }
        if (newGrid.contentEquals(grid)) {
            break
        } else {
            for (i in newGrid.indices) {
                grid[i] = newGrid[i]
            }

        }
    }

    return grid.map { it.count { c -> c == '#' } }.sum()
}
