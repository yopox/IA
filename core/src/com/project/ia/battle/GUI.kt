package com.project.ia.battle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.project.ia.states.BattleState
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.Sprite


/**
 * Affiche des informations sur les personnages dans la partie supérieure de l'écran.
 * TODO: Le GUI se casse quand une team prend trop l'avantage
 */

class GUI {

    companion object {
        private fun createTexture(width: Int, height: Int, color: Color): Texture {
            val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
            pixmap.setColor(color)
            pixmap.fillRectangle(0, 0, width, height)
            val texture = Texture(pixmap)
            pixmap.dispose()
            return texture
        }

        val barre0 = Sprite(createTexture(240, 6, Color.valueOf("98ea59")))
        val barre1 = Sprite(createTexture(240, 6, Color.valueOf("ea9859")))
        val black = Sprite(createTexture(240, 6, Color.valueOf("000000")))
        var maxHP0 = 0
        var maxHP1 = 0
        var div = 0.5f
    }

    fun setFighters(fighters: Array<Fighter>) {
        update(fighters)
        maxHP0 = 0
        maxHP1 = 0
        fighters.filter { it.team == 0 }.map { maxHP0 += it.stats.hp }
        fighters.filter { it.team == 1 }.map { maxHP1 += it.stats.hp }
        update(fighters)
    }

    fun update(fighters: Array<Fighter>) {
        var sum0 = 0f
        fighters.filter { it.team == 0 }.map { sum0 += it.stats.hp }
        var sum1 = 0f
        fighters.filter { it.team == 1 }.map { sum1 += it.stats.hp }

        div = 2 * sum0 / maxHP0 / (sum0 / maxHP0 + sum1 / maxHP1)

    }

    fun draw(batch: Batch, font: BitmapFont, bState: BattleState) {

        font.color = Color.WHITE
        font.draw(batch, "TURN " + if (bState.turn < 10) "0" + bState.turn else bState.turn, -18f, 80f)
        batch.draw(barre1, -120f, 58f)
        batch.draw(barre0, 120f * (1 - div), 58f)
        batch.draw(black, 120f, 58f)

    }

}