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
            "SPL1" -> spl(fighters, state, target!!.getTarget(fighters, state), 1)
            "SPL2" -> spl(fighters, state, target!!.getTarget(fighters, state), 2)
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
                    val buffTarget = if (buff.second) actor else target
                    buff.first.apply(buffTarget)
                    buffTarget.boosts.add(buff.first)
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
        actor.boosts.add(Boost({ it.stats.def.general += 50 }, 1))
    }

    fun wrm(fighters: Array<out LFighter>, state: BattleState, target: LFighter?) {
        val actor = fighters[state.charTurn]
        actor.stats.def.general += 20
        actor.stats.atk.general += 20
    }

    fun spl(fighters: Array<out LFighter>, state: BattleState, target: LFighter?, nSpl: Int) {

        if (target == null) {
            notarget(fighters[state.charTurn])
        } else {

            val actor = fighters[state.charTurn]
            val spell = when (nSpl) {
                1 -> actor.spell1 ?: LFighter.DEFAULT_SPELL
                else -> actor.spell2 ?: LFighter.DEFAULT_SPELL
            }

            // On applique les dommages
            if (spell.jets.isNotEmpty()) {
                actor.attack(target, spell.jets)
                // Log des dommages
                damage(actor, target, hpLost(damageCalculation(actor, target, spell.jets)))
            }

            // On applique les buffs
            spell.boosts?.let {
                for (buff in it) {
                    val buffTarget = if (buff.second) actor else target
                    buff.first.apply(buffTarget)
                    buffTarget.boosts.add(buff.first)
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