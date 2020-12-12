package org.grorg.aoc

import java.lang.IllegalArgumentException
import kotlin.math.abs

enum class Direction {
    EAST,
    NORTH,
    WEST,
    SOUTH,
}

val DIRECTIONS = Direction.values()

data class Position(val x: Int, val y: Int)

data class BoatState(val direction: Direction, val position: Position)

sealed class MoveInstruction {
    abstract fun move(state: BoatState): BoatState

    class North(private val distance: Int) : MoveInstruction() {
        override fun move(state: BoatState): BoatState {
            return BoatState(state.direction, Position(state.position.x, state.position.y + distance))
        }
    }

    class South(private val distance: Int) : MoveInstruction() {
        override fun move(state: BoatState): BoatState {
            return BoatState(state.direction, Position(state.position.x, state.position.y - distance))
        }
    }

    class East(private val distance: Int) : MoveInstruction() {
        override fun move(state: BoatState): BoatState {
            return BoatState(state.direction, Position(state.position.x - distance, state.position.y))
        }
    }

    class West(private val distance: Int) : MoveInstruction() {
        override fun move(state: BoatState): BoatState {
            return BoatState(state.direction, Position(state.position.x + distance, state.position.y))
        }
    }

    class Forward(private val distance: Int) : MoveInstruction() {
        override fun move(state: BoatState): BoatState {
            return when (state.direction) {
                Direction.EAST -> East(distance)
                Direction.NORTH -> North(distance)
                Direction.WEST -> West(distance)
                Direction.SOUTH -> South(distance)
            }.move(state)
        }
    }

    class Left(private val degree: Int) : MoveInstruction() {
        override fun move(state: BoatState): BoatState {
            return BoatState(shiftDirection(degree, state.direction, Int::plus), state.position)
        }
    }

    class Right(private val degree: Int) : MoveInstruction() {
        override fun move(state: BoatState): BoatState {
            return BoatState(shiftDirection(degree, state.direction, Int::minus), state.position)
        }
    }
}

fun shiftDirection(degree: Int, direction: Direction, operation: (Int, Int) -> Int): Direction {
    val index = degree / 90
    return DIRECTIONS[Math.floorMod(operation(direction.ordinal, index), DIRECTIONS.size)]
}

fun solveDay12p1(input: List<String>): Int {
    val (x, y) = input.asSequence()
        .map {
            val value = it.substring(1).toInt()
            when (it[0]) {
                'R' -> MoveInstruction.Right(value)
                'L' -> MoveInstruction.Left(value)
                'F' -> MoveInstruction.Forward(value)
                'N' -> MoveInstruction.North(value)
                'S' -> MoveInstruction.South(value)
                'E' -> MoveInstruction.East(value)
                'W' -> MoveInstruction.West(value)
                else -> throw IllegalArgumentException("unknown direction ${it[0]}")
            }
        }
        .fold(BoatState(Direction.EAST, Position(0, 0))) { acc, moveInstruction -> moveInstruction.move(acc) }
        .position
    return abs(x) + abs(y)
}

sealed class MoveInstructionWithWaypoint {
    abstract fun move(state: WaypointBoatState): WaypointBoatState

    class North(private val distance: Int) : MoveInstructionWithWaypoint() {
        override fun move(state: WaypointBoatState): WaypointBoatState {
            return WaypointBoatState(state.position, Position(state.waypoint.x, state.waypoint.y + distance))
        }
    }

    class South(private val distance: Int) : MoveInstructionWithWaypoint() {
        override fun move(state: WaypointBoatState): WaypointBoatState {
            return WaypointBoatState(state.position, Position(state.waypoint.x, state.waypoint.y - distance))
        }
    }

    class East(private val distance: Int) : MoveInstructionWithWaypoint() {
        override fun move(state: WaypointBoatState): WaypointBoatState {
            return WaypointBoatState(state.position, Position(state.waypoint.x - distance, state.waypoint.y))
        }
    }

    class West(private val distance: Int) : MoveInstructionWithWaypoint() {
        override fun move(state: WaypointBoatState): WaypointBoatState {
            return WaypointBoatState(state.position, Position(state.waypoint.x + distance, state.waypoint.y))
        }
    }

    class Forward(private val distance: Int) : MoveInstructionWithWaypoint() {
        override fun move(state: WaypointBoatState): WaypointBoatState {
            return WaypointBoatState(
                Position(
                    state.position.x + distance * state.waypoint.x,
                    state.position.y + distance * state.waypoint.y
                ),
                state.waypoint
            )
        }
    }

    class Left(private val degree: Int) : MoveInstructionWithWaypoint() {
        override fun move(state: WaypointBoatState): WaypointBoatState {
            return shiftDirection(degree, state, ::shiftLeft)
        }
    }

    class Right(private val degree: Int) : MoveInstructionWithWaypoint() {
        override fun move(state: WaypointBoatState): WaypointBoatState {
            return shiftDirection(degree, state, ::shiftRight)
        }
    }

    fun shiftDirection(degree: Int, state: WaypointBoatState, shift: (Position) -> Position): WaypointBoatState {
        val index = degree / 90
        return WaypointBoatState(state.position, generateSequence(state.waypoint, shift).take(index + 1).last())
    }
}

fun shiftLeft(position: Position) = Position(position.y, -position.x)
fun shiftRight(position: Position) = Position(-position.y, position.x)

data class WaypointBoatState(val position: Position, val waypoint: Position)

fun solveDay12p2(input: List<String>): Int {
    val (x, y) = input.asSequence()
        .map {
            val value = it.substring(1).toInt()
            when (it[0]) {
                'R' -> MoveInstructionWithWaypoint.Right(value)
                'L' -> MoveInstructionWithWaypoint.Left(value)
                'F' -> MoveInstructionWithWaypoint.Forward(value)
                'N' -> MoveInstructionWithWaypoint.North(value)
                'S' -> MoveInstructionWithWaypoint.South(value)
                'E' -> MoveInstructionWithWaypoint.East(value)
                'W' -> MoveInstructionWithWaypoint.West(value)
                else -> throw IllegalArgumentException("unknown direction ${it[0]}")
            }
        }
        .fold(WaypointBoatState(Position(0, 0), Position(-10, 1))) { acc, moveInstruction -> moveInstruction.move(acc) }
        .position
    return abs(x) + abs(y)
}