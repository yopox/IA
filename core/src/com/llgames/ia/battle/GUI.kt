package com.llgames.ia.battle

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.llgames.ia.states.BattleState

/**
 * Affiche des informations sur les personnages dans la partie supérieure de l'écran.
 *
 * TODO: Construire [parts] à l'initialisation et le MAJ uniquement dans [init] (à renommer)
 */

data class GUIPart(val name: String, val team: Int, var hp: Int)

class GUI {

    private var parts = ArrayList<GUIPart>()

    fun init(fighters: Array<Fighter>) {
        val rfighters = fighters.sortedBy { it.id }
        if (parts.size == 0) {
            rfighters.filter { it.team == 0 }.forEach { parts.add(GUIPart(it.name, 0, it.maxStats.hp)) }
            rfighters.filter { it.team == 1 }.forEach { parts.add(GUIPart(it.name, 1, it.maxStats.hp)) }
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
                font.draw(batch, part.name, X_START1 + X_OFFSET * i, Y_START - Y_OFFSET * j)
                font.draw(batch, "HP:" + part.hp, X_START2 + X_OFFSET * i, Y_START - Y_OFFSET * j)
            }
        }

    }

    fun debug(batch: Batch, font: BitmapFont, chars: Array<Fighter>, state: BattleState) {

        font.draw(batch, "-- TURN " + (if (state.turn < 10) "0" + state.turn else state.turn) + " --", -160f + 6f, -90f + 137f)
        font.draw(batch, chars[state.charTurn].getIAString().replace(" - ", "\n"), -160f + 6f, -90f + 122f)

    }

    companion object {
        private const val X_START1 = -160f + 28f
        private const val X_START2 = -160f + 78f
        private const val X_OFFSET = 160f
        private const val Y_START = -90f + 175f
        private const val Y_OFFSET = 11f
    }

}