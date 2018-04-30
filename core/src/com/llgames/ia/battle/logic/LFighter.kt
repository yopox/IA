package com.llgames.ia.battle.logic

import com.llgames.ia.battle.Fighter
import com.llgames.ia.battle.State

data class Jet(var type: String, var elem: String, var damage: Int)

data class Weapon(var jets: Array<Jet>)

open class LFighter(val name: String, val team: Int, val id: Int) {
    private val ia = IA()
    var stats = Stats()
    var maxStats = Stats()

    fun prepare() {
        stats.setTo(maxStats)
    }

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

    fun attack(target: Fighter, weapon: Weapon?) {
        target.stats.hp -= damageCalculation(this, target, weapon)
        //TODO: Implement death
    }

}

fun damageCalculation(fighter: LFighter, target: LFighter, weapon: Weapon?): Int {
    if (weapon == null)
        return 0

    // Attack

    val coeff = 1 + fighter.stats.atk["general"]!! / 100
    var sum = 0

    for ((t, e, d) in weapon.jets) {
        sum += (d + fighter.stats.atkB[t]!! + fighter.stats.atkB[e]!!) *
                    (1 + fighter.stats.atk[t]!! / 100) *
                    (1 + fighter.stats.atk[e]!! / 100)
    }

    return coeff * sum
}