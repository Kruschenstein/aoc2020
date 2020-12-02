package org.grorg.aoc

data class Entry(val target: Char, val min: Int, val max: Int, val password: String)

fun solveDay2p1(input: List<String>): Int {
    return input
        .map(::toEntry)
        .map { entry -> Pair(entry, entry.password.count { it == entry.target }) }
        .filter { (entry, occurrences) ->
            occurrences >= entry.min && occurrences <= entry.max
        }
        .count()
}

fun toEntry(input: String): Entry {
    val entry = input.split(" ")
    val minMax = entry[0].split("-")
    val target = entry[1][0]
    val password = entry[2]
    return Entry(target, minMax[0].toInt(), minMax[1].toInt(), password)
}

fun solveDay2p2(input: List<String>): Int {
    return input
        .map(::toEntry)
        .filter(::oneOccurrenceAtOnePositionNotBoth)
        .count()
}

fun oneOccurrenceAtOnePositionNotBoth(entry: Entry) =
    (entry.password[entry.min - 1] == entry.target)
        .xor(entry.password[entry.max - 1] == entry.target)
