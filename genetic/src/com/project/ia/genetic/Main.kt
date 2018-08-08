package com.project.ia.genetic

import java.time.Duration
import java.time.Instant

object CONFIG {
    const val NTEAMS = 100
    const val MAX_TURNS = 100
}

/**
 * Fonction principale.
 */
public fun main(args: Array<String>) {

    // Préparation des deux équipes
    val fighters: Array<GFighter>
    val tempTeam = mutableListOf<GFighter>()

    val team1 = GTeam(0)
    val team2 = GTeam(1)

    tempTeam.addAll(team1.fighters)
    tempTeam.addAll(team2.fighters)

    fighters = tempTeam.toTypedArray()

    // Préparation au combat
    fighters.map { it.resetStats(true) }
    fighters.sortByDescending { it.stats.spd }

    val turnManager = GTurn()
    val state = GState()

    val start = Instant.now()

    turnManager.play(fighters, state)

    while (state.winner == -1) {

        do {

            state.charTurn++

            if (state.charTurn == fighters.size) {
                state.newTurn()
                fighters.sortByDescending { it.stats.spd }
            }

            if (!fighters[state.charTurn].alive)
                fighters[state.charTurn].endTurn()

        } while (!fighters[state.charTurn].alive)

        turnManager.play(fighters, state)

        if (fighters.none { it.team == 0 && it.alive }) {
            state.winner = 1
        } else if (fighters.none { it.team == 1 && it.alive }) {
            state.winner = 0
        }

    }

    val finish = Instant.now()

    println("Team #${state.winner} won in ${Duration.between(start, finish).toMillis()}ms")


}

