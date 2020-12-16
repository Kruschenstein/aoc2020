package org.grorg.aoc

fun computeLatest(input: List<String>, step: Int): Int {
    val init = input[0].split(",").map(String::toInt)
    val memory = IntArray(step) { -1 }
    for (i in init.indices) {
        memory[init[i]] = i + 1
    }
    var latest = init.last()
    for (i in (init.size + 1)..step) {
        val oldLatest = latest
        latest = if (memory[latest] != -1) {
            i - 1 - memory[latest]
        } else {
            0
        }
        memory[oldLatest] = i - 1
    }
    return latest
}

fun solveDay15p1(input: List<String>): Int {
    return computeLatest(input, 2020)
}

fun solveDay15p2(input: List<String>): Int {
    return computeLatest(input, 30_000_000)
}