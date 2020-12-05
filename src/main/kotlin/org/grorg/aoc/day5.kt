package org.grorg.aoc

fun solveDay5p1(input: List<String>): Int {
    return toPlaceId(input)
        .maxOrNull()
        ?: throw NotFoundSolutionException()
}

private fun toPlaceId(input: List<String>) = input.asSequence()
    .map { computeRowNumber(it.subSequence(0..6)) * 8 + computeColumnNumber(it.subSequence(7..9)) }

fun computeColumnNumber(columnCode: CharSequence): Int =
    columnCode.map { if (it == 'L') '0' else '1' }.joinToString("").toInt(2)

fun computeRowNumber(rowCode: CharSequence): Int =
    rowCode.map { if (it == 'F') '0' else '1' }.joinToString("").toInt(2)

fun solveDay5p2(input: List<String>): Int {
    return toPlaceId(input)
        .sorted()
        .zipWithNext()
        .find { (a, b) -> a + 1 != b }
        ?.first
        ?.let { it + 1 }
        ?: throw NotFoundSolutionException()
}

