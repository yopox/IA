package com.project.ia.genetic

import com.project.ia.def.Equip
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

        // Mutation de l'équipement
        if (Math.random() < 1) {
            weapon = Equip.WEAPONS.keys.elementAt(Random().nextInt(Equip.WEAPONS.size))
        }


        // Mutation du sort 1
        if (Math.random() < 1) {
            spell1 = Equip.SPELLS.keys.elementAt(Random().nextInt(Equip.SPELLS.size))
        }

        // Mutation du sort 2
        if (Math.random() < 1) {
            spell1 = Equip.SPELLS.keys.elementAt(Random().nextInt(Equip.SPELLS.size))
        }

        // Mutation de la relique
        if (Math.random() < 2) {
            relic = Equip.RELICS.keys.elementAt(Random().nextInt(Equip.RELICS.size))
        }

        // TODO : Mutation de l'IA
        if (Math.random() < .4) {
            changeIA(getIAString()) // ne change rien.
        }

        resetStats()
    }
}