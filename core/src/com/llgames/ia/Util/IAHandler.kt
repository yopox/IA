package com.llgames.ia.Util

/**
 * Created by yopox on 3/23/18.
 */
interface IAHandler {

    fun newTurn(chars: Array<Fighter>, state: State) {

        // Get the rule
        val rule = chars[state.charTurn].getRule(chars, state)

        when (rule.act.id) {
            "DEF" -> def(chars, state)
            "WPN" -> wpn(chars, state, getTarget(rule.act.target, chars, state))
            "SPL" -> spl(chars, state, getTarget(rule.act.target, chars, state))
            "PRO" -> pro(chars, state, getTarget(rule.act.target, chars, state))
            else -> wait(chars, state)
        }

    }

    fun pro(fighters: Array<Fighter>, state: State, target: Fighter)

    fun wait(fighters: Array<Fighter>, state: State)

    fun def(fighters: Array<Fighter>, state: State)

    fun wpn(fighters: Array<Fighter>, state: State, target: Fighter)

    fun spl(fighters: Array<Fighter>, state: State, target: Fighter)

}