package com.project.ia.battle

import com.project.ia.data.Team
import com.project.ia.logic.LFighter

/**
 * Crée des objets de type [Fighter] pour [Battle].
 */
class Team(val nb: Int) {
    var name = ""
    var fighters = mutableListOf<Fighter>()

    fun import(team: Team) {
        name = team.name
        for (lFighter in team.fighters) {
            fighters.add(convertLFighter(lFighter, fighters.size))
        }
    }

    private fun convertLFighter(lFighter: LFighter, fighterNb: Int): Fighter {
        val depX = -0.2f + 0.2f * fighterNb
        val depY = if (nb == 0) -0.425f else 0.425f
        val fighter = Fighter(depX, depY, lFighter.name, nb, 3 * nb + fighterNb)

        fighter.changeJob(lFighter.job)
        fighter.changeIA(lFighter.getIAString())
        fighter.weapon = lFighter.weapon
        fighter.spell1 = lFighter.spell1
        fighter.spell2 = lFighter.spell2
        fighter.relic = lFighter.relic

        return fighter
    }

}