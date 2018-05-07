package com.llgames.ia.battle.logic

import com.llgames.ia.battle.State
import com.llgames.ia.battle.logic.Stats.Companion.GENERAL
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

data class Jet(var type: Int, var elem: Int, var damage: Int)

data class Boost(var stat: String, var type: Int, var value: Int, var duration: Int, var onSelf: Boolean = true)

data class Weapon(var jets: Array<Jet>, var boosts: Array<Boost>? = null)

/**
 * Partie logique des combattants.
 */

open class LFighter(val name: String, val team: Int, val id: Int) {
    private val ia = IA()
    var boosts: MutableList<Pair<Boost, LFighter>> = mutableListOf()
    var job = "HUMAN"
    var stats = Stats()
    var maxStats = Stats()
    var alive = true
    var protected: LFighter? = null

    open fun changeJob(c: String) {
        job = c
        when (c) {
            "Dark Mage" -> maxStats.setTo(DARK_MAGE.stats)
            "White Mage" -> maxStats.setTo(WHITE_MAGE.stats)
            "Paladin" -> maxStats.setTo(PALADIN.stats)
            else -> maxStats.setTo(HUMAN.stats)
        }
    }

    fun setIA(type: String) = ia.setRules(type)

    /**
     * Renvoie la règle d'IA utilisée ce tour-ci.
     */
    fun getRule(fighters: Array<out LFighter>, state: State): IA.Rule = ia.getRule(fighters, state)

    /**
     * Reset les stats du personnage.
     */
    fun prepare() {
        stats.setTo(maxStats)
    }

    /**
     * Logique de fin du tour (boosts expirés).
     */
    fun endTurn(fighters: Array<out LFighter>) {

        stopProtecting(fighters)

        for ((boost, target) in boosts) {
            boost.duration--
            if (boost.duration < 0)
                target.applyBoost(boost)
        }

        boosts.removeIf { it.first.duration < 0 }

    }

    fun attack(target: LFighter, weapon: Weapon?) {

        for ((_, _, damage) in damageCalculation(this, target, weapon)) {
            target.stats.hp -= damage
        }

    }

    /**
     * Tue le combattant.
     */
    fun kill(fighters: Array<out LFighter>) {
        alive = false
        stats.hp = 0
        boosts.clear()
        stopProtecting(fighters)
    }

    infix fun getPercent(value: String): Int = when (value) {
        "HP" -> 100 * stats.hp / maxStats.hp
        else -> 0
    }

    fun getIAString(): String {
        return ia.toString()
    }

    /**
     * Assure que le combattant ne protège plus de combattant•e•s
     */
    private fun stopProtecting(fighters: Array<out LFighter>) {
        fighters.forEach { if (it.protected?.id == id) it.protected = null }
    }

    /**
     * Applique un boost.
     */
    fun applyBoost(boost: Boost) {
        when (boost.stat) {
            "ATK" -> stats.atk[boost.type] += boost.value
            "DEF" -> stats.def[boost.type] += boost.value
            else -> Unit
        }
    }

}

fun atkStat(atk: Int) = max(-100, atk) / 100.0

fun defStat(def: Int) = min(100, def) / 100.0

fun damageCalculation(fighter: LFighter, target: LFighter, weapon: Weapon?): ArrayList<Jet> {

    val coeffAtk = 1 + atkStat(fighter.stats.atk[GENERAL])
    val coeffDef = 1 - defStat(target.stats.def[GENERAL])
    val damageDealt = ArrayList<Jet>()

    for ((t, e, d) in weapon!!.jets) {
        // Offensive formula
        val Ai = coeffAtk * max(0, d + fighter.stats.atkB[t] + fighter.stats.atkB[e]) *
                (1 + atkStat(fighter.stats.atk[t])) *
                (1 + atkStat(fighter.stats.atk[e]))
        // Defensive formula
        val Di = coeffDef * max(0.0, Ai - target.stats.defB[t] - target.stats.defB[e]) *
                (1 - defStat(target.stats.def[t])) *
                (1 - defStat(target.stats.def[e]))
        damageDealt.add(Jet(t, e, floor(Di).toInt()))
    }

    return damageDealt
}