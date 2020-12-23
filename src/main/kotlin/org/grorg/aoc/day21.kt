package org.grorg.aoc

data class IngredientList(val ingredients: Set<String>, val allergens: Set<String>)

fun solveDay21p1(input: List<String>): Int {
    val ingredients = input
        .map {
            val (ingredients, allergens) = it.split("(contains ")
            IngredientList(
                ingredients.trim().split(" ").toSet(),
                allergens.replace(")", "").split(", ").toSet()
            )
        }

    val potentialAllergens = ingredients
        .flatMap { ingredientList ->
            ingredientList.allergens.map { Pair(it, ingredientList.ingredients) }
        }
        .groupingBy { it.first }
        .aggregate { _, acc: Set<String>?, e, _ ->
            acc?.intersect(e.second) ?: e.second
        }

    val allergensSet = potentialAllergens
        .map { it.value }
        .reduceRight { e, acc -> acc.union(e) }

    return ingredients
        .flatMap { it.ingredients }
        .filter { !allergensSet.contains(it) }
        .count()
}

fun solveDay21p2(input: List<String>): String {
    val ingredients = input
        .map {
            val (ingredients, allergens) = it.split("(contains ")
            IngredientList(
                ingredients.trim().split(" ").toSet(),
                allergens.replace(")", "").split(", ").toSet()
            )
        }

    val potentialAllergens = ingredients
        .flatMap { ingredientList ->
            ingredientList.allergens.map { Pair(it, ingredientList.ingredients) }
        }
        .groupingBy { it.first }
        .aggregate { _, acc: Set<String>?, e, _ ->
            (acc?.intersect(e.second) ?: e.second).toMutableSet()
        }

    val visitedOneElements = mutableSetOf<String>()

    while (potentialAllergens.any { it.value.size > 1 }) {
        for ((key, value) in potentialAllergens.filter { it.value.size == 1 }) {
            visitedOneElements.add(key)
            for ((_, allergens) in potentialAllergens.filter { it.value.size > 1 }) {
                allergens.remove(value.first())
            }
        }
    }

    return potentialAllergens.keys.sorted().map { potentialAllergens[it]!!.first() }.joinToString(",")
}