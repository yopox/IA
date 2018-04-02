package com.llgames.ia.Util

/**
 * Created by yopox on 26/11/2017.
 */

class IA {

    data class LogicG(var id: String = "ID", var c1: Condition, var c2: Condition? = null)

    data class Rule(var gate: LogicG, var act: Action)

    data class Condition(var id: String = "E1T", var target: String = "", var value: Int = -1)

    data class Action(var id: String = "WAIT", var target: String = "")

    private val DEFAULT_RULE = Rule(
            LogicG(id = "ID", c1 = Condition(id = "E1T")),
            Action()
    )

    private var rules = arrayOf(
            Rule(
                    LogicG(id = "NOT", c1 = Condition(id = "EXT", value = 2)),
                    Action(id = "DEF")
            ),
            Rule(
                    LogicG(id = "ID", c1 = Condition(id = "E1T")),
                    Action()
            ))

    fun getRule(chars: Array<Perso>, state: State): Rule {
        return iaStep(0, chars, state)
    }

    private fun iaStep(index: Int, chars: Array<Perso>, state: State): Rule {
        // Default rule
        if (index == rules.size)
            return DEFAULT_RULE

        return if (gateCheck(rules[index].gate, chars, state)) {
            rules[index]
        } else {
            iaStep(index + 1, chars, state)
        }
    }

    private fun gateCheck(gate: LogicG, chars: Array<Perso>, state: State): Boolean {
        return when (gate.id) {
            "ID" -> condCheck(gate.c1, chars, state)
            "NOT" -> !condCheck(gate.c1, chars, state)
            "AND" -> condCheck(gate.c1, chars, state) and condCheck(gate.c2, chars, state)
            "OR" -> condCheck(gate.c1, chars, state) or condCheck(gate.c2, chars, state)
            "XOR" -> condCheck(gate.c1, chars, state) xor condCheck(gate.c2, chars, state)
            "NAND" -> !(condCheck(gate.c1, chars, state) and condCheck(gate.c2, chars, state))
            "NOR" -> !(condCheck(gate.c1, chars, state) or condCheck(gate.c2, chars, state))
            "NXOR" -> !(condCheck(gate.c1, chars, state) xor condCheck(gate.c2, chars, state))
            else -> false
        }
    }

    private fun condCheck(cond: Condition?, chars: Array<Perso>, state: State): Boolean {
        return when (cond?.id) {
            "E1T" -> true
            "EXT" -> state.turn.rem(cond.value) == 0
            "MXHP" -> getTarget(cond.target, chars, state) getPourcent "HP" >= cond.value
            "LXHP" -> getTarget(cond.target, chars, state) getPourcent "HP" <= cond.value
            "MXMP" -> getTarget(cond.target, chars, state) getPourcent "MP" >= cond.value
            "LXMP" -> getTarget(cond.target, chars, state) getPourcent "MP" <= cond.value
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

fun getTarget(target: String, chars: Array<Perso>, state: State): Perso {
    return when (target) {
    // HP MAX
        "aMHPM" -> chars.sortedWith(compareBy { -it.maxStats.hp }).first()
        "aLHPM" -> chars.sortedWith(compareBy { it.maxStats.hp }).first()
        "AMHPM" -> chars.filter { it.team == chars[state.charTurn].team }.sortedWith(compareBy { -it.maxStats.hp }).first()
        "ALHPM" -> chars.filter { it.team == chars[state.charTurn].team }.sortedWith(compareBy { it.maxStats.hp }).first()
        "EMHPM" -> chars.filter { it.team != chars[state.charTurn].team }.sortedWith(compareBy { -it.maxStats.hp }).first()
        "ELHPM" -> chars.filter { it.team != chars[state.charTurn].team }.sortedWith(compareBy { it.maxStats.hp }).first()
    // HP LEFT
        "aMHP" -> chars.sortedWith(compareBy { -it.stats.hp }).first()
        "aLHP" -> chars.sortedWith(compareBy { it.stats.hp }).first()
        "AMHP" -> chars.filter { it.team == chars[state.charTurn].team }.sortedWith(compareBy { -it.stats.hp }).first()
        "ALHP" -> chars.filter { it.team == chars[state.charTurn].team }.sortedWith(compareBy { it.stats.hp }).first()
        "EMHP" -> chars.filter { it.team != chars[state.charTurn].team }.sortedWith(compareBy { -it.stats.hp }).first()
        "ELHP" -> chars.filter { it.team != chars[state.charTurn].team }.sortedWith(compareBy { it.stats.hp }).first()
    // DIRECT
        "SELF" -> chars[state.charTurn]
        "ALLY" -> chars.filter { it.team == chars[state.charTurn].team }.shuffled().first()
        "ENN" -> chars.filter { it.team != chars[state.charTurn].team }.shuffled().first()
        else -> chars[state.charTurn]
    }
}