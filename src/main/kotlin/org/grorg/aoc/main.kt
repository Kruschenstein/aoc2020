package org.grorg.aoc

fun getResourceAsText(path: String): List<String> {
    return object {}.javaClass.getResource(path).readText().trim().split("\n")
}

inline fun <T> printlnResultAndDuration(dayPart: String, block: () -> T) {
    val start = System.currentTimeMillis()
    println("$dayPart: ${block()} in ${System.currentTimeMillis() - start}ms")
}

fun main() {
    printlnResultAndDuration("day 1.1") { solveDay1p1(getResourceAsText("/day1")) }
    printlnResultAndDuration("day 1.2") { solveDay1p2(getResourceAsText("/day1")) }
    printlnResultAndDuration("day 2.1") { solveDay2p1(getResourceAsText("/day2")) }
    printlnResultAndDuration("day 2.2") { solveDay2p2(getResourceAsText("/day2")) }
    printlnResultAndDuration("day 3.1") { solveDay3p1(getResourceAsText("/day3")) }
    printlnResultAndDuration("day 3.2") { solveDay3p2(getResourceAsText("/day3")) }
    printlnResultAndDuration("day 4.1") { solveDay4p1(getResourceAsText("/day4")) }
    printlnResultAndDuration("day 4.2") { solveDay4p2(getResourceAsText("/day4")) }
    printlnResultAndDuration("day 5.1") { solveDay5p1(getResourceAsText("/day5")) }
    printlnResultAndDuration("day 5.2") { solveDay5p2(getResourceAsText("/day5")) }
    printlnResultAndDuration("day 6.1") { solveDay6p1(getResourceAsText("/day6")) }
    printlnResultAndDuration("day 6.2") { solveDay6p2(getResourceAsText("/day6")) }
    printlnResultAndDuration("day 7.1") { solveDay7p1(getResourceAsText("/day7")) }
    printlnResultAndDuration("day 7.2") { solveDay7p2(getResourceAsText("/day7")) }
    printlnResultAndDuration("day 8.1") { solveDay8p1(getResourceAsText("/day8")) }
    printlnResultAndDuration("day 8.2") { solveDay8p2(getResourceAsText("/day8")) }
    printlnResultAndDuration("day 9.1") { solveDay9p1(getResourceAsText("/day9")) }
    printlnResultAndDuration("day 9.2") { solveDay9p2(getResourceAsText("/day9")) }
    printlnResultAndDuration("day 10.1") { solveDay10p1(getResourceAsText("/day10")) }
    printlnResultAndDuration("day 10.2") { solveDay10p2(getResourceAsText("/day10")) }
    printlnResultAndDuration("day 11.1") { solveDay11p1(getResourceAsText("/day11")) }
    printlnResultAndDuration("day 11.2") { solveDay11p2(getResourceAsText("/day11")) }
    printlnResultAndDuration("day 12.1") { solveDay12p1(getResourceAsText("/day12")) }
    printlnResultAndDuration("day 12.2") { solveDay12p2(getResourceAsText("/day12")) }
    printlnResultAndDuration("day 13.1") { solveDay13p1(getResourceAsText("/day13")) }
    printlnResultAndDuration("day 13.2") { solveDay13p2(getResourceAsText("/day13")) }
    printlnResultAndDuration("day 14.1") { solveDay14p1(getResourceAsText("/day14")) }
    printlnResultAndDuration("day 14.2") { solveDay14p2(getResourceAsText("/day14")) }
    printlnResultAndDuration("day 15.1") { solveDay15p1(getResourceAsText("/day15")) }
    printlnResultAndDuration("day 15.2") { solveDay15p2(getResourceAsText("/day15")) }
    printlnResultAndDuration("day 16.1") { solveDay16p1(getResourceAsText("/day16")) }
    printlnResultAndDuration("day 16.2") { solveDay16p2(getResourceAsText("/day16")) }
    printlnResultAndDuration("day 17.1") { solveDay17p1(getResourceAsText("/day17")) }
    printlnResultAndDuration("day 17.2") { solveDay17p2(getResourceAsText("/day17")) }
    printlnResultAndDuration("day 18.1") { solveDay18p1(getResourceAsText("/day18")) }
    printlnResultAndDuration("day 18.2") { solveDay18p2(getResourceAsText("/day18")) }
    printlnResultAndDuration("day 19.1") { solveDay19p1(getResourceAsText("/day19")) }
    printlnResultAndDuration("day 19.1") { solveDay19p2(getResourceAsText("/day19")) }
    printlnResultAndDuration("day 20.1") { solveDay20p1(getResourceAsText("/day20")) }
    printlnResultAndDuration("day 20.2") { solveDay20p2(getResourceAsText("/day20")) }
    printlnResultAndDuration("day 21.1") { solveDay21p1(getResourceAsText("/day21")) }
    printlnResultAndDuration("day 21.2") { solveDay21p2(getResourceAsText("/day21")) }
    printlnResultAndDuration("day 22.1") { solveDay22p1(getResourceAsText("/day22")) }
    printlnResultAndDuration("day 22.2") { solveDay22p2(getResourceAsText("/day22")) }
    printlnResultAndDuration("day 23.1") { solveDay23p1(getResourceAsText("/day23")) }
    printlnResultAndDuration("day 23.2") { solveDay23p2(getResourceAsText("/day23")) }
    printlnResultAndDuration("day 24.1") { solveDay24p1(getResourceAsText("/day24")) }
    printlnResultAndDuration("day 24.2") { solveDay24p2(getResourceAsText("/day24")) }
}
