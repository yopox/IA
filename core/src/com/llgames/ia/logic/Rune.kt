package com.llgames.ia.logic

import com.llgames.ia.battle.State

/**
 * Différents types de runes.
 */
enum class RT {
    GATE, CONDITION, VALUE, TARGET, ACTION, ERROR
}

/**
 * Définition d'une rune.
 * [id] : nom de la rune
 * [type] : type de la rune selon l'enum [IA.RT]
 * [next] : runes attendues à sa suite dans l'ordre
 */
open class Rune(val id: String, val type: RT, val next: Array<RT>) {

    override fun toString(): String = id

}

class RuneTarget(id: String, var carac: (LFighter) -> Int = { it.stats.hp })
    : Rune(id, RT.TARGET, arrayOf()) {

    /**
     * Renvoie le [LFighter] associé à la cible.
     */
    fun getTarget(fighters: Array<out LFighter>, state: State): LFighter? {
        val alive = fighters.filter { it.alive }
        return when (id.slice(0..1)) {
            "aM" -> alive.maxBy { carac(it) }
            "aL" -> alive.minBy { carac(it) }
            "AM" -> alive.filter { it.team == fighters[state.charTurn].team }.maxBy { carac(it) }
            "AL" -> alive.filter { it.team == fighters[state.charTurn].team }.minBy { carac(it) }
            "EM" -> alive.filter { it.team != fighters[state.charTurn].team }.maxBy { carac(it) }
            "EL" -> alive.filter { it.team != fighters[state.charTurn].team }.minBy { carac(it) }
            else -> fighters[state.charTurn]
        }
    }

}