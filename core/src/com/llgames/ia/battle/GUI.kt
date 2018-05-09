package com.llgames.ia.battle

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont

/**
 * GUI
 */

data class GUIPart(val name: String, val team: Int, var hp: Int)

class GUI {

    private var parts = ArrayList<GUIPart>()
    private var names = Array(2, { _ -> Array(3, { _ -> "" }) })
    private var hp = Array(2, { _ -> Array(3, { _ -> 0 }) })

    fun init(fighters: Array<Fighter>) {
        fighters.filter { it.team == 0 }.forEach { parts.add(GUIPart(it.name, 0, it.maxStats.hp)) }
        fighters.filter { it.team == 1 }.forEach { parts.add(GUIPart(it.name, 1, it.maxStats.hp)) }
    }

    fun update(fighters: Array<Fighter>) {
        for (fighter in fighters) {
            parts[fighter.id].hp = fighter.stats.hp
        }
    }

    fun draw(batch: Batch, font: BitmapFont) {

        for (i in 0..1) {
            for ((j, part) in parts.filter { it.team == i }.withIndex()) {
                font.draw(batch, part.name, X_START1 + X_OFFSET * i, Y_START - Y_OFFSET * j)
                font.draw(batch, "HP:" + part.hp, X_START2 + X_OFFSET * i, Y_START - Y_OFFSET * j)
            }
        }

    }

    fun debug(batch: Batch, font: BitmapFont, chars: Array<Fighter>, state: State) {

        font.draw(batch, "-- TURN " + (if (state.turn < 10) "0" + state.turn else state.turn) + " --", 6f, 137f)
        font.draw(batch, chars[state.charTurn].getIAString(), 6f, 122f)

    }

    companion object {
        private const val X_START1 = -160f + 28f
        private const val X_START2 = -160f + 58f
        private const val X_OFFSET = 160f
        private const val Y_START = -90f + 175f
        private const val Y_OFFSET = 11f
    }

}