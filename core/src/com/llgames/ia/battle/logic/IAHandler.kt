package com.llgames.ia.battle.logic

import com.llgames.ia.battle.State
import com.llgames.ia.battle.hpLost

/**
 * [IAHandler] s'occupe de la partie logique des actions.
 */
interface IAHandler {

    /**
     * Fonction appelée au début du tour d'un joueur.
     */
    fun play(fighters: Array<out LFighter>, state: State) {

        // Le perso qui joue ne protège plus personne
        fighters[state.charTurn].startTurn(fighters)

        // Règle d'IA de ce tour
        val rule = fighters[state.charTurn].getRule(fighters, state)

        when (rule.act.id) {
            "DEF" -> def(fighters, state)
            "WPN" -> wpn(fighters, state, getTarget(rule.act.target, fighters, state))
            "SPL" -> spl(fighters, state, getTarget(rule.act.target, fighters, state), rule.act.spell)
            "PRO" -> pro(fighters, state, getTarget(rule.act.target, fighters, state))
            "WRM" -> wrm(fighters, state, getTarget(rule.act.target, fighters, state))
            "ATK" -> atk(fighters, state, getTarget(rule.act.target, fighters, state), rule.act.weapon)
            else -> wait(fighters, state)
        }

        // MAJ des stats du personnage
        fighters[state.charTurn].endTurn()

    }

    fun atk(fighters: Array<out LFighter>, state: State, target: LFighter?, weapon: Weapon?) {

        if (target == null) {
            notarget(fighters[state.charTurn])
        } else {

            val actor = fighters[state.charTurn]

            // On récupère la vraie cible
            val rtarget = target.protected ?: target

            // On applique les dommages
            actor.attack(rtarget, weapon?.jets)

            // Log des dommages
            damage(actor, rtarget, hpLost(damageCalculation(actor, rtarget, weapon?.jets)))

            // On applique les buffs
            weapon?.boosts?.let {
                for (buff in it) {
                    val buffTarget = if (buff.onSelf) actor else rtarget
                    applyBoost(buff, actor, buffTarget)
                }
            }

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
        val actor = fighters[state.charTurn]
        applyBoost(Boost(Stats.DEFENSE, Stats.GENERAL, 50, 1), actor, actor)
    }

    fun wrm(fighters: Array<out LFighter>, state: State, target: LFighter?) {
        val actor = fighters[state.charTurn]
        applyBoost(Boost(Stats.ATTACK, Stats.GENERAL, 20, 1), actor, actor)
        applyBoost(Boost(Stats.DEFENSE, Stats.GENERAL, 20, 1), actor, actor)
    }

    fun wpn(fighters: Array<out LFighter>, state: State, target: LFighter?)

    fun spl(fighters: Array<out LFighter>, state: State, target: LFighter?, spell: Spell?) {

        if (target == null) {
            notarget(fighters[state.charTurn])
        } else {

            val actor = fighters[state.charTurn]

            // On applique les dommages
            actor.attack(target, spell?.jets)

            // Log des dommages
            damage(actor, target, hpLost(damageCalculation(actor, target, spell?.jets)))

            // On applique les buffs
            spell?.boosts?.let {
                for (buff in it) {
                    val buffTarget = if (buff.onSelf) actor else target
                    applyBoost(buff, actor, buffTarget)
                }
            }

            if (target.stats.hp <= 0) {
                target.kill(fighters)
                dies(target)
            }

        }

    }

    fun damage(actor: LFighter, target: LFighter, amount: String)

    fun dies(actor: LFighter)

    fun notarget(actor: LFighter)

}

/**
 * Applique un boost et programme la fin du boost.
 */
fun applyBoost(boost: Boost, actor: LFighter, target: LFighter) {
    target.applyBoost(boost)
    actor.boosts.add(Pair(boost.copy(value = -boost.value), target))
}