package com.project.ia.genetic

import com.project.ia.battle.TurnAction
import com.project.ia.logic.BattleState
import com.project.ia.logic.IAHandler
import com.project.ia.logic.LFighter
import com.project.ia.logic.State

/**
 * Implémentation de [IAHandler].
 *
 * TODO: Calcul du fitness selon ce qu'il se passe, malus de fitness pour [notarget] par ex ?
 */
class GTurn : IAHandler {

    override fun play(fighters: Array<out LFighter>, state: State) {
        super.play(fighters, state)
    }

    override fun wait(fighters: Array<out LFighter>, state: State) {
    }

    override fun damage(actor: LFighter, target: LFighter, amount: String) {
    }

    override fun heal(target: LFighter, value: Int) {

    }

    override fun dies(actor: LFighter) {
    }

    override fun notarget(actor: LFighter) {
    }

}