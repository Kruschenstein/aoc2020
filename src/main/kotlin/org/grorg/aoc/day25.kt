package org.grorg.aoc

private const val SUBJECT_NUMBER = 7L
private const val DIVIDER = 20201227L

private fun findLoopKey(publicKey: Long): Long {
    var loopKey = 1L
    var loopIntermediateResult = SUBJECT_NUMBER
    while (loopIntermediateResult != publicKey) {
        loopIntermediateResult = loopIntermediateResult * SUBJECT_NUMBER % DIVIDER
        ++loopKey
    }
    return loopKey
}

private fun findPrivateKey(publicKey: Long, loopNumber: Long): Long {
    var res = 1L
    for (i in 0 until loopNumber) {
        res = res * publicKey % DIVIDER
    }
    return res
}

fun solveDay25p1(input: List<String>): Long {
    val (cardPublicKey, doorPublicKey) = input.map(String::toLong)

    val doorLoopKey = findLoopKey(doorPublicKey)
    return findPrivateKey(cardPublicKey, doorLoopKey)
}