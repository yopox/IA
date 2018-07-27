package com.llgames.ia.logic

import com.llgames.ia.battle.State
import com.llgames.ia.def.IA_TEST

/**
 * **LOGIC** : On ne travaille qu'avec des [LFighter]
 * Gère les runes et l'intelligence artificielle des combattants.
 */

class IA {

    data class LogicG(var id: String = "ID", var c1: Condition, var c2: Condition? = null)

    data class Rule(var gate: LogicG, var act: Action)

    data class Target(var main: String? = null, var carac: (LFighter) -> Int = { it.stats.hp })

    data class Condition(var id: String = "E1T", var target: Target? = null, var value: Int = -1)

    data class Action(var id: String = "WAIT", var target: Target? = null, var weapon: Weapon? = null, var spell: Spell? = null)

    private var rules: Array<Rule> = arrayOf(DEFAULT_RULE)

    fun setRules() {
        val possible = arrayListOf(IA_TEST.leecher, IA_TEST.noBrain, IA_TEST.prudent, IA_TEST.tank)
        rules = possible.shuffled().first()
    }

    /**
     * Renvoie la règle utilisée ce tour-ci.
     */
    fun getRule(fighters: Array<out LFighter>, state: State): Rule {
        return iaStep(0, fighters, state)
    }

    /**
     * Interroge la porte logique de la règle numéro `index`.
     * @return la première règle qui s'applique
     */
    private fun iaStep(index: Int, fighters: Array<out LFighter>, state: State): Rule {
        // Default rule
        if (index == rules.size)
            return DEFAULT_RULE

        return if (gateCheck(rules[index].gate, fighters, state)) {
            rules[index]
        } else {
            iaStep(index + 1, fighters, state)
        }
    }

    /**
     * État de la porte logique.
     */
    private fun gateCheck(gate: LogicG, fighters: Array<out LFighter>, state: State): Boolean {
        return when (gate.id) {
            "ID" -> condCheck(gate.c1, fighters, state)
            "NOT" -> !condCheck(gate.c1, fighters, state)
            "AND" -> condCheck(gate.c1, fighters, state) and condCheck(gate.c2, fighters, state)
            "OR" -> condCheck(gate.c1, fighters, state) or condCheck(gate.c2, fighters, state)
            "XOR" -> condCheck(gate.c1, fighters, state) xor condCheck(gate.c2, fighters, state)
            "NAND" -> !(condCheck(gate.c1, fighters, state) and condCheck(gate.c2, fighters, state))
            "NOR" -> !(condCheck(gate.c1, fighters, state) or condCheck(gate.c2, fighters, state))
            "NXOR" -> !(condCheck(gate.c1, fighters, state) xor condCheck(gate.c2, fighters, state))
            else -> false
        }
    }

    /**
     * État de la condition.
     */
    private fun condCheck(cond: Condition?, fighters: Array<out LFighter>, state: State): Boolean {
        return when (cond?.id) {
            "E1T" -> true
            "EXT" -> state.turn % cond.value == 0
            "MXHP" -> getTarget(cond.target, fighters, state)!! getPercent "HP" >= cond.value
            "LXHP" -> getTarget(cond.target, fighters, state)!! getPercent "HP" <= cond.value
            else -> false
        }
    }

    fun toString(cond: Condition): String {
        var str = ""
        str += cond.id
        cond.target?.let { str += "-${it.main}" }
        if (cond.value != -1) str += "-${cond.value}"
        return str
    }

    fun toString(act: Action): String {
        var str = ""
        str += act.id
        act.target?.let { str += "-${it.main}" }
        return str
    }

    override fun toString(): String {
        var str = ""
        for (rule in rules) {
            // GATE
            str += rule.gate.id
            // COND
            str += "-${toString(rule.gate.c1)}"
            rule.gate.c2?.let { str += "-${toString(it)}" }
            str += "-${toString(rule.act)}"
        }
        return str
    }

    companion object {
        private val DEFAULT_RULE = Rule(
                LogicG(id = "ID", c1 = Condition(id = "E1T")),
                Action(id = "WAIT")
        )
    }

}

/**
 * Renvoie le [LFighter] associé à la cible [IA.Target].
 */
fun getTarget(target: IA.Target?, fighters: Array<out LFighter>, state: State): LFighter? {
    target?.let {
        val alive = fighters.filter { it.alive }
        return when (target.main?.slice(0..1)) {
            "aM" -> alive.maxBy { target.carac(it) }
            "aL" -> alive.minBy { target.carac(it) }
            "AM" -> alive.filter { it.team == fighters[state.charTurn].team }.maxBy { target.carac(it) }
            "AL" -> alive.filter { it.team == fighters[state.charTurn].team }.minBy { target.carac(it) }
            "EM" -> alive.filter { it.team != fighters[state.charTurn].team }.maxBy { target.carac(it) }
            "EL" -> alive.filter { it.team != fighters[state.charTurn].team }.minBy { target.carac(it) }
            else -> fighters[state.charTurn]
        }
    }
    return null
}