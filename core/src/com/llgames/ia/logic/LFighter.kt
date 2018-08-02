package com.llgames.ia.logic

import com.llgames.ia.def.JOBS
import com.llgames.ia.states.BattleState
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

data class Jet(var type: TYPES, var elem: ELEMENTS, var damage: Int)

/**
 * Définition d'un [Boost].
 * @param [apply] est une fonction qui applique le boost voulu
 */
data class Boost(var apply: (LFighter) -> Unit, var duration: Int)

data class Weapon(var name: String, var jets: Array<Jet>, var boosts: Array<Pair<Boost, Boolean>>? = null)

data class Spell(var name: String, var jets: Array<Jet>, var boosts: Array<Pair<Boost, Boolean>>? = null)


/**
 * Partie logique des combattants.
 *
 * //TODO: équipements
 */
open class LFighter(var name: String, val team: Int = 0, val id: Int = 0) {
    private val ia = IA()
    var boosts: MutableList<Boost> = mutableListOf()
    var job = JOBS.getJob("HUMAN")
    var stats = Stats()
    var maxStats = Stats()
    var alive = true
    var protected: LFighter? = null
    var weapon: Weapon? = null
    var spell: Spell? = null
    var onceUsed = false

    companion object {
        val DEFAULT_WEAPON = Weapon("Fists", arrayOf(Jet(TYPES.PHYSICAL, ELEMENTS.NEUTRAL, 5)))
        val DEFAULT_SPELL = Spell("Pray +", arrayOf(), arrayOf(Pair(Boost({it.stats.def.general += 20}, 2), false)))
    }

    open fun changeJob(job: Job) {
        this.job = job
        this.maxStats = job.stats
    }

    fun changeIA(rules: String) = ia.changeIA(rules)

    /**
     * Renvoie la règle d'IA utilisée ce tour-ci.
     */
    fun getRule(fighters: Array<out LFighter>, state: BattleState): Array<Rune> = ia.getRule(fighters, state)

    /**
     * Reset les stats du personnage.
     */
    fun resetStats(setHp: Boolean = false) {
        stats.setTo(maxStats, setHp)
        // TODO: Tenir compte des bonus d'équipements
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

        for (boost in boosts) {
            boost.duration--
            if (boost.duration >= 0)
                this.applyBoost(boost)
        }

        val toRemove = mutableListOf<Int>()
        for (i in 0..boosts.lastIndex) {
            if (boosts[i].duration < 0)
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
        boost.apply(this)
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
            var bonusT = fighter.stats.atkB.types[t] ?: 0
            var bonusE = fighter.stats.atkB.elements[e] ?: 0
            val attackT = fighter.stats.atk.types[t] ?: 0
            val attackE = fighter.stats.atk.elements[e] ?: 0

            val Ai = coeffAtk * max(0, d + bonusT + bonusE) *
                    (1 + atkStat(attackT)) *
                    (1 + atkStat(attackE))

            // Defensive formula
            bonusT = fighter.stats.defB.types[t] ?: 0
            bonusE = fighter.stats.defB.elements[e] ?: 0
            val defT = fighter.stats.def.types[t] ?: 0
            val defE = fighter.stats.def.elements[e] ?: 0

            val Di = coeffDef * max(0.0, Ai - bonusT - bonusE) *
                    (1 - defStat(defT)) *
                    (1 - defStat(defE))
            damageDealt.add(Jet(t, e, floor(Di).toInt()))
        }
    }

    return damageDealt
}