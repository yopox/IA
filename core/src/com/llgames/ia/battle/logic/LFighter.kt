package com.llgames.ia.battle.logic

import com.llgames.ia.battle.Fighter
import com.llgames.ia.battle.State

data class Stats(var hp: Int = 0, var mp: Int = 0, var atk: Int = 0, var wsd: Int = 0, var def: Int = 0, var spd: Int = 0)

val HUMAN = Stats(100, 25, 25, 25, 25, 25);

open class LFighter(val name: String, val team: Int, val id: Int) {
    private val ia = IA()
    var stats = Stats()
    var maxStats = Stats()

    fun getRule(fighters: Array<Fighter>, state: State): IA.Rule {
        return ia.getRule(fighters, state)
    }

    infix fun getPercent(value: String): Int {
        return when (value) {
            "HP" -> 100 * stats.hp / maxStats.hp
            "MP" -> 100 * stats.mp / maxStats.mp
            else -> 0
        }
    }

    fun getIAString(): String {
        return ia.toString()
    }

}