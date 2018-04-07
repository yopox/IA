package com.llgames.ia.battle

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.llgames.ia.battle.logic.LFighter

/**
 * Created by yopox on 06/09/2017.
 */

class Fighter(texture: Texture?, srcWidth: Int, srcHeight: Int, private var posX: Float, private var posY: Float, name: String, team: Int, id: Int) : LFighter(name, team, id) {

    val sprite = Sprite(texture, srcWidth, srcHeight)

    fun updatePos(camera: Camera) {

        val alpha = camera.angle
        val oX = camera.center[0]
        val oY = camera.center[1]

        val beta = Math.atan2(Math.sin(alpha), Math.cos(alpha)) - Math.atan2(posY.toDouble(), posX.toDouble())
        val dist = Math.sqrt(Math.pow(posY.toDouble(), 2.0) + Math.pow(posX.toDouble(), 2.0))

        sprite.x = (oX + dist * Math.sin(beta) * 165 - 8).toFloat()
        sprite.y = (oY + dist * Math.cos(beta) * 18 - 12 - 22).toFloat()
        sprite.setScale(1f - Math.cos(beta).toFloat() / 7)

    }

    fun drawChar(batch: Batch, camera: Camera) {
        val oX = camera.center[0]
        sprite.setFlip(sprite.x + 16 < oX, false)
        sprite.draw(batch)
    }



}
