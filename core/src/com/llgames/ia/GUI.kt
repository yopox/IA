package com.llgames.ia

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont

/**
 * Created by yopox on 26/11/2017.
 */

class GUI() {
    private val names = Array(2, { i -> Array(3, { j -> "char$i$j" }) })
    private val hp = Array(2, { i -> Array(3, { j -> 0 }) })
    private val mp = Array(2, { i -> Array(3, { j -> 0 }) })

    fun update(heroes:Array<Perso>, foes:Array<Perso>) {

    }

    fun draw(batch: Batch, font: BitmapFont) {

        val posX = floatArrayOf(28f, 78f, 108f);
        val posY = floatArrayOf(186f, 175f, 164f);

        for (i in 0..1) {
            for (j in 0..2) {
                font.draw(batch, names[i][j], posX[0] + 160 * i, posY[j])
                font.draw(batch, "HP:" + hp[i][j], posX[1] + 160 * i, posY[j])
                font.draw(batch, "MP:" + mp[i][j], posX[2] + 160 * i, posY[j])
            }
        }
    }
}