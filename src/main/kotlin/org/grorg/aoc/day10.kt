package org.grorg.aoc

fun solveDay10p1(input: List<String>): Int {
    val numericInput = input.map(String::toInt)
    val maxInput = numericInput.maxOrNull()!! + 3

    val frequency = (sequenceOf(0, maxInput) +
            numericInput.asSequence())
        .sorted()
        .zipWithNext()
        .map { (a, b) -> b - a }
        .groupingBy { it }
        .eachCount()
    return frequency[1]!! * frequency[3]!!
}

fun solveDay10p2(input: List<String>): Long {
    val numericInput = listOf(0) + input.map(String::toInt).sorted()

    val numberOfPathFrom = mutableMapOf(Pair(0, 1L))
    for (i in 1 until numericInput.size) {
        for (j in (i - 1) downTo 0) {
            if (numericInput[i] - numericInput[j] < 4) {
                numberOfPathFrom[i] = (numberOfPathFrom[i] ?: 0L) + numberOfPathFrom[j]!!
            } else {
                break
            }
        }
    }
    return numberOfPathFrom[numberOfPathFrom.keys.maxOrNull()!!]!!
}
