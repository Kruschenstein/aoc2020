package org.grorg.aoc

typealias Coord = List<Int>

fun solveDay17p1(input: List<String>): Int {
    val grid = input
        .flatMapIndexed { y, element ->
            element.mapIndexed { x, cell -> if (cell == '#') { listOf(x, y, 0) } else null }
        }
        .filterNotNull()
        .toSet()

    val origin = listOf(0, 0, 0)
    val neighborsTransformation = sequenceOf(-1, 0, 1)
        .flatMap { x ->
            sequenceOf(-1, 0, 1)
                .flatMap { y ->
                    sequenceOf(-1, 0, 1).map { z -> listOf(x, y, z) }
                }
        }
        .filter { it != origin }
        .toList()

    return getNumberOfCellAfterSixGeneration(grid, neighborsTransformation)
}

private fun isAlive(grid: Set<Coord>, coord: Coord) = grid.contains(coord)

private fun isDead(grid: Set<Coord>, coord: Coord) = !grid.contains(coord)

fun solveDay17p2(input: List<String>): Int {
    val grid = input
        .flatMapIndexed { y, element ->
            element.mapIndexed { x, cell -> if (cell == '#') { listOf(x, y, 0, 0) } else null }
        }
        .filterNotNull()
        .toSet()

    val origin = listOf(0, 0, 0, 0)
    val neighborsTransformation = sequenceOf(-1, 0, 1)
        .flatMap { x ->
            sequenceOf(-1, 0, 1)
                .flatMap { y ->
                    sequenceOf(-1, 0, 1)
                        .flatMap { z ->
                        sequenceOf(-1, 0, 1).map { w -> listOf(x, y, z, w) }
                    }
                }
        }
        .filter { it != origin }
        .toList()

    return getNumberOfCellAfterSixGeneration(grid, neighborsTransformation)
}

private fun getNumberOfCellAfterSixGeneration(
    originalGrid: Set<Coord>,
    neighborsTransformation: List<List<Int>>
): Int {
    var grid = originalGrid
    val neighborsForGeneration = mutableSetOf<Coord>()

    for (i in 1..6) {
        val newGrid = mutableSetOf<Coord>()
        for (coord in grid) {
            val neighbors = neighborsTransformation
                .map { innerCoord -> innerCoord.zip(coord).map { it.first + it.second } }
            neighborsForGeneration.addAll(neighbors)
        }
        for (coord in neighborsForGeneration) {
            val neighbors = neighborsTransformation
                .map { innerCoord -> innerCoord.zip(coord).map { it.first + it.second } }
                .filter { isAlive(grid, it) }
                .count()
            if (isDead(grid, coord) && neighbors == 3 ||
                isAlive(grid, coord) && neighbors in 2..3
            ) {
                newGrid.add(coord)
            }
        }
        neighborsForGeneration.clear()
        grid = newGrid
    }
    return grid.count()
}
