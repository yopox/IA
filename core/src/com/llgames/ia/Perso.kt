package com.llgames.ia

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

/**
 * Created by yopox on 06/09/2017.
 */
class Perso(texture: Texture?, srcWidth: Int, srcHeight: Int, private val posX: Float, private val posY: Float) : Sprite(texture, srcWidth, srcHeight) {
    var realX: Float = 0f
    var realY: Float = 0f

    fun updatePos(alpha: Double, oX: Float, oY: Float) {

        val beta = Math.atan2(Math.sin(alpha), Math.cos(alpha)) - Math.atan2(posY.toDouble(), posX.toDouble())
        val dist = Math.sqrt(Math.pow(posY.toDouble(), 2.0) + Math.pow(posX.toDouble(), 2.0))

        realX = (oX + dist * Math.sin(beta) * 165 - 8).toFloat()
        realY = (oY + dist * Math.cos(beta) * 20 - 8).toFloat()

    }

}
