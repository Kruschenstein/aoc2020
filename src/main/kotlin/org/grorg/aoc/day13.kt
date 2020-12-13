package org.grorg.aoc

fun solveDay13p1(input: List<String>): Int {
    val start = input[0].toInt()
    return input[1]
        .split(",")
        .asSequence()
        .filter { it != "x" }
        .map { it.toInt() }
        .map { Pair(it, if (start % it != 0) it * (start / it + 1) else it * (start / it)) }
        .minByOrNull { (_, b) -> b }
        ?.let { (a, b) -> (b - start) * a }
        ?: throw NotFoundSolutionException()
}

fun findModulus1(x: Long, modulus: Long) = generateSequence(0) { it + 1 }.filter { (x * it % modulus) == 1L }.first()

fun solveDay13p2(input: List<String>): Long {
    // Chinese remainder theorem implementation
    val elements = input[1]
        .split(",")
        .asSequence()
        .zip(generateSequence(0) { it + 1 })
        .filter { (a, _) -> a != "x" }
        .map { (a, b) ->
            val al = a.toLong()
            Pair(al, (al - b) % al)
        }
        .toMap()
    val m = elements.keys.reduce(Long::times)
    val mn = elements.map { (a, _) -> Pair(a, findModulus1(m / a, a)) }.toMap()
    return elements.keys
        .map { elements[it]!! * (m / it) * mn[it]!! }
        .reduce(Long::plus) % m
}