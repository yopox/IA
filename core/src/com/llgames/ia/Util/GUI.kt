package com.llgames.ia.Util

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/**
 * Created by yopox on 26/11/2017.
 */

class GUI() {
    private var names = Array(2, { _ -> Array(3, { _ -> "" }) })
    private var hp = Array(2, { _ -> Array(3, { _ -> 0 }) })
    private var mp = Array(2, { _ -> Array(3, { _ -> 0 }) })

    fun init(chars: Array<Perso>) {
        // Set names
        for (char in chars) {
            names[char.team][char.id] = char.name
        }
    }

    fun update(chars: Array<Perso>) {
        // TODO : Update stats
    }

    fun draw(batch: Batch, font: BitmapFont) {

        val posX = floatArrayOf(28f, 78f, 108f);
        val posY = floatArrayOf(184f, 173f, 162f);

        for (i in 0..1) {
            for (j in 0..2) {
                if (names[i][j].isNotEmpty()) {
                    font.draw(batch, names[i][j], posX[0] + 160 * i, posY[j])
                    font.draw(batch, "HP:" + hp[i][j], posX[1] + 160 * i, posY[j])
                    font.draw(batch, "MP:" + mp[i][j], posX[2] + 160 * i, posY[j])
                }
            }
        }
    }

    fun debug(batch: Batch, font: BitmapFont, camera: Camera, chars: Array<Perso>, state: State) {

        font.draw(batch, "TURN " + if (state.turn < 10) "0" + state.turn else state.turn, 6f, 144f)
        font.draw(batch, chars[state.charTurn].getIAString(), 6f, 129f)

    }

}