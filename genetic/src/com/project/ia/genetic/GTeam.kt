package com.project.ia.genetic

import com.project.ia.def.JOBS
import com.project.ia.def.Runes
import com.project.ia.logic.RT
import com.project.ia.logic.Rune

/**
 * Gère une équipe de [GFighter].
 *
 * TODO: Génération de [GFighter] aléatoires
 * TODO: Calcul du fitness
 * TODO: Mutations
 */
class GTeam() {
    var fighters: Array<GFighter> = arrayOf()
    var fitness = 0

    init {
        val fighter1 = GFighter("Hadri", 0, 0)
        val fighter2 = GFighter("yopox", 0, 1)
        val fighter3 = GFighter("Ixous", 0, 2)

        fighters = arrayOf(fighter1, fighter2, fighter3)

        shuffle()
    }

    private fun shuffle() {

        for (fighter in fighters) {

            // Règles d'IA
            val rules = mutableListOf<Array<Rune>>()
            rules.add(getRandomRule())
            rules.add(getRandomRule())
            var chaine = ""
            rules.map { chaine += " - " + Runes.toString(it) }
            fighter.changeIA(chaine.drop(3))

            // Classe
            fighter.changeJob(JOBS.randomJob())

        }

    }

    /**
     * Renvoie une règle aléatoire.
     */
    private fun getRandomRule(): Array<Rune> {
        // Construction d'une IA aléatoire
        val runes = mutableListOf<Rune>()

        // On ajoute une porte logique
        runes.add(Runes.getRandom(RT.GATE))

        // On ajoute les runes nécessaires
        // On utilise une pile contenant les runes suivantes nécessaires
        val next = mutableListOf<RT>()
        next.addAll(runes[0].next.reversed())

        while (next.any()) {
            val type = next.removeAt(next.lastIndex)
            runes.add(Runes.getRandom(type))
            next.addAll(runes[runes.lastIndex].next.reversed())
        }

        return runes.toTypedArray()

    }

    /**
     * Peut faire muter la team.
     */
    fun mutate() {

    }

}