package org.grorg.aoc

private enum class Color {
    BLACK,
    WHITE,
    ;
    fun flip() = if (this == BLACK) WHITE else BLACK
}

private enum class HexagonalDirection(val coordinate: Coordinate) {
    NORTH_EAST(Coordinate(0, 1)),
    NORTH_WEST(Coordinate(1, 1)),
    WEST(Coordinate(1, 0)),
    SOUTH_WEST(Coordinate(0, -1)),
    SOUTH_EAST(Coordinate(-1, -1)),
    EAST(Coordinate(-1, 0))
}

private data class Coordinate(val x: Int, val y: Int) {
    fun next(direction: HexagonalDirection): Coordinate =
        Coordinate(x + direction.coordinate.x, y + direction.coordinate.y)
}

private fun readDirection(line: String): List<HexagonalDirection> {
    val result = mutableListOf<HexagonalDirection>()
    var i = 0
    while (i < line.length) {
        result.add(
            if (line[i] == 's') {
                if (line[++i] == 'e') {
                    HexagonalDirection.SOUTH_EAST
                } else {
                    HexagonalDirection.SOUTH_WEST
                }
            } else if (line[i] == 'n') {
                if (line[++i] == 'e') {
                    HexagonalDirection.NORTH_EAST
                } else {
                    HexagonalDirection.NORTH_WEST
                }
            } else if (line[i] == 'e') {
                HexagonalDirection.EAST
            } else {
                HexagonalDirection.WEST
            }
        )

        ++i
    }
    return result
}

fun solveDay24p1(input: List<String>): Int {
    val directions = input.map(::readDirection)
    val ground = mutableMapOf(Coordinate(0, 0) to Color.WHITE)

    for (directionList in directions) {
        var actualTile = Coordinate(0, 0)
        for (direction in directionList) {
            actualTile = actualTile.next(direction)
        }
        ground[actualTile] = (ground[actualTile] ?: Color.WHITE).flip()
    }
    return ground.values.count { it == Color.BLACK }
}

fun solveDay24p2(input: List<String>): Int {
    val directions = input.map(::readDirection)
//    var ground = mutableMapOf(Coordinate(0, 0) to Color.WHITE)
    var ground = mutableMapOf(Coordinate(0, 0) to Color.WHITE)

    for (directionList in directions) {
        var actualTile = Coordinate(0, 0)
        for (direction in directionList) {
            actualTile = actualTile.next(direction)
        }
        ground[actualTile] = (ground[actualTile] ?: Color.WHITE).flip()
    }

    for (i in 1..100) {
        val newGround = mutableMapOf<Coordinate, Color>()
        val tiles = mutableSetOf<Coordinate>()
        for (tile in ground) {
            tiles.addAll(HexagonalDirection.values().map { tile.key.next(it) })
        }

        for (coord in tiles) {
            val color = ground[coord] ?: Color.WHITE
            val numberOfBlackNeighbor =
                HexagonalDirection.values().map { ground[coord.next(it)] }.count { it == Color.BLACK }

            if (color == Color.BLACK && (numberOfBlackNeighbor == 0 || numberOfBlackNeighbor > 2)) {
                newGround[coord] = Color.WHITE
            } else if (color == Color.WHITE && numberOfBlackNeighbor == 2) {
                newGround[coord] = Color.BLACK
            } else {
                newGround[coord] = color
            }
        }
        ground = newGround
    }
    return ground.values.count { it == Color.BLACK }
}