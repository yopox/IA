package com.project.ia.genetic

/**
 * Permet de faire combattre deux teams.
 */
object GBattle {

    fun fight(team1: GTeam, team2: GTeam): GState {

        val tempTeam = mutableListOf<GFighter>()

        team1.fighters.map { it.team = 0 }
        team2.fighters.map { it.team = 1 }

        tempTeam.addAll(team1.fighters)
        tempTeam.addAll(team2.fighters)

        val fighters = tempTeam.toTypedArray()

        // Préparation au combat
        fighters.map { it.resetStats(true) }
        fighters.map { it.alive = true }
        fighters.sortByDescending { it.stats.spd }

        val turnManager = GTurn()
        val state = GState()

        turnManager.play(fighters, state)

        while (state.winner == -1) {

            // On regarde si le combat est fini
            when {
                fighters.none { it.team == 0 && it.alive } -> state.winner = 1
                fighters.none { it.team == 1 && it.alive } -> state.winner = 0
                state.turn > CONFIG.MAX_TURNS -> state.winner = -2
                else -> {

                    // Tour du personnage suivant
                    state.charTurn++

                    // Tous les personnages ont joué
                    if (state.charTurn == fighters.size) {
                        state.newTurn()
                        fighters.sortByDescending { it.stats.spd }
                    }

                    // Personnage mort
                    if (!fighters[state.charTurn].alive)
                        fighters[state.charTurn].endTurn()
                    else
                        turnManager.play(fighters, state)
                }
            }
        }

        return state

    }

}