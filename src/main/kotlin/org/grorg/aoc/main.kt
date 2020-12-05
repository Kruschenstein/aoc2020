package org.grorg.aoc

fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText().trim()
}

fun main() {
    println("day 1.1 : ${solveDay1p1(getResourceAsText("/day1").split("\n"))}")
    println("day 1.2 : ${solveDay1p2(getResourceAsText("/day1").split("\n"))}")
    println("day 2.1 : ${solveDay2p1(getResourceAsText("/day2").split("\n"))}")
    println("day 2.2 : ${solveDay2p2(getResourceAsText("/day2").split("\n"))}")
    println("day 3.1 : ${solveDay3p1(getResourceAsText("/day3").split("\n"))}")
    println("day 3.2 : ${solveDay3p2(getResourceAsText("/day3").split("\n"))}")
    println("day 4.1 : ${solveDay4p1(getResourceAsText("/day4").split("\n"))}")
    println("day 4.2 : ${solveDay4p2(getResourceAsText("/day4").split("\n"))}")
}
