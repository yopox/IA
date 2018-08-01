package com.llgames.ia.logic

import com.llgames.ia.battle.hpLost
import com.llgames.ia.states.BattleState

/**
 * [IAHandler] s'occupe de la partie logique des actions.
 */
interface IAHandler {

    /**
     * Fonction appelée au début du tour d'un joueur.
     */
    fun play(fighters: Array<out LFighter>, state: BattleState) {

        // Le perso qui joue ne protège plus personne
        fighters[state.charTurn].startTurn(fighters)

        // Règle d'IA de ce tour
        val rule = fighters[state.charTurn].getRule(fighters, state)

        // On récupère l'indice de la rune action
        var actionIndex = 0
        while (rule[actionIndex].type != RT.ACTION) actionIndex++

        // S'il y a une [RuneTarget] derrière la rune action, on la récupère
        val target = if (actionIndex == rule.lastIndex) null else rule[actionIndex + 1] as RuneTarget

        // Action du tour
        when (rule[actionIndex].id) {
            "DEF" -> def(fighters, state)
            "WPN" -> wpn(fighters, state, target!!.getTarget(fighters, state))
            "SPL" -> spl(fighters, state, target!!.getTarget(fighters, state))
            "PRO" -> pro(fighters, state, target!!.getTarget(fighters, state))
            "WRM" -> wrm(fighters, state, target!!.getTarget(fighters, state))
            "ATK" -> atk(fighters, state, target!!.getTarget(fighters, state))
            else -> wait(fighters, state)
        }

        // MAJ des stats du personnage
        fighters[state.charTurn].endTurn()

    }

    fun atk(fighters: Array<out LFighter>, state: BattleState, target: LFighter?) {

        if (target == null) {
            notarget(fighters[state.charTurn])
        } else {

            val actor = fighters[state.charTurn]
            val weapon = actor.weapon ?: LFighter.DEFAULT_WEAPON

            // On récupère la vraie cible
            val rtarget = target.protected ?: target

            // On applique les dommages
            actor.attack(rtarget, weapon.jets)

            // Log des dommages
            damage(actor, rtarget, hpLost(damageCalculation(actor, rtarget, weapon.jets)))

            // On applique les buffs
            weapon.boosts?.let {
                for (buff in it) {
                    val buffTarget = if (buff.onSelf) actor else rtarget
                    buff.apply(buffTarget)
                }
            }

            // Mort
            if (rtarget.stats.hp <= 0) {
                rtarget.kill(fighters)
                dies(rtarget)
            }

        }
    }

    fun pro(fighters: Array<out LFighter>, state: BattleState, target: LFighter?) {
        target?.let { it.protected = fighters[state.charTurn] }
    }

    fun wait(fighters: Array<out LFighter>, state: BattleState)

    fun def(fighters: Array<out LFighter>, state: BattleState) {
        val actor = fighters[state.charTurn]
        actor.stats.def.general += 50
    }

    fun wrm(fighters: Array<out LFighter>, state: BattleState, target: LFighter?) {
        val actor = fighters[state.charTurn]
        actor.stats.def.general += 20
        actor.stats.atk.general += 20
    }

    fun wpn(fighters: Array<out LFighter>, state: BattleState, target: LFighter?)

    fun spl(fighters: Array<out LFighter>, state: BattleState, target: LFighter?) {

        if (target == null) {
            notarget(fighters[state.charTurn])
        } else {

            val actor = fighters[state.charTurn]
            val spell = fighters[state.charTurn].spell ?: LFighter.DEFAULT_SPELL

            // On applique les dommages
            actor.attack(target, spell.jets)

            // Log des dommages
            damage(actor, target, hpLost(damageCalculation(actor, target, spell.jets)))

            // On applique les buffs
            spell.boosts?.let {
                for (buff in it) {
                    val buffTarget = if (buff.onSelf) actor else target
                    buff.apply(buffTarget)
                }
            }

            // Mort
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