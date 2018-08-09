package com.project.ia.genetic

object CONFIG {
    const val NTEAMS = 20
    const val N_GEN = 20
    const val MAX_TURNS = 100
}

/**
 * Fonction principale.
 */

object Algo {

    fun main() {

        // Création des équipes
        val teams = MutableList(CONFIG.NTEAMS) { GTeam() }

        // Boucle sur les générations
        for (gen in 1..CONFIG.N_GEN) {

            // Reset du fitness
            teams.map { it.fitness = 0 }

            // On fait combattre chaque équipe contre les autres
            for (i in 0 until CONFIG.NTEAMS) {
                for (j in 0 until CONFIG.NTEAMS) {
                    if (i != j) {
                        val state = GBattle.fight(teams[i], teams[j])

                        // Bonus de fitness pour la team gagnante
                        when (state.winner) {
                            0 -> teams[i].fitness += 1000 + (1000 - 10 * state.turn)
                            1 -> teams[j].fitness += 1000 + (1000 - 10 * state.turn)
                        }

                    }
                }
            }

            // Sélection des meilleures teams
            teams.sortByDescending { it.fitness }
            teams = teams.dropLast(CONFIG.NTEAMS / 2)

            // Affichage de la meilleure team
            println("----GEN $gen----")
            println("Best fitness : ${teams[0].fitness}")
            for (i in 0..2) {
                println("Fighter #${i + 1} :")
                println("----- ${teams[0].fighters[i].job.name}")
                println("----- ${teams[0].fighters[i].getIAString()}")
            }

            // Ajout de nouvelles teams
            for (i in 0 until CONFIG.NTEAMS / 2)
                teams.add(GTeam())

            // Mutations
            teams.map { it.mutate() }

        }
    }
}

