package org.grorg.aoc

data class Tile(val id: Long, var data: List<String>) {
    val borders = listOf(
        data[0],
        data.last(),
        data.map { it.first() }.joinToString(""),
        data.map { it.last() }.joinToString(""),
        data[0].reversed(),
        data.last().reversed(),
        data.map { it.first() }.joinToString("").reversed(),
        data.map { it.last() }.joinToString("").reversed(),
    )

    val neighbors = mutableSetOf<Tile>()

    var up: Tile? = null
    var right: Tile? = null
    var down: Tile? = null
    var left: Tile? = null

    fun upBorder() = data.first()
    fun rightBorder() = data.map { it.last() }.joinToString("")
    fun downBorder() = data.last()
    fun leftBorder() = data.map { it.first() }.joinToString("")

    fun rotate() {
        val newData = List(data.size) { StringBuilder(data.first().length) }
        for (i in data.indices) {
            for (line in data.reversed()) {
                newData[i].append(line[i])
            }
        }
        data = newData.map { it.toString() }
    }

    fun flip() {
        data = data.map { it.reversed() }
    }

    fun clearNeighborsOrientation() {
        up = null
        right = null
        down = null
        left = null
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other != null &&
                other is Tile &&
                id == other.id
    }
}

fun readTiles(input: List<String>): List<Tile> {
    val result = mutableListOf<Tile>()
    var tileData = mutableListOf<String>()
    var tileId = 0L
    for (line in input) {
        when {
            line.startsWith("Tile ") -> {
                tileId = line.substring("Tile ".length, line.length - 1).toLong()
            }
            line.isEmpty() -> {
                result.add(Tile(tileId, tileData))
                tileData = mutableListOf()
            }
            else -> {
                tileData.add(line)
            }
        }
    }
    result.add(Tile(tileId, tileData))
    return result
}

fun allBorderExceptGiven(tile: Tile, tiles: List<Tile>): Set<String> {
    return tiles
        .asSequence()
        .filter { it != tile }
        .flatMap { it.borders }
        .toSet()
}

fun solveDay20p1(input: List<String>): Long {
    val tiles = readTiles(input)
    val corners = mutableListOf<Tile>()
    for (tile in tiles) {
        val border = allBorderExceptGiven(tile, tiles)
        val count = tile.borders.filter { border.contains(it) }.count()
        if (count == 4) {
            corners.add(tile)
        }
    }
    return corners.map { it.id }.reduceRight(Long::times)
}

fun rotate(image: String): String {
    val data = image.split("\n")
    val newData = List(data.size) { StringBuilder(data.first().length) }
    for (i in data.indices) {
        for (line in data.reversed()) {
            newData[i].append(line[i])
        }
    }
    return newData.joinToString("\n") { it.toString() }
}

fun flip(image: String): String {
    val data = image.split("\n")
    return data.joinToString("\n") { it.reversed() }
}

fun solveDay20p2(input: List<String>): Int {
    val tiles = readTiles(input)

    associateTilesWithNeighbors(tiles)
    val topLeftCorner = getTopLeftCorner(tiles)
    associateTilesWithNeighbors(topLeftCorner, tiles)
    val image = createImage(tiles, topLeftCorner)

    val numberOfTag = image.count { it == '#' }
    val monsterLength = 15
    return numberOfTag - findMonsterNumber(image) * monsterLength
}

private fun findMonsterNumber(image: String): Int {
    var currentImage = image
    for (i in 1..4) {
        val match = countMonster(currentImage)
        if (match > 0) {
            return match
        }
        val otherMatch = countMonster(flip(currentImage))
        if (otherMatch > 0) {
            return otherMatch
        }
        currentImage = rotate(currentImage)
    }
    return 0
}

private fun countMonster(image: String): Int {
    val monsterIndices = listOf(
        listOf(18),
        listOf(0, 5, 6, 11, 12, 17, 18, 19),
        listOf(1, 4, 7, 10, 13, 16)
    )
    val splitImage = image.split("\n")
    var counter = 0
    for (i in 0 until splitImage.size - 3) {
        val first = i
        val second = i + 1
        val third = i + 2

        for (j in 0 until splitImage[0].length - 19) {
            if (monsterIndices[0].all { splitImage[first][it + j] == '#' } &&
                monsterIndices[1].all { splitImage[second][it + j] == '#' } &&
                monsterIndices[2].all { splitImage[third][it + j] == '#' }
            ) {
                counter++
            }
        }
    }
    return counter
}

