package com.project.ia.genetic

import com.project.ia.def.Equip
import com.project.ia.def.JOBS
import com.project.ia.def.Runes
import com.project.ia.logic.LFighter
import java.util.Random

/**
 * Combattant génétique. Hérite de [LFighter].
 *
 * TODO: fitness du fighter
 */
class GFighter(name: String, team: Int, id: Int) : LFighter(name, team, id) {

    /**
     * Permet de muter un fighter
     */
    fun mutate() {

        // Mutation du job
        if (Math.random() < 0.25) {
            changeJob(JOBS.randomJob())
        }

        // Mutation de l'équipement
        if (Math.random() < 0.25) {
            weapon = Equip.WEAPONS.keys.elementAt(Random().nextInt(Equip.WEAPONS.size))
        }


        // Mutation du sort 1
        if (Math.random() < 0.25) {
            spell1 = Equip.SPELLS.keys.elementAt(Random().nextInt(Equip.SPELLS.size))
        }

        // Mutation du sort 2
        if (Math.random() < 0.25) {
            spell1 = Equip.SPELLS.keys.elementAt(Random().nextInt(Equip.SPELLS.size))
        }

        // Mutation de la relique
        if (Math.random() < 0.25) {
            relic = Equip.RELICS.keys.elementAt(Random().nextInt(Equip.RELICS.size))
        }

        // Mutation de l'IA
        while (Math.random() < .4) {

            // On récupère les runes
            val runes = getIAString().split(" - ").map { it.split(" ").toMutableList() }

            // Choix d'une règle à modifier
            val i = Random().nextInt(runes.size)
            val j = Random().nextInt(runes[i].size)

            // Choix d'une rune à échanger
            val oldRune = Runes.runes[runes[i][j]]!!
            val newRune = Runes.getRandom(oldRune.type)
            runes[i][j] = newRune.id

            // TODO: Correction de la règle au cas où les [next] sont différents

        }

        resetStats()
    }
}