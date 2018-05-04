package com.llgames.ia.battle.logic

import com.llgames.ia.battle.State
import com.llgames.ia.battle.logic.Stats.Companion.GENERAL
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

data class Jet(var type: Int, var elem: Int, var damage: Int)

data class Boost(var stat: String, var type: Int, var value: Int, var duration: Int)

data class Weapon(var jets: Array<Jet>)

/**
 * Partie logique des combattants.
 */

open class LFighter(val name: String, val team: Int, val id: Int) {
    private val ia = IA()
    var job = "HUMAN"
    var stats = Stats()
    var maxStats = Stats()
    var alive = true
    var boosts: MutableList<Boost> = mutableListOf()
    var protected: LFighter? = null

    fun changeJob(c: String) {
        job = c
        when (c) {
            "Dark Mage" -> maxStats.setTo(DARK_MAGE.stats, true)
            "White Mage" -> maxStats.setTo(WHITE_MAGE.stats, true)
            else -> maxStats.setTo(HUMAN.stats, true)
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
        stats.setTo(maxStats, true)
    }

    /**
     * Applique les boosts du tour.
     */
    fun newTurn() {

        stats.setTo(maxStats)

        for (boost in boosts) {
            boost.duration--
            when (boost.stat) {
                "ATK" -> stats.atk[boost.type] += boost.value
                "DEF" -> stats.def[boost.type] += boost.value
                else -> Unit
            }
        }

        // Remove finished boosts
        boosts = boosts.filter { it.duration > 0 }.toMutableList()

    }

    fun attack(target: LFighter, weapon: Weapon?) {

        for ((_, _, damage) in damageCalculation(this, target, weapon)) {
            target.stats.hp -= damage
        }

    }

    fun defend() {
        stats.def[GENERAL] += 50
    }

    /**
     * Tue le combattant.
     */
    fun kill(fighters: Array<out LFighter>) {
        alive = false
        stats.hp = 0
        // Si le combattant protégeait un joueur, ce n'est plus le cas
        fighters.forEach { if (it.protected?.id == id) it.protected = null }
    }

    infix fun getPercent(value: String): Int = when (value) {
        "HP" -> 100 * stats.hp / maxStats.hp
        else -> 0
    }

    fun getIAString(): String {
        return ia.toString()
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