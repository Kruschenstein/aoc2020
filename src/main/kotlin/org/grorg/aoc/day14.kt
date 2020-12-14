package org.grorg.aoc

val MEM_REGEX = "mem\\[(\\d+)] = (\\d+)".toRegex()

fun solveDay14p1(input: List<String>): Long {
    val memory = mutableMapOf<Int, Long>()
    var currentMask = ""
    for (instruction in input) {
        if (instruction.startsWith("mask = ")) {
            currentMask = instruction.substring("mask = ".length)
        } else {
            val (position, value) = MEM_REGEX.find(instruction)!!.destructured
            val builder = StringBuilder(Integer.toBinaryString(value.toInt()).padStart(36, '0'))
            for (i in currentMask.indices) {
                if (currentMask[i] != 'X') {
                    builder.setCharAt(i, currentMask[i])
                }
            }
            memory[position.toInt()] = builder.toString().toLong(2)
        }
    }
    return memory.values.sum()
}

fun solveDay14p2(input: List<String>): Long {
    var currentMask = ""
    val memory = mutableMapOf<String, Long>()

    for (instruction in input) {
        if (instruction.startsWith("mask = ")) {
            currentMask = instruction.substring("mask = ".length)
        } else {
            val (position, value) = MEM_REGEX.find(instruction)!!.destructured
            val builder = StringBuilder(Integer.toBinaryString(position.toInt()).padStart(36, '0'))
            for (i in currentMask.indices) {
                if (currentMask[i] != '0') {
                    builder.setCharAt(i, currentMask[i])
                }
            }

            val addresses = builder.toString().reversed().fold(listOf("")) { acc, c ->
                if (c == 'X') {
                    acc.map { "1$it" } + acc.map { "0$it" }
                } else {
                    acc.map { "$c$it" }
                }
            }
            for (mask in addresses) {
                memory[mask] = value.toLong()
            }
        }
    }

    return memory.values.sum()
}