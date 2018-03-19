package com.llgames.ia.Util

/**
 * Created by yopox on 26/11/2017.
 */

class IA {

    data class LogicG(var id: String = "ID", var c1: Condition, var c2: Condition? = null)

    data class Rule(var gate: LogicG, var cdt: Condition, var act: Action)

    data class Condition(var id: String = "E1T", var target: String = "", var value: Int = 1)

    data class Action(var id: String = "WAIT", var target: String = "")

    private val DEFAULT_RULE = Rule(
                                        LogicG(id = "ID", c1 = Condition(id = "E1T")),
                                        Condition(),
                                        Action()
                                )


    private var rules = arrayOf(
                                Rule(
                                        LogicG(id = "ID", c1 = Condition(id = "EXT", value = 2)),
                                        Condition(),
                                        Action(id = "DEF")
                                ),
                                Rule(
                                        LogicG(id = "ID", c1 = Condition(id = "E1T")),
                                        Condition(),
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
            "ID" -> check(gate.c1, chars, state)
            "NOT" -> !check(gate.c1, chars, state)
            "AND" -> check(gate.c1, chars, state) and check(gate.c2, chars, state)
            "OR" -> check(gate.c1, chars, state) or check(gate.c2, chars, state)
            "XOR" -> check(gate.c1, chars, state) xor check(gate.c2, chars, state)
            "NAND" -> !(check(gate.c1, chars, state) and check(gate.c2, chars, state))
            "NOR" -> !(check(gate.c1, chars, state) or check(gate.c2, chars, state))
            "NXOR" -> !(check(gate.c1, chars, state) xor check(gate.c2, chars, state))
            else -> false
        }
    }

    private fun check(cond: Condition?, chars: Array<Perso>, state: State): Boolean {
        return when (cond?.id) {
            "E1T" -> true
            "EXT" -> state.turn.rem(cond.value) == 0
            "HP<X" -> chars[state.charTurn] getPourcent "HP" < cond.value
            "MP<X" -> chars[state.charTurn] getPourcent "MP" < cond.value
            else -> false
        }
    }

    override fun toString(): String {
        var str = ""
        for (rule in rules) {
            str += rule.toString() + "\n"
        }
        return str
    }

}