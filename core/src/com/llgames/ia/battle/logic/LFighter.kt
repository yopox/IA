package com.llgames.ia.battle.logic

import com.llgames.ia.battle.State
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

data class Jet(var type: String, var elem: String, var damage: Int)

data class Weapon(var jets: Array<Jet>)

open class LFighter(val name: String, val team: Int, val id: Int) {
    private val ia = IA()
    var stats = Stats()
    var maxStats = Stats()

    fun setClass(c: String) = when (c) {
        "Dark Mage" -> maxStats.setTo(DARK_MAGE.stats)
        "White Mage" -> maxStats.setTo(WHITE_MAGE.stats)
        else -> maxStats.setTo(HUMAN.stats)
    }

    fun prepare() {
        stats.setTo(maxStats)
    }

    fun getRule(fighters: Array<out LFighter>, state: State): IA.Rule {
        return ia.getRule(fighters, state)
    }

    infix fun getPercent(value: String): Int {
        return when (value) {
            "HP" -> 100 * stats.hp / maxStats.hp
            else -> 0
        }
    }

    fun getIAString(): String {
        return ia.toString()
    }

    fun attack(target: LFighter, weapon: Weapon?) {

        for ((_, _, damage) in damageCalculation(this, target, weapon)) {
            target.stats.hp -= damage
        }
        //TODO: Implement death

    }

}

fun atkStat(atk: Int) = max(-100, atk) / 100.0

fun defStat(def: Int) = min(100, def) / 100.0

fun damageCalculation(fighter: LFighter, target: LFighter, weapon: Weapon?): ArrayList<Jet> {

    val coeffAtk = 1 + atkStat(fighter.stats.atk[Stats.GENERAL]!!)
    val coeffDef = 1 - defStat(target.stats.def["general"]!!)
    val damageDealt = ArrayList<Jet>()

    for ((t, e, d) in weapon!!.jets) {
        // Offensive formula
        val Ai = coeffAtk * max(0, d + fighter.stats.atkB[t]!! + fighter.stats.atkB[e]!!) *
                (1 + atkStat(fighter.stats.atk[t]!!)) *
                (1 + atkStat(fighter.stats.atk[e]!!))
        // Defensive formula
        val Di = coeffDef * max(0.0, Ai - target.stats.defB[t]!! - target.stats.defB[e]!!) *
                (1 - defStat(target.stats.def[t]!!)) *
                (1 - defStat(target.stats.def[e]!!))
        damageDealt.add(Jet(t, e, floor(Di).toInt()))
    }

    return damageDealt
}