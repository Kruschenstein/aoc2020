package org.grorg.aoc

data class Rule(val name: String, val firstRange: IntRange, val secondRange: IntRange){
    fun matchAnyRange(value: Int) = value in firstRange || value in secondRange

    fun notMatchAnyRange(value: Int) = !matchAnyRange(value)
}

val RULE_REGEX = "(\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex()

fun getRules(input: List<String>) = input.asSequence()
        .takeWhile { it != "" }
        .map {
            val (name, ranges) = it.split(':')
            val (firstStart, firstEnd, secondStart, secondEnd) = RULE_REGEX.find(ranges.trim())!!.destructured
            Rule(name, firstStart.toInt()..firstEnd.toInt(), secondStart.toInt()..secondEnd.toInt())
        }

fun solveDay16p1(input: List<String>): Int {
    val rules = getRules(input)

    return input.asSequence()
        .dropWhile { it != "nearby tickets:" }
        .drop(1)
        .flatMap { it.split(',').map(String::toInt) }
        .filter { rules.none { rule -> rule.matchAnyRange(it) } }
        .sum()
}

fun solveDay16p2(input: List<String>): Long {
    val rules = getRules(input)

    val ticket = input
        .dropWhile { it != "your ticket:" }
        .drop(1)
        .take(1)
        .flatMap { it.split(',').map(String::toInt) }

    val keptTicket = input.asSequence()
        .dropWhile { it != "nearby tickets:" }
        .drop(1)
        .map { it.split(',').map(String::toInt) }
        .filter { t -> t.all { rules.any { rule -> rule.matchAnyRange(it) } } }
        .toList()

    val possibleIndexForRule = rules.zip(generateSequence(ticket.indices.toMutableList()) { it.toMutableList() }).toMap()

    excludeRules(ticket, possibleIndexForRule)
    keptTicket.forEach {
        excludeRules(it, possibleIndexForRule)
    }

    return possibleIndexForRule
        .filter { (key, _) -> key.name.startsWith("departure") }
        .map { (_, value) -> ticket[value[0]].toLong() }
        .reduceRight(Long::times)
}

private fun excludeRules(
    it: List<Int>,
    possibleIndexForRule: Map<Rule, MutableList<Int>>
) {
    for (i in it.indices) {
        val availableRules = possibleIndexForRule.filter { (_, value) -> i in value }.keys
        for (rule in availableRules) {
            if (rule.notMatchAnyRange(it[i])) {
                possibleIndexForRule[rule]!!.remove(i)

                if (possibleIndexForRule[rule]!!.size == 1) {
                    sanitize(possibleIndexForRule, rule)
                }
            }
        }
    }
}

private fun sanitize(
    possibleIndexForRule: Map<Rule, MutableList<Int>>,
    rule: Rule
) {
    val foundPosition = possibleIndexForRule[rule]!![0]
    possibleIndexForRule
        .filter { (_, value) -> value.size > 1 }
        .forEach { (key, value) ->
            value.remove(foundPosition)
            if (value.size == 1) {
                sanitize(possibleIndexForRule, key)
            }
        }
}
