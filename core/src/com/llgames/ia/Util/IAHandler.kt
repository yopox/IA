package com.llgames.ia.Util

/**
 * Created by yopox on 3/23/18.
 */
interface IAHandler {

    fun newTurn(chars: Array<Perso>, state: State) {

        // Get the rule
        val rule = chars[state.charTurn].getRule(chars, state)

        when (rule.act.id) {
            "DEF" -> def(chars, state)
            "WPN" -> wpn(chars, state)
            "SPL" -> spl(chars, state)
            else -> wait(chars, state)
        }

    }

    fun wait(chars: Array<Perso>, state: State)

    fun def(chars: Array<Perso>, state: State)

    fun wpn(chars: Array<Perso>, state: State)

    fun spl(chars: Array<Perso>, state: State)

}