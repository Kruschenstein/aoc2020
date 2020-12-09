package org.grorg.aoc

fun solveDay9p1(input: List<String>): Long {
    val data = input.map(String::toLong)
    val offset = 25

    return data.asSequence()
        .drop(offset)
        .filterIndexed { i, element ->
            val previous = data.slice(i..(i + offset)).toSortedSet()
            !previous.any { previous.contains(element - it) }
        }
        .first()
}

fun solveDay9p2(input: List<String>): Long {
    val anomaly = solveDay9p1(input)
    val data = input.map(String::toLong)

    return generateSequence(0) { it + 1 }
        .take(data.size)
        .flatMap { i ->
            generateSequence(i) { j -> j + 1 }
                .map { j -> data.slice(i..j) }
                .dropWhile { slice -> slice.sum() < anomaly }
                .takeWhile { slice -> slice.sum() == anomaly }
                .map(List<Long>::sorted)
                .map { slice -> slice.first() + slice.last() }
        }
        .first()
}