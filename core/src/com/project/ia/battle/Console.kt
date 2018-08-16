package com.project.ia.battle

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont

/**
 * Created by yopox on 26/11/2017.
 */

class Console {
    private val lines = arrayOf("", "")
    private var toWrite = ""
    private var firstWrite = 0

    /**
     * Appelé à chaque début de combat.
     */
    fun reset() {
        toWrite = "Battle begins!"
        firstWrite = toWrite.length
        lines[0] = ""
        lines[1] = ""
    }

    /**
     * Appelé à chaque frame où la console s'actualise.
     */
    fun update() {
        if (toWrite.isNotEmpty()) {
            if (firstWrite > 0) {
                lines[0] = lines[0] + toWrite[0]
                firstWrite--
            } else {
                lines[1] = lines[1] + toWrite[0]
            }
            toWrite = toWrite.substring(1)
        }
    }

    fun draw(batch: Batch, font: BitmapFont) {
        val posX = -160f + 28f
        font.draw(batch, lines[0], posX, -90f + 32f)
        font.draw(batch, lines[1], posX, -90f + 16f)
    }

    infix fun write(text: String) {
        if (lines[1].isNotEmpty())
            lines[0] = lines[1]
        lines[1] = ""
        toWrite = text
    }
}