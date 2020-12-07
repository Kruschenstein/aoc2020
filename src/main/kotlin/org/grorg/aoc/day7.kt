package org.grorg.aoc

class Bag(val colorName: String) {
    private val containedBy = mutableMapOf<Bag, Int>()
    private val contains = mutableMapOf<Bag, Int>()

    fun isContainedBy(bag: Bag, nb: Int) {
        containedBy[bag] = nb
        bag.contains[this] = nb
    }

    fun getContainedBy(): Map<Bag, Int> = containedBy.toMap()

    fun getContained(): Map<Bag, Int> = contains.toMap()
}

fun countChild(foundBag: MutableSet<Bag>, bag: Bag): MutableSet<Bag> {
    foundBag.add(bag)
    if (bag.getContainedBy().isEmpty()) {
        return foundBag
    }
    return bag.getContainedBy().keys
        .map { countChild(foundBag, it) }
        .fold(foundBag) { acc, i ->
            acc.addAll(i)
            acc
        }
}

fun countChild(bags: Bag): Int {
    return countChild(mutableSetOf(), bags).size - 1
}

fun solveDay7p1(input: List<String>): Int {
    val bags = buildBagsTree(input)

    return bags["shiny gold"]?.let(::countChild) ?: throw NotFoundSolutionException()
}

private fun buildBagsTree(input: List<String>): Map<String, Bag> {
    val bags = input
        .map {
            val colorName = it.split(" bags contain")[0]
            Pair(colorName, Bag(colorName))
        }
        .toMap()
    input
        .forEach { entry ->
            val (currentBag, otherParts) = entry.split("bags contain")
            otherParts
                .split(",")
                .map(::bagCapacity)
                .forEach { (nb, color) ->
                    bags[currentBag.trim()]?.let { bag -> bags[color]?.isContainedBy(bag, nb) }
                }
        }
    return bags
}

private fun bagCapacity(it: String): Pair<Int, String> {
    val (nb, colorP1, colorP2) = it.trim().split(" ")
    return when (nb) {
        "no" -> Pair(0, "")
        else -> Pair(nb.toInt(), "$colorP1 $colorP2")
    }
}

fun solveDay7p2(input: List<String>): Int {
    val bags = buildBagsTree(input)
    return bags["shiny gold"]?.let(::countSubBags)?.let { it - 1 } ?: throw NotFoundSolutionException()
}

fun countSubBags(bag: Bag): Int {
    if (bag.getContained().isEmpty()) {
        return 1
    }
    return 1 + bag.getContained().map { (parentBag, nb) -> nb * countSubBags(parentBag) }.sum()
}