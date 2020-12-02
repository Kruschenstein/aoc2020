package org.grorg.aoc

fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText().trim()
}

fun main() {
    println("day 1.1 : ${solveDay1p1(getResourceAsText("/day1").split("\n"))}")
    println("day 1.2 : ${solveDay1p2(getResourceAsText("/day1").split("\n"))}")
    println("day 2.1 : ${solveDay2p1(getResourceAsText("/day2").split("\n"))}")
    println("day 2.2 : ${solveDay2p2(getResourceAsText("/day2").split("\n"))}")
}
