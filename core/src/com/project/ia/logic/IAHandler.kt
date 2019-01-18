package com.project.ia.logic

import com.project.ia.battle.hpLost
import com.project.ia.def.Equip
import com.project.ia.def.General

open class State(var turn: Int = 1, var charTurn: Int = 0, var winner: Int = -1) {
    private fun newTurn() {
        charTurn = 0
        turn++
    }

    fun nextTurn(fighters: Array<out LFighter>, turnManager: IAHandler) {
        // On regarde si le combat est fini
        when {
            // Une équipe gagne
            fighters.none { it.team == 0 && it.alive } -> winner = 1
            fighters.none { it.team == 1 && it.alive } -> winner = 0

            // Combat trop long
            turn > General.MAX_TURNS -> winner = -2

            // Le combat continue
            else -> {
                // Tour du personnage suivant
                charTurn++

                // Tous les personnages ont joué
                if (charTurn == fighters.size) {
                    newTurn()
                    fighters.sortByDescending { it.stats.spd }
                }

                // Personnage mort
                if (!fighters[charTurn].alive)
                    fighters[charTurn].endTurn()
                else
                    turnManager.play(fighters, this)
            }
        }
    }

    open fun setActRule(rule: Array<Rune>, lFighter: LFighter) {}
}


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
        state.setActRule(rule, fighters[state.charTurn])

        // On récupère l'indice de la rune action
        var actionIndex = 0
        while (rule[actionIndex].type != RT.ACTION) actionIndex++

        // S'il y a une [RuneTarget] derrière la rune action, on la récupère
        val target = if (actionIndex == rule.lastIndex) null else rule[actionIndex + 1] as RuneTarget

        // Action du tour
        when (rule[actionIndex].id) {
            "SPL1" -> spl(fighters, state, target!!.getTarget(fighters, state), 1)
            "SPL2" -> spl(fighters, state, target!!.getTarget(fighters, state), 2)
            "PRO" -> pro(fighters, state, target!!.getTarget(fighters, state))
            "ATK" -> atk(fighters, state, target!!.getTarget(fighters, state))
            else -> wait(fighters, state)
        }

        // MAJ des STAT du personnage
        fighters[state.charTurn].endTurn()

    }

    fun atk(fighters: Array<out LFighter>, state: State, target: LFighter?) {

        if (target == null) {
            notarget(fighters[state.charTurn])
        } else {

            val actor = fighters[state.charTurn]
            val weapon = Equip.getWeapon(actor.weapon)

            // On récupère la vraie cible
            val rtarget = target.protected ?: target

            // On applique les dommages
            actor.attack(rtarget, weapon.jets)

            // Log des dommages
            damage(actor, rtarget, hpLost(damageCalculation(actor, rtarget, weapon.jets)))

            // Mort
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

    fun spl(fighters: Array<out LFighter>, state: State, target: LFighter?, nSpl: Int) {

        if (target == null) {
            notarget(fighters[state.charTurn])
        } else {

            val actor = fighters[state.charTurn]
            val spell = when (nSpl) {
                1 -> Equip.getSpell(actor.spell1)
                else -> Equip.getSpell(actor.spell2)
            }

            // On applique les dommages
            if (spell.jets.isNotEmpty()) {
                actor.attack(target, spell.jets)
                // Log des dommages
                damage(actor, target, hpLost(damageCalculation(actor, target, spell.jets)))
            }

            // On applique les buffs
            spell.boosts?.let {
                for (boost in it) {
                    val buffTarget = if (boost.onSelf) actor else target
                    actor.boosts.add(ActiveBoost(boost.copy(), buffTarget))
                    if (boost.stat == STAT.HP) {
                        heal(buffTarget, boost.value)
                    }
                }
            }

            // Mort
            if (target.stats.hp <= 0) {
                target.kill(fighters)
                dies(target)
            }

        }

    }

    fun heal(target: LFighter, value: Int)

    fun damage(actor: LFighter, target: LFighter, amount: String)

    fun dies(actor: LFighter)

    fun notarget(actor: LFighter)

}