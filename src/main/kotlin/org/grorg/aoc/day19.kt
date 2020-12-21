package org.grorg.aoc

enum class PartialOk {
    KO,
    OK,
    PARTIAL,
}

data class SatelliteRulesStep(val partialOk: PartialOk, val read: Int)

sealed class SatelliteRules {
    abstract fun match(message: String, rules: Map<Int, SatelliteRules>): SatelliteRulesStep

    data class Equals(val letter: Char) : SatelliteRules() {
        override fun match(message: String, rules: Map<Int, SatelliteRules>): SatelliteRulesStep {
            if (message.isEmpty()) {
                return SatelliteRulesStep(PartialOk.KO, 0)
            }
            if (message.first() == letter) {
                if (message.length == 1) {
                    return SatelliteRulesStep(PartialOk.OK, 1)
                }
                return SatelliteRulesStep(PartialOk.PARTIAL, 1)
            }
            return SatelliteRulesStep(PartialOk.KO, 1)
        }
    }

    data class And(val ref: List<Int>) : SatelliteRules() {
        override fun match(message: String, rules: Map<Int, SatelliteRules>): SatelliteRulesStep {
            var read = 0
            for (i in ref.indices) {
                val rule = rules[ref[i]]!!
                val subMessage = message.substring(read)
                val (match, subRead) = rule.match(subMessage, rules)
                if (match == PartialOk.KO) {
                    return SatelliteRulesStep(match, read)
                } else if (match == PartialOk.OK) {
                    if (i == ref.size - 1) {
                        return SatelliteRulesStep(match, read)
                    } else {
                        PartialOk.KO
                    }
                }
                read += subRead
            }
            return SatelliteRulesStep(PartialOk.PARTIAL, read)
        }
    }

    data class Or(val left: List<Int>, val right: List<Int>) : SatelliteRules() {
        override fun match(message: String, rules: Map<Int, SatelliteRules>): SatelliteRulesStep {
            val (leftMatch, read) = And(left).match(message, rules)
            if (leftMatch == PartialOk.OK || leftMatch == PartialOk.PARTIAL) {
                return SatelliteRulesStep(leftMatch, read)
            }

            val rightResult = And(right).match(message, rules)
            val (rightMatch, rightRead) = rightResult
            if (rightMatch == PartialOk.KO) {
                return SatelliteRulesStep(rightMatch, rightRead)
            }
            return rightResult

        }
    }
}

fun toRule(rule: String): SatelliteRules {
    return when {
        rule[0] == '"' -> {
            SatelliteRules.Equals(rule[1])
        }
        rule.contains("|") -> {
            val (left, right) = rule.split(" | ")
            SatelliteRules.Or(left.split(" ").map(String::toInt), right.split(" ").map(String::toInt))
        }
        else -> {
            SatelliteRules.And(rule.split(" ").map(String::toInt))
        }
    }
}

fun solveDay19p1(input: List<String>): Int {
    val inputSequence = input
        .asSequence()

    val rules = parseRules(inputSequence)

    return inputSequence
        .dropWhile { it != "" }
        .drop(1)
        .map { rules[0]!!.match(it, rules) }
        .filter { it.partialOk == PartialOk.OK }
        .count()
}

fun solveDay19p2(input: List<String>): Int {
    val inputSequence = input
        .asSequence()

    val rules = parseRules(inputSequence)

    rules[8] = SatelliteRules.Or(listOf(42), listOf(42, 8))
    rules[11] = SatelliteRules.Or(listOf(42, 31), listOf(42, 11, 31))

    return inputSequence
        .dropWhile { it != "" }
        .drop(1)
        .map { rules[0]!!.match(it, rules) }
        .filter { it.partialOk == PartialOk.OK }
        .count()
}

private fun parseRules(inputSequence: Sequence<String>) = inputSequence
    .takeWhile { it != "" }
    .map {
        val (number, rule) = it.split(": ")
        Pair(number.toInt(), toRule(rule))
    }
    .toMap()
    .toMutableMap()