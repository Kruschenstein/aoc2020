package org.grorg.aoc

fun solveDay22p1(input: List<String>): Long {
    val player1Deck = readFirstDeck(input)
    val player2Deck = readSecondDeck(input)

    while (player1Deck.isNotEmpty() && player2Deck.isNotEmpty()) {
        val player1Card = player1Deck.removeFirst()
        val player2Card = player2Deck.removeFirst()

        if (player1Card > player2Card) {
            player1Deck.add(player1Card)
            player1Deck.add(player2Card)
        } else {
            player2Deck.add(player2Card)
            player2Deck.add(player1Card)
        }
    }

    return computeFinalScore(
        if (player1Deck.isNotEmpty()) {
            player1Deck
        } else {
            player2Deck
        }
    )
}

private fun computeFinalScore(deck: List<Long>) = deck.reversed().mapIndexed { i, value -> (i + 1) * value }.sum()

private fun readSecondDeck(input: List<String>) = input.asSequence()
    .dropWhile { it != "Player 2:" }
    .drop(1)
    .map { it.toLong() }
    .toMutableList()

private fun readFirstDeck(input: List<String>) = input.asSequence()
    .drop(1)
    .takeWhile { it.isNotEmpty() }
    .map { it.toLong() }
    .toMutableList()

fun solveDay22p2(input: List<String>): Long {
    val player1Deck = readFirstDeck(input)
    val player2Deck = readSecondDeck(input)
    return computeFinalScore(game(player1Deck, player2Deck).deck)
}

data class GameResult(val player: Int, val deck: List<Long>)

private fun game(firstDeck: List<Long>, secondDeck: List<Long>): GameResult {
    val previousFirstDecks = mutableSetOf(firstDeck)
    val previousSecondDecks = mutableSetOf(secondDeck)

    var currentFirstDeck = firstDeck
    var currentSecondDeck = secondDeck
    while (currentFirstDeck.isNotEmpty() && currentSecondDeck.isNotEmpty()) {
        val firstPlayerCard = currentFirstDeck.first()
        val secondPlayerCard = currentSecondDeck.first()
        currentFirstDeck = currentFirstDeck.subList(1, currentFirstDeck.size)
        currentSecondDeck = currentSecondDeck.subList(1, currentSecondDeck.size)

        if (previousFirstDecks.contains(currentFirstDeck) || previousSecondDecks.contains(currentSecondDeck)) {
            return GameResult(1, currentFirstDeck)
        }

        if (firstPlayerCard <= currentFirstDeck.size.toLong() && secondPlayerCard <= currentSecondDeck.size.toLong()) {
            val result = game(
                currentFirstDeck.subList(0, firstPlayerCard.toInt()),
                currentSecondDeck.subList(0, secondPlayerCard.toInt())
            )
            if (result.player == 1) {
                currentFirstDeck = currentFirstDeck + listOf(firstPlayerCard, secondPlayerCard)
            } else {
                currentSecondDeck = currentSecondDeck + listOf(secondPlayerCard, firstPlayerCard)
            }
        } else if (firstPlayerCard > secondPlayerCard) {
            currentFirstDeck = currentFirstDeck + listOf(firstPlayerCard, secondPlayerCard)
        } else {
            currentSecondDeck = currentSecondDeck + listOf(secondPlayerCard, firstPlayerCard)
        }

        previousFirstDecks.add(currentFirstDeck)
        previousSecondDecks.add(currentSecondDeck)
    }

    return if (currentFirstDeck.isNotEmpty()) {
        GameResult(1, currentFirstDeck)
    } else {
        GameResult(2, currentSecondDeck)
    }
}