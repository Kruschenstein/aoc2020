package org.grorg.aoc

sealed class Instruction {
    abstract fun execute(programState: ProgramState): ProgramState;

    class Jmp(val offset: Int) : Instruction() {
        override fun execute(programState: ProgramState): ProgramState =
            ProgramState(programState.position + offset, programState.acc)
    }

    class Acc(private val value: Int) : Instruction() {
        override fun execute(programState: ProgramState): ProgramState =
            ProgramState(programState.position + 1, programState.acc + value)
    }

    class Nop(val value: Int) : Instruction() {
        override fun execute(programState: ProgramState): ProgramState =
            ProgramState(programState.position + 1, programState.acc)
    }
}

class ProgramState(val position: Int, val acc: Int)

/*
 Maybe we could enhance this with a nullable ProgramState and instructions.getOrNull(..) ?
 */
fun execute(instructions: List<Instruction>, state: ProgramState): ProgramState {
    if (state.position < instructions.size) {
        return instructions[state.position].execute(state)
    }
    return state
}

class NotExistingInstruction(instruction: String) : Exception("The instruction \"$instruction\" does not exist")

fun createInstructions(input: List<String>) = input
    .map {
        val (instruction, value) = it.split(" ")
        when (instruction) {
            "jmp" -> Instruction.Jmp(value.toInt())
            "acc" -> Instruction.Acc(value.toInt())
            "nop" -> Instruction.Nop(value.toInt())
            else -> throw NotExistingInstruction(it)
        }
    }

fun runProgram(instructions: List<Instruction>, state: ProgramState, visitedPosition: MutableSet<Int>): ProgramState {
    if (visitedPosition.contains(state.position)) {
        return state
    }
    visitedPosition.add(state.position)
    return runProgram(instructions, execute(instructions, state), visitedPosition)
}

fun runProgram(instructions: List<Instruction>): ProgramState {
    return runProgram(instructions, ProgramState(0, 0), mutableSetOf())
}

fun solveDay8p1(input: List<String>): Int {
    val instructions = createInstructions(input)
    return runProgram(instructions).acc
}

fun swapJumpAndNop(instruction: Instruction): Instruction {
    return when (instruction) {
        is Instruction.Jmp -> Instruction.Nop(instruction.offset)
        is Instruction.Acc -> instruction
        is Instruction.Nop -> Instruction.Jmp(instruction.value)
    }
}

fun runProgramWithSwapping(
    instructions: List<Instruction>,
    state: ProgramState,
    visitedPosition: MutableSet<Int>
): ProgramState? {
    if (state.position == instructions.size) {
        return state
    }
    val alternativeState =
        runProgram(instructions, swapJumpAndNop(instructions[state.position]).execute(state), mutableSetOf())
    if (alternativeState.position == instructions.size) {
        return alternativeState
    }

    if (visitedPosition.contains(state.position)) {
        return null
    }

    return runProgramWithSwapping(instructions, execute(instructions, state), visitedPosition)
}

fun runProgramWithSwapping(instructions: List<Instruction>): ProgramState? {
    return runProgramWithSwapping(instructions, ProgramState(0, 0), mutableSetOf())
}

fun solveDay8p2(input: List<String>): Int {
    val instructions = createInstructions(input)
    return runProgramWithSwapping(instructions)?.acc ?: throw NotFoundSolutionException()
}