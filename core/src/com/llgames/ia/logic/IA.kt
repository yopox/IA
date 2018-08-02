package com.llgames.ia.logic

import com.llgames.ia.def.Runes
import com.llgames.ia.states.BattleState

/**
 * On ne travaille qu'avec des [LFighter], partie logique des combattants.
 * Gère les runes et l'intelligence artificielle des combattants.
 *
 * Une règle d'IA est de type Array<[Rune]>.
 * Il y a forcément au moins, dans cet ordre, une porte logique, une condition et une action.
 * Une [Rune] peut spécifier les types de runes ([RT]) attendus à sa suite.
 */

class IA {

    // Les règles d'IA
    private var rules: MutableList<Array<Rune>> = mutableListOf(DEFAULT_RULE)

    companion object {
        private val DEFAULT_RULE = Runes.fromString("ID EXT 1 WAIT")
    }

    /**
     * Renvoie une copie lisible des règles d'IA.
     */
    override fun toString(): String {
        var str = ""
        rules.map { str += Runes.toString(it) + " - " }
        return str.dropLast(3)
    }

    /**
     * Change les règles d'IA de l'objet.
     */
    fun changeIA(rules: String) {
        this.rules = rules.split(" - ").map { Runes.fromString(it) }.toMutableList()
    }

    /**
     * Renvoie la règle utilisée ce tour-ci.
     */
    fun getRule(fighters: Array<out LFighter>, state: BattleState): Array<Rune> {
        val rule = iaStep(0, fighters, state)
        // Si la rune ONCE a été utilisée
        if (rule.any { it.id == "ONCE" })
            fighters[state.charTurn].onceUsed = true
        return rule
    }

    /**
     * Fonction récursive qui renvoie la première règle qui s'applique.
     */
    private fun iaStep(index: Int, fighters: Array<out LFighter>, state: BattleState): Array<Rune> {

        // Cas de base
        if (index == rules.size)
            return DEFAULT_RULE

        // On teste la porte logique
        return if (RUNE_LOGIC.gateCheck(rules[index], fighters, state)) {
            rules[index]
        } else {
            iaStep(index + 1, fighters, state)
        }
    }

}