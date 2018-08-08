package com.project.ia.genetic

import com.project.ia.battle.TurnAction
import com.project.ia.logic.BattleState
import com.project.ia.logic.IAHandler
import com.project.ia.logic.LFighter

/**
 * Implémentation de [IAHandler].
 *
 * TODO: Calcul du fitness selon ce qu'il se passe, malus de fitness pour [notarget] par ex ?
 */
class GTurn : IAHandler {

    override fun play(fighters: Array<out LFighter>, state: BattleState) {
        super.play(fighters, state)
    }

    override fun wait(fighters: Array<out LFighter>, state: BattleState) {
    }

    override fun damage(actor: LFighter, target: LFighter, amount: String) {
    }

    override fun dies(actor: LFighter) {
    }

    override fun notarget(actor: LFighter) {
    }

}