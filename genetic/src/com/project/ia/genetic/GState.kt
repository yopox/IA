package com.project.ia.genetic

import com.project.ia.logic.BattleState

class GState(turn: Int = 1, frame: Int = -1, charTurn: Int = 0, winner: Int = -1) : BattleState(turn, frame, charTurn, winner) {

    override fun newTurn() {
        super.newTurn()
    }

}