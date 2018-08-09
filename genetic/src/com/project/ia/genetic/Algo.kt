package com.project.ia.genetic

object CONFIG {
    const val NTEAMS = 20
    const val N_GEN = 20
    const val MAX_TURNS = 100
    const val RATIO_KEPT = .2 // doit être moins que .3, et c'est conseillé qu'il soit même moins que .25
    const val NTEAMS_KEPT = (NTEAMS * RATIO_KEPT).toInt()
}

/**
 * Fonction principale.
 */

object Algo {

   /* fun main() {

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
                            //-2 -> {
                            //    teams[i].fitness += 500
                            //    teams[j].fitness += 500
                            //}
                        }

                    }
                }
            }

            // Sélection des meilleures teams
            teams.sortByDescending { it.fitness }

            for(i in CONFIG.NTEAMS - 1 downTo CONFIG.NTEAMS_KEPT)
                teams.removeAt(i)


            // Affichage de la meilleure team
            println("----GEN $gen----")
            println("Best fitness : ${teams[0].fitness}")
            for (i in 0..2) {
                println("Fighter #${i + 1} :")
                println("----- ${teams[0].fighters[i].job.name}")
                println("----- ${teams[0].fighters[i].getIAString()}")
            }

            // Ajout des teams mutées
            val mteams = mutableListOf<GTeam>()
            for (t in teams)
                mteams.add(t.mutate())
            mteams.forEach { teams.add(it) }

            // TODO: Ajout des teams descendantes

            // Ajout de nouvelles teams
            while (teams.size != CONFIG.NTEAMS)
                teams.add(GTeam())

        }
    }
    */
    fun main() {
       val team1 = GTeam()

       GBattle.fight(team1, team1)
   }
}

