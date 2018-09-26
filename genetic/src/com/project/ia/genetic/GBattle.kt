package com.project.ia.genetic

/**
 * Permet de faire combattre deux teams.
 */
object GBattle {

    fun fight(team1: GTeam, team2: GTeam): GState {

        val tempTeam = mutableListOf<GFighter>()

        team1.fighters.map { it.team = 0 }
        tempTeam.addAll(team1.copy().fighters)

        team2.fighters.map { it.team = 1 }
        tempTeam.addAll(team2.copy().fighters)

        val fighters = tempTeam.toTypedArray()

        // Préparation au combat
        fighters.map { it.resetStats(true) }
        fighters.map { it.alive = true }
        fighters.sortByDescending { it.stats.spd }

        val turnManager = GTurn()
        val state = GState()

        turnManager.play(fighters, state)

        while (state.winner == -1)
            state.nextTurn(fighters, turnManager)

        return state

    }

}