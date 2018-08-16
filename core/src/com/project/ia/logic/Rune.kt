package com.project.ia.logic

import com.project.ia.def.Runes
import kotlin.math.max

/**
 * Différents types de runes.
 */
enum class RT {
    GATE, CONDITION, VALUE, TARGET, ACTION, BLANK, ERROR
}

/**
 * L'objet [Rune] définit formellement une rune.
 * @param id : nom de la rune
 * @param type : type de la rune selon l'enum [RT]
 * @param next : runes attendues à sa suite dans l'ordre
 */
open class Rune(val id: String, val type: RT, val next: Array<RT> = arrayOf()) {

    override fun toString(): String = id

}

class RuneTarget(id: String, var carac: (LFighter) -> Int = { it.stats.hp })
    : Rune(id, RT.TARGET, arrayOf()) {

    /**
     * Renvoie le [LFighter] associé à la cible.
     */
    fun getTarget(fighters: Array<out LFighter>, state: State): LFighter? {
        val alive = fighters.filter { it.alive }
        val default = fighters[state.charTurn]
        return when (id.slice(0..1)) {
            "aM" -> alive.maxBy { carac(it) } ?: default
            "aL" -> alive.minBy { carac(it) } ?: default
            "AM" -> alive.filter { it.team == fighters[state.charTurn].team }.maxBy { carac(it) } ?: default
            "AL" -> alive.filter { it.team == fighters[state.charTurn].team }.minBy { carac(it) } ?: default
            "EM" -> alive.filter { it.team != fighters[state.charTurn].team }.maxBy { carac(it) } ?: default
            "EL" -> alive.filter { it.team != fighters[state.charTurn].team }.minBy { carac(it) } ?: default
            else -> fighters[state.charTurn]
        }
    }
}

/**
 * Comportement des runes.
 */
object RUNE_LOGIC {

    /**
     * Teste une porte logique.
     *
     * Pour les portes logiques à deux conditions, on appelle [condCheck] en coupant [rule]
     * après la première condition. [condCheck] s'occupe de trouver la condition.
     */
    fun gateCheck(rule: Array<Rune>, fighters: Array<out LFighter>, state: State): Boolean {

        return when (rule[0].id) {
            "ID" -> condCheck(rule, fighters, state)
            "NOT" -> !condCheck(rule, fighters, state)
            "AND" -> condCheck(rule, fighters, state) and condCheck(rule.copyOfRange(2, rule.lastIndex), fighters, state)
            "OR" -> condCheck(rule, fighters, state) or condCheck(rule.copyOfRange(2, rule.lastIndex), fighters, state)
            "XOR" -> condCheck(rule, fighters, state) xor condCheck(rule.copyOfRange(2, rule.lastIndex), fighters, state)
            "NAND" -> !(condCheck(rule, fighters, state) and condCheck(rule.copyOfRange(2, rule.lastIndex), fighters, state))
            "NOR" -> !(condCheck(rule, fighters, state) or condCheck(rule.copyOfRange(2, rule.lastIndex), fighters, state))
            "NXOR" -> !(condCheck(rule, fighters, state) xor condCheck(rule.copyOfRange(2, rule.lastIndex), fighters, state))
            else -> false
        }
    }

    /**
     * Vérifie une condition.
     */
    private fun condCheck(rule: Array<Rune>, fighters: Array<out LFighter>, state: State): Boolean {

        // Il n'est pas sûr que rule[0] soit une rune de type RT.CONDITION
        var condIndex = 0
        while (rule[condIndex].type != RT.CONDITION)
            condIndex++

        val value = if (RT.VALUE in rule[condIndex].next) rule[condIndex + 1].id.toInt() else 0

        return when (rule[condIndex].id) {
        // Conditions simples
            "ONCE" -> !fighters[state.charTurn].onceUsed

        // Conditions avec valeur
            "EXT" -> state.turn % max(value, 1) == 0
            "TX" -> state.turn == value
            "T>X" -> state.turn > value

        // Conditions avec une cible et une valeur
        // La conversion suivante marche car l'ordre est toujours COND VALUE TARGET
            "MXHP" -> (rule[condIndex + 2] as RuneTarget)
                    .getTarget(fighters, state) getPercent "HP" >= value
            "LXHP" -> (rule[condIndex + 2] as RuneTarget)
                    .getTarget(fighters, state) getPercent "HP" <= value

            else -> false
        }
    }
}
