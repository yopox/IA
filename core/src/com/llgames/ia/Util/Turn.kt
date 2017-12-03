package com.llgames.ia.Util

/**
 * Created by yopox on 29/11/2017.
 * Turn is used to manage the different actions happening in a turn.
 * Here are the different actions :
 * - [frame, "cam", [], "foe" / "ally"]
 * - [frame, "txt", [], "Text to write"]
 */

data class TurnAction(val frame: Int = 0, val id: String = "cam", val intContent:Array<Int> = arrayOf(0), val strContent: String = "Default text.")

class Turn() {

    private lateinit var actions: Array<TurnAction> 

    fun newTurn(char: Int, chars: Array<Perso>) {

        getRandomTurn(char, chars)

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

    private fun getRandomTurn(char: Int, chars: Array<Perso>) {

        val text = if (chars[char].team == 0) "ally" else "foe"
        actions = arrayOf(TurnAction(0, "cam", strContent = text), TurnAction(10, "txt", strContent = chars[char].name + " does nothing."))

    }

}