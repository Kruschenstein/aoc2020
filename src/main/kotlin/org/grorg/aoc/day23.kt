package org.grorg.aoc

fun solveDay23p1(input: List<String>): Int {
    val ring = input.first().map { it.toInt() - '0'.toInt() }.toMutableList()

    fun getCurrent(index: Int): Int = ring[index % ring.size]
    fun getIndex(index: Int): Int = index % ring.size
    fun getNextIndex(current: Int): Int = getIndex(ring.indexOfFirst { it == current } + 1)
    fun remove3Next(current: Int): List<Int> = listOf(
        ring.removeAt(getNextIndex(current)),
        ring.removeAt(getNextIndex(current)),
        ring.removeAt(getNextIndex(current)),
    )

    var current = getCurrent(0)
    for (i in 1..100) {
        val next = remove3Next(current)
        for (m in 1..9) {
            if (current - m <= 0) {
                val maxIndex = ring.indexOf(ring.maxOrNull()!!)
                val subList = ring.slice(getIndex(maxIndex + 1) until ring.size)
                ring.removeAll(subList)
                ring.addAll(next)
                ring.addAll(subList)
                break
            }
            val index = ring.indexOfFirst { it == current - m }
            if (index != -1) {
                val subList = ring.slice(getIndex(index + 1) until ring.size)
                ring.removeAll(subList)
                ring.addAll(next)
                ring.addAll(subList)
                break
            }
        }
        current = ring[getIndex(ring.indexOfFirst { it == current } + 1)]
    }

    val indexOf1 = ring.indexOf(1)
    val result = ring.slice(indexOf1 + 1 until ring.size) + ring.slice(0 until indexOf1)
    return result.joinToString("").toInt()
}

class Ring(var current: Node, val index: Array<Node?> = Array(1_000_001) { null }) {
    init {
        index[current.value.toInt()] = current
    }
    fun add(value: Long) {
        val node = Node(value, next = current, previous = current.previous ?: current)
        index[value.toInt()] = node
        current.previous?.next = node
        current.previous = node
        if (current.next == null) {
            current.next = node
        }
    }

    fun addNext(node: Node) {
        val next = current.next
        var last: Node? = node
        while (last?.next != null) {
            last = last.next
        }
        current.next = node
        last?.next = next
    }

    fun removeNext(numberOfRemovedElement: Int): Node {
        val first = current.next
        var next = current.next
        for (i in 1 until numberOfRemovedElement) {
            next = next?.next
        }
        val last = next
        current.next = last?.next
        last?.next = null
        return first!!
    }
}

class Node(val value: Long, var previous: Node? = null, var next: Node? = null) {

}

fun solveDay23p2(input: List<String>): Long {
    val line = input.first()
    val ring = Ring(Node(charToLong(line.first())))
    line.drop(1).forEach { ring.add(charToLong(it)) }

    for (i in 10L..1_000_000L) {
        ring.add(i)
    }

    val removedValues = LongArray(3)

    for (i in 1..10_000_000) {
        val removed = ring.removeNext(3)
        removedValues[0] = removed.value
        removedValues[1] = removed.next?.value!!
        removedValues[2] = removed.next?.next?.value!!
        val current = ring.current.value
        for (m in 1..1_000_000) {
            if (current - m <= 0) {
                if (!removedValues.contains(ring.index[1_000_000]!!.value)) {
                    ring.current = ring.index[1_000_000]!!
                } else if (!removedValues.contains(ring.index[999_999]!!.value)) {
                    ring.current = ring.index[999_999]!!
                } else if (!removedValues.contains(ring.index[999_998]!!.value)) {
                    ring.current = ring.index[999_998]!!
                } else {
                    ring.current = ring.index[999_997]!!
                }
                ring.addNext(removed)
                break
            } else if (!removedValues.contains(current - m)) {
                ring.current = ring.index[(current - m).toInt()]!!
                ring.addNext(removed)
                break
            }
        }
        ring.current = ring.index[current.toInt()]?.next!!
    }

    val one = ring.index[1]!!
    return one.next?.value!! * one.next?.next?.value!!
}