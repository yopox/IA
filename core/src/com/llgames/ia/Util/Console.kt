package com.llgames.ia.Util

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont

/**
 * Created by yopox on 26/11/2017.
 */

class Console {
    private val lines = Array(2, { _ -> ""})
    private var toWrite = "foe2 uses Earthquake!"
    private val writeTo = 0

    fun update() {
        if (toWrite.isNotEmpty()) {
            lines[writeTo] = lines[writeTo] + toWrite[0]
            toWrite = toWrite.substring(1)
        }
    }

    fun draw(batch: Batch, font: BitmapFont) {

        val posX = 28f;

        font.draw(batch, lines[0], posX, 39f)
        font.draw(batch, lines[1], posX, 25f)
    }
}