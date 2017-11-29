package com.llgames.ia.Util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite

/**
 * Created by yopox on 06/09/2017.
 */
class Perso(texture: Texture?, srcWidth: Int, srcHeight: Int, private val posX: Float, private val posY: Float) : Sprite(texture, srcWidth, srcHeight) {

    fun updatePos(camera:Camera) {

        val alpha = camera.angle
        val oX = camera.center[0]
        val oY = camera.center[1]

        val beta = Math.atan2(Math.sin(alpha), Math.cos(alpha)) - Math.atan2(posY.toDouble(), posX.toDouble())
        val dist = Math.sqrt(Math.pow(posY.toDouble(), 2.0) + Math.pow(posX.toDouble(), 2.0))

        this.x = (oX + dist * Math.sin(beta) * 165 - 8).toFloat()
        this.y = (oY + dist * Math.cos(beta) * 18 - 12 - 22).toFloat()
        this.setScale(1f - Math.cos(beta).toFloat() / 7)

    }

    fun drawChar(batch: Batch, camera: Camera) {
        val oX = camera.center[0]
        this.setFlip(this.x + 16 < oX, false)
        this.draw(batch)
    }

}
