package com.llgames.ia.battle

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.llgames.ia.states.BattleState

/**
 * Affiche des informations sur les personnages dans la partie supérieure de l'écran.
 */

data class GUIPart(var name: String, val id: Int, val team: Int, var hp: Int, var visible: Boolean = true)

class GUI {

    private var parts = MutableList<GUIPart>(6, { GUIPart("?", it, it / 3, 0) })

    fun setFighters(fighters: Array<Fighter>) {
        val rfighters = fighters.sortedBy { it.id }
        for (part in parts) {
            val fighter = rfighters.filter { it.team == part.team && it.id == part.id }.firstOrNull()
            if (fighter == null) {
                part.visible = false
            } else {
                part.visible = true
                part.hp = fighter.maxStats.hp
                part.name = fighter.name
            }
        }
    }

    fun update(fighters: Array<Fighter>) {
        for (fighter in fighters) {
            parts[fighter.id].hp = fighter.stats.hp
        }
    }

    fun draw(batch: Batch, font: BitmapFont) {

        for (i in 0..1) {
            for ((j, part) in parts.filter { it.team == i }.withIndex()) {
                if (part.visible) {
                    font.draw(batch, part.name, X_START1 + X_OFFSET * i, Y_START - Y_OFFSET * j)
                    font.draw(batch, "HP:" + part.hp, X_START2 + X_OFFSET * i, Y_START - Y_OFFSET * j)
                }
            }
        }

    }

    fun debug(batch: Batch, font: BitmapFont, chars: Array<Fighter>, state: BattleState) {

        font.draw(batch, "TURN " + (if (state.turn < 10) "0" + state.turn else state.turn) + " - ${chars[state.charTurn].name}", -160f + 6f, -90f + 133f)
        font.draw(batch, chars[state.charTurn].getIAString().replace(" - ", "\n"), -160f + 6f, -90f + 118f)

    }

    companion object {
        private const val X_START1 = -160f + 28f
        private const val X_START2 = -160f + 78f
        private const val X_OFFSET = 160f
        private const val Y_START = -90f + 175f
        private const val Y_OFFSET = 11f
    }

}