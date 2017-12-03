package com.llgames.ia.Util

/**
 * Created by yopox on 26/11/2017.
 */

data class LogicG(var id: String = "ID", var c1: String = "E1T", var c2: String = "", var value1: Int = 2, var value2: Int = 0)
data class Rule(var gate: LogicG = LogicG(), var action: String = "WAIT", var target: String = "SELF")

class IA {
    private var rules = arrayOf(Rule(action = "DEF"))

    fun getRule(chars: Array<Perso>, state: State): Rule {
        return iaStep(0, chars, state)
    }

    private fun iaStep(index: Int, chars: Array<Perso>, state: State): Rule {
        // Default rule
        if (index == rules.size)
            return Rule()
        
        if (gateCheck(rules[index].gate, chars, state)) {
            return rules[index]
        } else {
            return iaStep(index + 1, chars, state)
        }
    }

    private fun gateCheck(gate: LogicG, chars: Array<Perso>, state: State): Boolean {
        return when (gate.id) {
            "ID" -> check(gate.c1, gate.value1, chars, state)
            "NOT" -> ! check(gate.c1, gate.value1, chars, state)
            "AND" -> check(gate.c1, gate.value1, chars, state) and check(gate.c2, gate.value2, chars, state)
            "OR" -> check(gate.c1, gate.value1, chars, state) or check(gate.c2, gate.value2, chars, state)
            "XOR" -> check(gate.c1, gate.value1, chars, state) xor check(gate.c2, gate.value2, chars, state)
            "NAND" -> ! (check(gate.c1, gate.value1, chars, state) and check(gate.c2, gate.value2, chars, state))
            "NOR" -> ! (check(gate.c1, gate.value1, chars, state) or check(gate.c2, gate.value2, chars, state))
            "NXOR" -> ! (check(gate.c1, gate.value1, chars, state) xor check(gate.c2, gate.value2, chars, state))
            else -> false
        }
    }

    private fun check(cond: String, value: Int, chars: Array<Perso>, state: State): Boolean {
        return when (cond) {
            "E1T" -> true
            "EXT" -> state.turn % value == 0
            else -> false
        }
    }

}