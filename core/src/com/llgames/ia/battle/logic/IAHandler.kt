package com.llgames.ia.battle.logic

import com.llgames.ia.battle.State
import com.llgames.ia.battle.hpLost

/**
 * [IAHandler] s'occupe de la partie logique des actions.
 * TODO: Trouver une meilleure façon de
 */
interface IAHandler {

    /**
     * Fonction appelée au début du tour d'un joueur.
     */
    fun play(fighters: Array<out LFighter>, state: State) {

        // MAJ des stats du personnage
        fighters[state.charTurn].newTurn()

        // Règle d'IA de ce tour
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

        if (target == null) {
            notarget(fighters[state.charTurn])
        } else {

            val actor = fighters[state.charTurn]

            // On récupère la vraie cible
            var rtarget = target.protected ?: target
            actor.attack(rtarget, weapon)
            damage(actor, target, hpLost(damageCalculation(actor, target, weapon)))

            if (rtarget.stats.hp <= 0) {
                rtarget.kill(fighters)
                dies(rtarget)
            }

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

    fun damage(actor: LFighter, target: LFighter, amount: String)

    fun dies(actor: LFighter)

    fun notarget(actor: LFighter)

}