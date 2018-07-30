package com.llgames.ia.logic

import com.llgames.ia.battle.State
import com.llgames.ia.def.Runes

/**
 * On ne travaille qu'avec des [LFighter]
 * Gère les runes et l'intelligence artificielle des combattants.
 *
 * Une règle d'IA est de type Array<[Rune]>.
 * Il y a forcément au moins, dans cet ordre, une porte logique, une condition et une action.
 * Une [Rune] peut spécifier les RunesTypes ([RT]) attendus à sa suite.
 */

class IA {

    private var rules: MutableList<Array<Rune>> = mutableListOf(DEFAULT_RULE)

    companion object {
        private val DEFAULT_RULE = Runes.fromString("ID E1T WAIT")
    }

    override fun toString(): String {
        var str = ""
        rules.map { str += Runes.toString(it) + "\n" }
        return str
    }

    fun serialize(): String {
        var str = ""
        rules.map { str += Runes.toString(it) + " - " }
        return str.dropLast(1)
    }

    fun changeIA(rules: String) {
        this.rules = rules.split(" - ").map { Runes.fromString(it) }.toMutableList()
    }

    /**
     * Renvoie la règle utilisée ce tour-ci.
     */
    fun getRule(fighters: Array<out LFighter>, state: State): Array<Rune> {
        return iaStep(0, fighters, state)
    }

    /**
     * Interroge la porte logique de la règle numéro [index].
     * @return la première règle qui s'applique
     */
    private fun iaStep(index: Int, fighters: Array<out LFighter>, state: State): Array<Rune> {

        // Règle par défaut
        if (index == rules.size)
            return DEFAULT_RULE

        // On teste la porte logique
        return if (gateCheck(rules[index], fighters, state)) {
            rules[index]
        } else {
            iaStep(index + 1, fighters, state)
        }
    }

    /**
     * État de la porte logique.
     */
    private fun gateCheck(rule: Array<Rune>, fighters: Array<out LFighter>, state: State): Boolean {

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
     * État de la condition.
     */
    private fun condCheck(rule: Array<Rune>, fighters: Array<out LFighter>, state: State): Boolean {

        // Il n'est pas sûr que rule[0] soit une rune de type RT.CONDITION
        var condIndex = 0
        while (rule[condIndex].type != RT.CONDITION)
            condIndex++

        val value = if (RT.VALUE in rule[condIndex].next) rule[condIndex + 1].id.toInt() else 0

        return when (rule[condIndex].id) {
            "E1T" -> true
            "EXT" -> state.turn % value == 0
            "MXHP" -> (rule[condIndex + 2] as RuneTarget)
                    .getTarget(fighters, state)!! getPercent "HP" >= value
            "LXHP" -> (rule[condIndex + 2] as RuneTarget)
                    .getTarget(fighters, state)!! getPercent "HP" <= value
            else -> false
        }
    }

}