private fun computeOtherElementThatIsNotMonster(
    currentImage: String,
    match: Int
) = currentImage.count { it == '#' } - 15 * match

private fun createImage(
    tiles: List<Tile>,
    topLeftCorner: Tile
): String {
    val tileLineWithoutBorderSize = tiles.first().data.size - 2
    val imageLength = tileLineWithoutBorderSize * 2 * tiles.size
    val imageBuilder = StringBuilder(imageLength)

    val tileBorderlessRange = 1 until tiles.first().data.size - 1

    var currentImage: Tile? = topLeftCorner

    while (currentImage != null) {
        for (i in tileBorderlessRange) {
            var right: Tile? = currentImage
            while (right != null) {
                imageBuilder.append(right.data[i].substring(tileBorderlessRange))
                right = right.right
            }
            imageBuilder.append('\n')
        }
        currentImage = currentImage.down
    }
    return imageBuilder.toString().trim('\n')
}

private fun associateTilesWithNeighbors(
    topLeftCorner: Tile,
    tiles: List<Tile>
) {
    val visitedTiles = mutableSetOf(topLeftCorner)
    val toVisit = topLeftCorner.neighbors.toMutableList()
    while (visitedTiles.size != tiles.size) {
        val tile = toVisit.removeFirst()
        for (neighbor in tile.neighbors) {
            if (neighbor in visitedTiles) {
                continue
            }
            for (i in 1..4) {
                if (matchAndAffectTileOrFlipAndRetry(tile, neighbor)) {
                    break
                }
                neighbor.flip()
                neighbor.rotate()
            }
        }
        visitedTiles.add(tile)
        toVisit.addAll(tile.neighbors)
        toVisit.removeAll(visitedTiles)
    }
}

private fun getTopLeftCorner(tiles: List<Tile>): Tile {
    val topLeftCorner = tiles.first { it.neighbors.size == 2 }
    while (topLeftCorner.right == null && topLeftCorner.down == null) {
        for (neighbor in topLeftCorner.neighbors) {
            for (i in 1..4) {
                if (matchAndAffectTileOrFlipAndRetry(topLeftCorner, neighbor)) {
                    break
                }
                neighbor.flip()
                neighbor.rotate()
            }
        }
        if (topLeftCorner.right == null || topLeftCorner.down == null) {
            topLeftCorner.clearNeighborsOrientation()
            topLeftCorner.neighbors.forEach(Tile::clearNeighborsOrientation)
            topLeftCorner.rotate()
        }
    }
    return topLeftCorner
}

private fun associateTilesWithNeighbors(tiles: List<Tile>) {
    for (tile in tiles) {
        for (otherTile in tiles) {
            if (tile.id == otherTile.id) {
                continue
            }
            if (tile.borders.any { otherTile.borders.contains(it) }) {
                tile.neighbors.add(otherTile)
                otherTile.neighbors.add(tile)
            }
        }
    }
}

private fun matchAndAffectTileOrFlipAndRetry(tile: Tile, otherTile: Tile): Boolean {
    if (matchAndAffectTile(tile, otherTile)) {
        return true
    }
    otherTile.flip()
    if (matchAndAffectTile(tile, otherTile)) {
        return true
    }
    return false
}

private fun matchAndAffectTile(tile: Tile, remainingTile: Tile): Boolean {
    if (tile.id == remainingTile.id) {
        return false
    }
    if (tile.up == null && tile.upBorder() == remainingTile.downBorder()) {
        tile.up = remainingTile
        remainingTile.down = tile
    }
    else if (tile.left == null && tile.leftBorder() == remainingTile.rightBorder()) {
        tile.left = remainingTile
        remainingTile.right = tile
    }
    else if (tile.down == null && tile.downBorder() == remainingTile.upBorder()) {
        tile.down = remainingTile
        remainingTile.up = tile
    }
    else if (tile.right == null && tile.rightBorder() == remainingTile.leftBorder()) {
        tile.right = remainingTile
        remainingTile.left = tile
    }
    else return false
    return true
}