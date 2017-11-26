package com.llgames.ia

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont

/**
 * Created by yopox on 26/11/2017.
 */

class Console {
    private val line1 = "hero1 takes 6 damage!"
    private val line2 = "foe2 uses Earthquake!"

    fun draw(batch: Batch, font: BitmapFont) {

        val posX = 28f;

        font.draw(batch, line1, posX, 39f)
        font.draw(batch, line2, posX, 25f)
    }
}