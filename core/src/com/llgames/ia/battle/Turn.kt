package com.llgames.ia.battle

import com.llgames.ia.battle.logic.IAHandler

/**
 * Turn is used to manage the different actions happening in a turn.
 * Here are the different actions :
 * - [frame, "cam", [], "foe" / "ally"]
 * - [frame, "txt", [], "Text to write"]
 * - [frame, "def", [0], ""]
 */

data class TurnAction(val frame: Int = 0, val id: String = "cam", val intContent: Array<Int> = arrayOf(0), val strContent: String = "Default text.")

class Turn() : IAHandler {

    private val actions: MutableList<TurnAction> = mutableListOf()

    override fun newTurn(fighters: Array<Fighter>, state: State) {

        actions.clear()

        // Move the camera to the playing character        
        val text = if (fighters[state.charTurn].team == 0) "ally" else "foe"
        actions.add(TurnAction(0, "cam", strContent = text))

        super.newTurn(fighters, state)

    }

    fun update(frame: Int, camera: Camera, console: Console, chars: Array<Fighter>) {

        actions
                .filter { frame == it.frame }
                .forEach {
                    when (it.id) {
                        "cam" -> camera moveTo it.strContent
                        "txt" -> console write it.strContent
                    }
                }

    }

    override fun def(fighters: Array<Fighter>, state: State) {
        actions.add(TurnAction(10, "txt", strContent = fighters[state.charTurn].name + " is defending himself!"))
    }

    override fun wpn(fighters: Array<Fighter>, state: State, target: Fighter) {
        TODO("not implemented")
    }

    override fun spl(fighters: Array<Fighter>, state: State, target: Fighter) {
        TODO("not implemented")
    }

    override fun pro(fighters: Array<Fighter>, state: State, target: Fighter) {
        TODO("not implemented")
    }

    override fun wait(fighters: Array<Fighter>, state: State) {
        actions.add(TurnAction(10, "txt", strContent = fighters[state.charTurn].name + " does nothing."))
    }

}