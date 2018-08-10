package com.project.ia.genetic

import java.util.Random

object CONFIG {
    const val NTEAMS = 50
    const val N_GEN = 3000
    const val MAX_TURNS = 100
    const val RATIO_KEPT = .1 // doit être moins que .3, et c'est conseillé qu'il soit même moins que .25
    const val NTEAMS_KEPT = (NTEAMS * RATIO_KEPT).toInt()
    const val MUTATION_PROB = .6
}

/**
 * Fonction principale.
 */

object Algo {
    fun test(team: GTeam) {
        val tteams = MutableList(CONFIG.NTEAMS * 1000) { GTeam() }

        var nwins = 0
        var nloses = 0
        var nnul = 0

        tteams.forEach {
            val state = GBattle.fight(team, it)

            when (state.winner) {
                0 -> nwins++
                1 -> nloses++
                -2 -> nnul++
            }
        }

        println()
        println("*** TESTS ***")
        println()
        println("wins : $nwins")
        println("loses : $nloses")
        println("nul : $nnul")
        println()
    }

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
                            0 -> teams[i].fitness += 1000 + kotlin.math.max(1000 - 10 * state.turn, 0)
                            1 -> teams[j].fitness += 1000 + kotlin.math.max(1000 - 10 * state.turn, 0)
                            -2 -> {
                                teams[i].fitness += 500
                                teams[j].fitness += 500
                            }
                        }

                    }
                }
            }

            // Sélection des meilleures teams
            teams.sortByDescending { it.fitness }

            for (i in CONFIG.NTEAMS - 1 downTo CONFIG.NTEAMS_KEPT)
                teams.removeAt(i)


            // Affichage de la meilleure team
            println("----GEN $gen----")
            println("Best fitness : ${teams[0].fitness}")
            for (i in 0..2) {
                println("Fighter #${i + 1} :")
                val fighter = teams[0].fighters[i]
                println("----- ${fighter.job.name}  (${fighter.weapon} • ${fighter.spell1} • ${fighter.spell2} • ${fighter.relic})")
                println("----- ${teams[0].fighters[i].getIAString()}")
            }

            // Calcul des teams mutées
            val mteams = mutableListOf<GTeam>()
            for (t in teams)
                mteams.add(t.mutate())

            // Calcul des teams reproduites
            val rteams = mutableListOf<GTeam>()
            for (i in 1..CONFIG.NTEAMS_KEPT)
                rteams.add(GTeam.reproduce(
                        teams[Random().nextInt(teams.size)],
                        teams[Random().nextInt(teams.size)]))

            // Ajout de nouvelles teams
            mteams.forEach { teams.add(it) } // Ajout des teams mutées
            rteams.forEach { teams.add(it) } // Ajout des teams reproduites
            while (teams.size != CONFIG.NTEAMS)
                teams.add(GTeam()) // nouvelles teams aléatoires

        }

        teams.sortByDescending { it.fitness }
        test(teams[0])
    }
}

