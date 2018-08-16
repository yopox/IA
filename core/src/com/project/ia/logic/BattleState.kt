package com.project.ia.logic

/**
 * TODO: [frame] ne devrait pas être dans la partie logique -> créer une superclasse State.
 */
open class BattleState(var turn: Int = 1, var frame: Int = -1, var charTurn: Int = 0, var winner: Int = -1) {
    open fun newTurn() {
        charTurn = 0
        turn++
    }
}