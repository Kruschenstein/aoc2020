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
    println("day 5.1 : ${solveDay5p1(getResourceAsText("/day5").split("\n"))}")
    println("day 5.2 : ${solveDay5p2(getResourceAsText("/day5").split("\n"))}")
    println("day 6.1 : ${solveDay6p1(getResourceAsText("/day6").split("\n"))}")
    println("day 6.2 : ${solveDay6p2(getResourceAsText("/day6").split("\n"))}")
    println("day 7.1 : ${solveDay7p1(getResourceAsText("/day7").split("\n"))}")
    println("day 7.2 : ${solveDay7p2(getResourceAsText("/day7").split("\n"))}")
    println("day 8.1 : ${solveDay8p1(getResourceAsText("/day8").split("\n"))}")
    println("day 8.2 : ${solveDay8p2(getResourceAsText("/day8").split("\n"))}")
    println("day 9.1 : ${solveDay9p1(getResourceAsText("/day9").split("\n"))}")
    println("day 9.2 : ${solveDay9p2(getResourceAsText("/day9").split("\n"))}")
    println("day 10.1 : ${solveDay10p1(getResourceAsText("/day10").split("\n"))}")
    println("day 10.2 : ${solveDay10p2(getResourceAsText("/day10").split("\n"))}")
    println("day 11.1 : ${solveDay11p1(getResourceAsText("/day11").split("\n"))}")
    println("day 11.2 : ${solveDay11p2(getResourceAsText("/day11").split("\n"))}")
    println("day 12.1 : ${solveDay12p1(getResourceAsText("/day12").split("\n"))}")
    println("day 12.2 : ${solveDay12p2(getResourceAsText("/day12").split("\n"))}")
}
