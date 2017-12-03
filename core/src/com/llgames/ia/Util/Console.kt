package com.llgames.ia.Util

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont

/**
 * Created by yopox on 26/11/2017.
 */

class Console {
    private val lines = arrayOf("Battle begins!", "")
    private var toWrite = ""

    fun update() {
        if (toWrite.isNotEmpty()) {
            lines[1] = lines[1] + toWrite[0]
            toWrite = toWrite.substring(1)
        }
    }

    fun draw(batch: Batch, font: BitmapFont) {

        val posX = 28f;

        font.draw(batch, lines[0], posX, 39f)
        font.draw(batch, lines[1], posX, 25f)
    }
    
    infix fun write(text: String) {
        if (lines[1].isNotEmpty())
            lines[0] = lines[1]
        lines[1] = ""
        toWrite = text
    }
}