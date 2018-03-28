package com.llgames.ia.Util

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

    override fun newTurn(chars: Array<Perso>, state: State) {

        actions.clear()

        // Move the camera to the playing character        
        val text = if (chars[state.charTurn].team == 0) "ally" else "foe"
        actions.add(TurnAction(0, "cam", strContent = text))

        super.newTurn(chars, state)

    }

    fun update(frame: Int, camera: Camera, console: Console, chars: Array<Perso>) {

        actions
                .filter { frame == it.frame }
                .forEach {
                    when (it.id) {
                        "cam" -> camera moveTo it.strContent
                        "txt" -> console write it.strContent
                    }
                }

    }

    override fun def(chars: Array<Perso>, state: State) {
        actions.add(TurnAction(10, "txt", strContent = chars[state.charTurn].name + " is defending himself!"))
    }

    override fun wpn(chars: Array<Perso>, state: State) {
        TODO("not implemented")
    }

    override fun spl(chars: Array<Perso>, state: State) {
        TODO("not implemented")
    }

    override fun pro(chars: Array<Perso>, state: State) {
        TODO("not implemented")
    }

    override fun wait(chars: Array<Perso>, state: State) {
        actions.add(TurnAction(10, "txt", strContent = chars[state.charTurn].name + " does nothing."))
    }

}