package com.llgames.ia.battle.logic

import com.llgames.ia.battle.Fighter
import com.llgames.ia.battle.State

/**
 * Created by yopox on 3/23/18.
 */
interface IAHandler {

    fun newTurn(fighters: Array<Fighter>, state: State) {

        // Get the rule
        val rule = fighters[state.charTurn].getRule(fighters, state)

        when (rule.act.id) {
            "DEF" -> def(fighters, state)
            "WPN" -> wpn(fighters, state, getTarget(rule.act.target, fighters, state))
            "SPL" -> spl(fighters, state, getTarget(rule.act.target, fighters, state))
            "PRO" -> pro(fighters, state, getTarget(rule.act.target, fighters, state))
            "ATK" -> atk(fighters, state, getTarget(rule.act.target, fighters, state), rule.act.weapon)
            else -> wait(fighters, state)
        }

    }

    fun atk(fighters: Array<Fighter>, state: State, target: Fighter?, weapon: Weapon?) {
        target?.let { fighters[state.charTurn].attack(target, weapon) }
    }

    fun pro(fighters: Array<Fighter>, state: State, target: Fighter?)

    fun wait(fighters: Array<Fighter>, state: State)

    fun def(fighters: Array<Fighter>, state: State)

    fun wpn(fighters: Array<Fighter>, state: State, target: Fighter?)

    fun spl(fighters: Array<Fighter>, state: State, target: Fighter?)

}