package com.llgames.ia.battle.logic

import com.llgames.ia.battle.Fighter
import com.llgames.ia.battle.State

/**
 * Created by yopox on 26/11/2017.
 */

class IA {

    // RULES

    data class LogicG(var id: String = "ID", var c1: Condition, var c2: Condition? = null)

    data class Rule(var gate: LogicG, var act: Action)

    data class Condition(var id: String = "E1T", var target: String = "", var value: Int = -1)

    data class Action(var id: String = "WAIT", var target: String = "", var weapon: Weapon? = null)

    private val DEFAULT_RULE = Rule(
            LogicG(id = "ID", c1 = Condition(id = "E1T")),
            Action()
    )

    private var rules = arrayOf(
            Rule(
                    LogicG(id = "ID", c1 = Condition(id = "E1T")),
                    Action(id = "ATK", target = "ELHP", weapon = Weapon(arrayOf(Jet("blade", "neutral", 10))))
            ))

    fun getRule(fighters: Array<Fighter>, state: State): Rule {
        return iaStep(0, fighters, state)
    }

    private fun iaStep(index: Int, fighters: Array<Fighter>, state: State): Rule {
        // Default rule
        if (index == rules.size)
            return DEFAULT_RULE

        return if (gateCheck(rules[index].gate, fighters, state)) {
            rules[index]
        } else {
            iaStep(index + 1, fighters, state)
        }
    }

    private fun gateCheck(gate: LogicG, fighters: Array<Fighter>, state: State): Boolean {
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

    private fun condCheck(cond: Condition?, fighters: Array<Fighter>, state: State): Boolean {
        return when (cond?.id) {
            "E1T" -> true
            "EXT" -> state.turn % cond.value == 0
            "MXHP" -> getTarget(cond.target, fighters, state) getPercent "HP" >= cond.value
            "LXHP" -> getTarget(cond.target, fighters, state) getPercent "HP" <= cond.value
            "MXMP" -> getTarget(cond.target, fighters, state) getPercent "MP" >= cond.value
            "LXMP" -> getTarget(cond.target, fighters, state) getPercent "MP" <= cond.value
            else -> false
        }
    }

    private fun toString(cond: Condition): String {
        var str = ""
        str += "[" + cond.id
        if (cond.target != "") str += " " + cond.target
        if (cond.value != -1) str += " " + cond.value
        str += "]"
        return str
    }

    private fun toString(act: Action): String {
        var str = ""
        str += "[" + act.id
        if (act.target != "") str += " " + act.target
        str += "]"
        return str
    }

    override fun toString(): String {
        var str = ""
        for (rule in rules) {
            // GATE
            str += rule.gate.id
            // COND
            str += " " + toString(rule.gate.c1)
            rule.gate.c2?.let { str += " " + toString(it) }
            str += " " + toString(rule.act)
            str += "\n"
        }
        return str
    }

}

fun getTarget(target: String, fighters: Array<Fighter>, state: State): Fighter {
    return when (target) {
    // HP MAX
        "aMHPM" -> fighters.sortedWith(compareBy { -it.maxStats.hp }).first()
        "aLHPM" -> fighters.sortedWith(compareBy { it.maxStats.hp }).first()
        "AMHPM" -> fighters.filter { it.team == fighters[state.charTurn].team }.sortedWith(compareBy { -it.maxStats.hp }).first()
        "ALHPM" -> fighters.filter { it.team == fighters[state.charTurn].team }.sortedWith(compareBy { it.maxStats.hp }).first()
        "EMHPM" -> fighters.filter { it.team != fighters[state.charTurn].team }.sortedWith(compareBy { -it.maxStats.hp }).first()
        "ELHPM" -> fighters.filter { it.team != fighters[state.charTurn].team }.sortedWith(compareBy { it.maxStats.hp }).first()
    // HP LEFT
        "aMHP" -> fighters.sortedWith(compareBy { -it.stats.hp }).first()
        "aLHP" -> fighters.sortedWith(compareBy { it.stats.hp }).first()
        "AMHP" -> fighters.filter { it.team == fighters[state.charTurn].team }.sortedWith(compareBy { -it.stats.hp }).first()
        "ALHP" -> fighters.filter { it.team == fighters[state.charTurn].team }.sortedWith(compareBy { it.stats.hp }).first()
        "EMHP" -> fighters.filter { it.team != fighters[state.charTurn].team }.sortedWith(compareBy { -it.stats.hp }).first()
        "ELHP" -> fighters.filter { it.team != fighters[state.charTurn].team }.sortedWith(compareBy { it.stats.hp }).first()
    // DIRECT
        "SELF" -> fighters[state.charTurn]
        "ALLY" -> fighters.filter { it.team == fighters[state.charTurn].team }.shuffled().first()
        "ENN" -> fighters.filter { it.team != fighters[state.charTurn].team }.shuffled().first()
        else -> fighters[state.charTurn]
    }
}