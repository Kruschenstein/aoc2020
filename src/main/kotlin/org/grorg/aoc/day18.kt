package org.grorg.aoc

import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import kotlin.math.exp

sealed class Calculus {
    abstract fun compute(): Long

    data class Number(private val value: Int) : Calculus() {
        override fun compute(): Long = value.toLong()
    }
    data class Multiplication(val left: Calculus, val right: Calculus) : Calculus() {
        override fun compute(): Long = left.compute() * right.compute()
    }

    data class Addition(val left: Calculus, val right: Calculus) : Calculus() {
        override fun compute(): Long = left.compute() + right.compute()
    }
}

fun buildOperator(operation: Char, leftOperand: Calculus, rightOperand: Calculus): Calculus {
    return when (operation) {
        '+' -> Calculus.Addition(leftOperand, rightOperand)
        '*' -> Calculus.Multiplication(leftOperand, rightOperand)
        else -> throw IllegalArgumentException("No operator named $operation")
    }
}

typealias CalculusBuilder = Pair<Calculus, String>

private fun buildOperation(
    line: String,
    actual: Char,
    left: Calculus
): CalculusBuilder {
    val next = line.substring(1)
    return when (val right = line.first()) {
        in '0'..'9' -> {
            val rightNumber = Calculus.Number(charToInt(right))
            buildCalculus(buildOperator(actual, left, rightNumber), next)
        }
        '(' -> {
            val (subCalculus, nextOperation) = buildCalculus(next)
            buildCalculus(buildOperator(actual, subCalculus, left), nextOperation)
        }
        else -> throw IllegalArgumentException("Invalid calculus, unexpected $right")
    }
}

fun buildCalculus(left: Calculus, line: String): CalculusBuilder {
    if (line.isEmpty()) {
        return CalculusBuilder(left, line)
    }
    val actual = line.first()
    val next = line.substring(1)

    return when (actual) {
        '+', '*' -> buildOperation(next, actual, left)
        '(' -> {
            val (subCalculus, nextOperation) = buildCalculus(left, next)
            buildCalculus(subCalculus, nextOperation)
        }
        else -> CalculusBuilder(left, next)
    }
}

fun buildCalculus(line: String): CalculusBuilder {
    val actual = line.first()
    val next = line.substring(1)

    if (actual in '0'..'9') {
        val left = Calculus.Number(charToInt(actual))
        return when {
            next.isEmpty() -> CalculusBuilder(left, "")
            else -> buildCalculus(left, next)
        }
    } else if (actual == '(') {
        val (subCalculus, nextOperation) = buildCalculus(next)
        return buildCalculus(subCalculus, nextOperation)
    }
    throw IllegalArgumentException("Unknown $actual")
}

fun charToInt(char: Char): Int = char.toInt() - '0'.toInt()

fun solveDay18p1(input: List<String>): Long {
    return input
        .map { it.replace(" ", "") }
        .map { buildCalculus(it).first }
        .map { it.compute() }
        .sum()
}

// PART 2
fun charToLong(c: Char) = c.toLong() - '0'.toLong()

fun transformToInversePolishNotation(formula: String): String {
    val stack = mutableListOf<Char>()
    val result = StringBuilder()

    for (element in formula) {
        if (element in '0'..'9') {
            result.append(element)
        } else {
            val last = stack.lastOrNull()
            if (element != '(' && element == last) {
                result.append(element)
            } else if (element == '*' && last == '+') {
                stack.removeLast()
                result.append(last)
                stack.add(element)
            } else if (element == ')') {
                do {
                    val l = stack.removeLast()
                    if (l != '(') {
                        result.append(l)
                    }
                } while (l != '(')
            } else {
                stack.add(element)
            }
        }
    }
    for (element in stack.reversed()) {
        result.append(element)
    }
    return result.toString()
}

fun compute(operation: String): Long {
    val stack = mutableListOf<Long>()
    for (element in operation) {
        if (element in '0'..'9') {
            stack.add(charToLong(element))
        } else {
            val left = stack.removeLast()
            val right = stack.removeLast()
            if (element == '+') {
                stack.add(left + right)
            } else {
                stack.add(left * right)
            }
        }
    }
    return stack.last()
}

fun solveDay18p2(input: List<String>): Long {
    return input
        .asSequence()
        .map { it.replace(" ", "") }
        .map { transformToInversePolishNotation(it) }
        .map { compute(it)}
        .sum()
}