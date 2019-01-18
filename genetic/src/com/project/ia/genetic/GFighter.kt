package com.project.ia.genetic

import com.project.ia.def.Equip
import com.project.ia.def.Jobs
import com.project.ia.def.Runes
import com.project.ia.logic.LFighter
import com.project.ia.logic.RT
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
        if (Math.random() < 0.25)
            changeJob(Jobs.randomJob())

        // Mutation de l'équipement
        if (Math.random() < 0.25)
            weapon = Equip.randomWeapon()

        // Mutation du sort 1
        if (Math.random() < 0.25)
            spell1 = Equip.randomSpell()

        // Mutation du sort 2
        if (Math.random() < 0.25)
            spell1 = Equip.randomSpell()

        // Mutation de la relique
        if (Math.random() < 0.25)
            relic = Equip.randomRelic()

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
            when (oldRune.type) {
                RT.GATE -> {
                    val singleCondGates = arrayOf("ID", "NOT")
                    // Il faut rajouter une condition
                    if (oldRune.id in singleCondGates && newRune.id !in singleCondGates) {
                        println("ADD COND : " + runes[i])
                        val newCond = Runes.getRandom(RT.CONDITION)
                        runes[i].add(1, newCond.id)
                        for (nextRune in newCond.next.reversed())
                            runes[i].add(2, Runes.getRandom(nextRune).id)
                        println(runes[i])
                    } else if (oldRune.id !in singleCondGates && newRune.id in singleCondGates) {
                        println("SUPPR COND : " + runes[i])
                        // Il faut supprimer une condition
                        if (Math.random() < 0.5) {
                            // On supprime la première condition
                            runes[i].removeAt(1)
                            while (Runes.runes[runes[i][1]]!!.type != RT.CONDITION)
                                runes[i].removeAt(1)
                        } else {
                            // On supprime la seconde condition
                            val condIndex = runes[i].indexOfLast { Runes.runes[it]!!.type == RT.CONDITION }
                            while (Runes.runes[runes[i][condIndex]]!!.type != RT.ACTION)
                                runes[i].removeAt(condIndex)
                        }
                        println(runes[i])
                    }
                }
                RT.CONDITION -> {
                    // TODO
                }
                RT.ACTION -> {
                    // TODO
                }
                else -> {}
            }



        }

        resetStats()
    }
}