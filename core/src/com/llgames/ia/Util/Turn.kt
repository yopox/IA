package com.llgames.ia.Util

/**
 * Created by yopox on 29/11/2017.
 */

data class TurnAction(var star: Int = 0, var targets:Array<Int> = arrayOf(0), var text: String = "missing text")

class Turn() {

    fun update(camera: Camera, console: Console, chars: Array<Perso>) {

        val tA = getRandomTurn(chars)
        if (chars[tA.star].team == 1) camera moveTo "foe" else camera moveTo "ally"
        console.writeText(tA.text)

    }

    // private fun interpolation(from: Double, shift: Double, method: String = "sin") {
         
    // }

    private fun getRandomTurn(chars: Array<Perso>): TurnAction {

        val r = Math.floor(Math.random() * chars.size).toInt()
        return(TurnAction(r, arrayOf(r), chars[r].name + " does nothing."))

    }

}