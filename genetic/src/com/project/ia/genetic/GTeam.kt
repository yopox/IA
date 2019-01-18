package com.project.ia.genetic

import com.project.ia.def.Equip
import com.project.ia.def.Jobs
import com.project.ia.def.Runes
import com.project.ia.logic.RT
import com.project.ia.logic.Rune

/**
 * Gère une équipe de [GFighter].
 *
 * TODO: Calcul du fitness
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
            fighter.changeJob(Jobs.randomJob())

            // Equipement
            fighter.weapon = Equip.randomWeapon()
            fighter.spell1 = Equip.randomSpell()
            fighter.spell2 = Equip.randomSpell()
            fighter.relic = Equip.randomRelic()

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
     * Copie la team.
     */
    fun copy(): GTeam {
        val res = GTeam()

        for (i in 0..2) {

            res.fighters[i].name = fighters[i].name
            res.fighters[i].changeJob(fighters[i].job)
            res.fighters[i].team = fighters[i].team
            res.fighters[i].id = fighters[i].id

            // Copie de l'IA
            res.fighters[i].changeIA(fighters[i].getIAString())

            // Copie de l'équipement
            res.fighters[i].weapon = fighters[i].weapon
            res.fighters[i].spell1 = fighters[i].spell1
            res.fighters[i].spell2 = fighters[i].spell2
            res.fighters[i].relic = fighters[i].relic

        }

        return res
    }

    /**
     * Peut faire muter la team.
     */
    fun mutate(): GTeam {
        val mutation = copy()

        for (i in 0..2)
            if (Math.random() > CONFIG.MUTATION_PROB)
                mutation.fighters[i].mutate()

        return mutation
    }

    /**
     * Fonctions statiques
     */
    companion object {

        /**
         * Permets de faire se reproduire deux teams
         */
        fun reproduce(team1: GTeam, team2: GTeam): GTeam {
            val res = GTeam()

            for (i in 0..2)
                if (Math.random() > .5)
                    res.fighters[i] = team1.fighters[i]
                else
                    res.fighters[i] = team2.fighters[i]

            return res
        }
    }

}