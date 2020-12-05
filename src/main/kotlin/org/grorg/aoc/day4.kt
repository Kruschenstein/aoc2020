package org.grorg.aoc

fun solveDay4p1(input: List<String>): Int {
    return getPasswordsWithRequiredLine(input)
        .count()
}

fun inlinePassportEntry(acc: MutableList<String>, line: String): MutableList<String> {
    if (line.isEmpty()) {
        acc.add("")
    } else {
        acc[acc.lastIndex] = "${acc.last()} $line"
    }
    return acc
}

fun getPasswordsWithRequiredLine(input: List<String>): List<String> {
    val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    val passports = input.fold(mutableListOf(""), ::inlinePassportEntry)

    return passports
        .filter { passport ->
            requiredFields
                .asSequence()
                .map { field -> "$field:" }
                .all { passport.contains(it) }
        }
}

enum class PassportLineField(val fieldName: String) {
    BIRTH_YEAR("byr"),
    ISSUE_YEAR("iyr"),
    EXPIRATION_YEAR("eyr"),
    HEIGHT("hgt"),
    HAIR_COLOR("hcl"),
    EYE_COLOR("ecl"),
    PASSPORT_ID("pid")
}

data class PassportEntry(
    val birthYear: Int, val issueYear: Int, val expirationYear: Int,
    val height: Int, val heightUnit: String, val hairColor: String, val eyeColor: String,
    val passportId: String
)

val HEIGHT_PATTERN = "(\\d+)(.+)".toRegex()
val FOUR_DIGIT_NUMBER = "\\d{4}".toRegex()

fun convertFourDigitNumber(str: String?): Int =
    str?.let { if (FOUR_DIGIT_NUMBER.matches(it)) it.toInt() else null } ?: 0

fun parsePassportLine(passportLine: String): PassportEntry {
    val parsedLine = parsePassportLineIntoMap(passportLine)

    val heightField = parsedLine[PassportLineField.HEIGHT.fieldName] ?: "0x"
    val (height, unit) = HEIGHT_PATTERN.find(heightField)!!.destructured

    return PassportEntry(
        convertFourDigitNumber(parsedLine[PassportLineField.BIRTH_YEAR.fieldName]),
        convertFourDigitNumber(parsedLine[PassportLineField.ISSUE_YEAR.fieldName]),
        convertFourDigitNumber(parsedLine[PassportLineField.EXPIRATION_YEAR.fieldName]),
        height.toInt(),
        unit,
        parsedLine[PassportLineField.HAIR_COLOR.fieldName] ?: "",
        parsedLine[PassportLineField.EYE_COLOR.fieldName] ?: "",
        parsedLine[PassportLineField.PASSPORT_ID.fieldName] ?: ""
    )
}

private fun parsePassportLineIntoMap(passportLine: String) = passportLine
    .trim()
    .split(" ")
    .map { splitToPair(it) }
    .toMap()

private fun splitToPair(it: String): Pair<String, String> {
    val field = it.split(":")
    return Pair(field[0], field[1])
}

val COLOR_PATTERN = "#[a-f0-9]{6}".toRegex()
val EYE_COLORS = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
val PASSPORT_ID_PATTERN = "\\d{9}".toRegex()

fun isValidEntry(entry: PassportEntry): Boolean {
    return entry.birthYear in 1920..2002 &&
        entry.issueYear in 2010..2020 &&
        entry.expirationYear in 2020..2030 &&
        ((entry.heightUnit == "cm" && entry.height in 150..193) ||
                (entry.heightUnit == "in" && entry.height in 59..76)) &&
        COLOR_PATTERN.matches(entry.hairColor) &&
        EYE_COLORS.contains(entry.eyeColor) &&
        PASSPORT_ID_PATTERN.matches(entry.passportId)
}

fun solveDay4p2(input: List<String>): Int {
    return getPasswordsWithRequiredLine(input)
        .asSequence()
        .map(::parsePassportLine)
        .filter(::isValidEntry)
        .count()
}
