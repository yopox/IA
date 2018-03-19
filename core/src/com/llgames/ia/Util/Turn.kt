package com.llgames.ia.Util

/**
 * Turn is used to manage the different actions happening in a turn.
 * Here are the different actions :
 * - [frame, "cam", [], "foe" / "ally"]
 * - [frame, "txt", [], "Text to write"]
 * - [frame, "def", [0], ""]
 */

data class TurnAction(val frame: Int = 0, val id: String = "cam", val intContent:Array<Int> = arrayOf(0), val strContent: String = "Default text.")

class Turn() {

    val actions: MutableList<TurnAction> = mutableListOf()

    fun newTurn(chars: Array<Perso>, state: State) {

        actions.clear()

        // Move the camera to the playing character        
        val text = if (chars[state.charTurn].team == 0) "ally" else "foe"
        actions.add(TurnAction(0, "cam", strContent = text))
        
        // Get the rule
        val rule = chars[state.charTurn].getRule(chars, state)
        
        when (rule.act.id) {
            "DEF" -> actions.add(TurnAction(10, "txt", strContent = chars[state.charTurn].name + " is defending himself!"))
            else -> actions.add(TurnAction(10, "txt", strContent = chars[state.charTurn].name + " does nothing."))
        }

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

}