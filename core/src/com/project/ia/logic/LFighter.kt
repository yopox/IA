package com.project.ia.logic

import com.project.ia.def.Equip
import com.project.ia.def.JOBS
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

data class Jet(var type: TYPES, var elem: ELEMENTS, var damage: Int)

/**
 * Définition d'un [Boost].
 * @param [apply] est une fonction qui applique le boost voulu
 */
data class Boost(var stat: STAT_ENUM,
                 var value: Int,
                 var duration: Int,
                 var onSelf: Boolean = true)

data class ActiveBoost(val boost: Boost, val target: LFighter)

/**
 * Partie logique des combattants.
 */
open class LFighter(var name: String, var team: Int = 0, var id: Int = 0) {
    private val ia = IA()
    var boosts: MutableList<ActiveBoost> = mutableListOf()
    var job = JOBS.getJob("HUMAN")
    var stats = Stats()
    var maxStats = Stats()
    var alive = true
    var protected: LFighter? = null
    var weapon = "NONE"
    var spell1 = "NONE"
    var spell2 = "NONE"
    var relic = "NONE"
    var onceUsed = false

    open fun changeJob(job: Job) {
        this.job = job
        maxStats.setTo(job.stats, true)
    }

    fun changeIA(rules: String) = ia.changeIA(rules)

    /**
     * Renvoie la règle d'IA utilisée ce tour-ci.
     */
    fun getRule(fighters: Array<out LFighter>, state: State): Array<Rune> = ia.getRule(fighters, state)

    /**
     * Met à jour [maxStats] en tenant compte du Job et de l'équipement.
     */
    fun updateMaxStats() {
        maxStats.setTo(job.stats, true)

        // Arme
        for (boost in Equip.getWeapon(weapon).stats) {
            maxStats.applyBuff(boost)
        }

        // Relique
        for (boost in Equip.getRelic(relic).stats) {
            maxStats.applyBuff(boost)
        }

    }

    /**
     * Reset les stats du personnage.
     */
    fun resetStats(setHp: Boolean = false) {
        updateMaxStats()
        stats.setTo(maxStats, setHp)
    }

    /**
     * Logique de début de tour.
     */
    fun startTurn(fighters: Array<out LFighter>) {
        stopProtecting(fighters)
    }

    /**
     * Logique de fin du tour (boosts expirés).
     */
    fun endTurn() {

        resetStats()

        // On applique les boosts
        for ((boost, target) in boosts) {
            boost.duration--
            if (boost.duration >= 0)
                target.applyBoost(boost)
        }

        // On supprime les boosts terminés
        val toRemove = mutableListOf<Int>()
        for (i in 0..boosts.lastIndex) {
            if (boosts[i].boost.duration < 0)
                toRemove.add(i)
        }
        toRemove.reversed().map { boosts.removeAt(it) }

    }

    fun attack(target: LFighter, jets: Array<Jet>?) {

        for ((_, _, damage) in damageCalculation(this, target, jets)) {
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
        val idStop = id
        fighters.forEach { if (it.protected?.id == idStop) it.protected = null }
    }

    /**
     * Applique un boost.
     */
    private fun applyBoost(boost: Boost) {
        if (boost.stat != STAT_ENUM.HP)
            // Modification de stat
            stats.applyBuff(Pair(boost.stat, boost.value))
        else if (alive && boost.value > 0) {
            // Soin
            stats.hp = min(stats.hp + boost.value, maxStats.hp)
        }
    }

}

fun atkStat(atk: Int) = max(-100, atk) / 100.0

fun defStat(def: Int) = min(100, def) / 100.0

fun damageCalculation(fighter: LFighter, target: LFighter, jets: Array<Jet>?): ArrayList<Jet> {

    val coeffAtk = 1 + atkStat(fighter.stats.atk.general)
    val coeffDef = 1 - defStat(target.stats.def.general)
    val damageDealt = ArrayList<Jet>()

    if (jets != null) {
        for ((t, e, d) in jets) {

            // Offensive formula
            val attackT = fighter.stats.atk.types[t] ?: 0
            val attackE = fighter.stats.atk.elements[e] ?: 0

            val Ai = coeffAtk * max(0, d) *
                    (1 + atkStat(attackT)) *
                    (1 + atkStat(attackE))

            // Defensive formula
            val defT = fighter.stats.def.types[t] ?: 0
            val defE = fighter.stats.def.elements[e] ?: 0

            val Di = coeffDef * max(0.0, Ai) *
                    (1 - defStat(defT)) *
                    (1 - defStat(defE))
            damageDealt.add(Jet(t, e, floor(Di).toInt()))
        }
    }

    return damageDealt
}