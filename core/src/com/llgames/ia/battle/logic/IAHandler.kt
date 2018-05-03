package com.llgames.ia.battle.logic

import com.llgames.ia.battle.Fighter
import com.llgames.ia.battle.State

/**
 * [IAHandler] s'occupe de la partie logique des actions.
 */
interface IAHandler {

    fun play(fighters: Array<out LFighter>, state: State) {

        fighters[state.charTurn].newTurn()

        // Get the rule
        val rule = fighters[state.charTurn].getRule(fighters, state)

        when (rule.act.id) {
            "DEF" -> def(fighters, state)
            "WPN" -> wpn(fighters, state, getTarget(rule.act.target, fighters, state))
            "SPL" -> spl(fighters, state, getTarget(rule.act.target, fighters, state))
            "PRO" -> pro(fighters, state, getTarget(rule.act.target, fighters, state))
            "WRM" -> wrm(fighters, state, getTarget(rule.act.target, fighters, state))
            "ATK" -> atk(fighters, state, getTarget(rule.act.target, fighters, state), rule.act.weapon)
            else -> wait(fighters, state)
        }

    }

    fun atk(fighters: Array<out LFighter>, state: State, target: LFighter?, weapon: Weapon?) {
        target?.let {
            fighters[state.charTurn].attack(target.protected ?: target, weapon)
        }
    }

    fun pro(fighters: Array<out LFighter>, state: State, target: LFighter?) {
        target?.let { it.protected = fighters[state.charTurn] }
    }

    fun wait(fighters: Array<out LFighter>, state: State)

    fun def(fighters: Array<out LFighter>, state: State) {
        fighters[state.charTurn].defend()
    }

    fun wrm(fighters: Array<out LFighter>, state: State, target: LFighter?) {
        fighters[state.charTurn].boosts.add(Boost(Stats.ATTACK, Stats.GENERAL, 20, 1))
        fighters[state.charTurn].boosts.add(Boost(Stats.DEFENSE, Stats.GENERAL, 20, 1))
    }

    fun wpn(fighters: Array<out LFighter>, state: State, target: LFighter?)

    fun spl(fighters: Array<out LFighter>, state: State, target: LFighter?)

}