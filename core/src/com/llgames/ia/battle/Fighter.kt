package com.llgames.ia.battle

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.llgames.ia.battle.logic.LFighter
import java.util.*

/**
 * Created by yopox on 06/09/2017.
 */

class Fighter(texture: Texture?, srcWidth: Int, srcHeight: Int, private val depX: Float, private val depY: Float, name: String, team: Int, id: Int) : LFighter(name, team, id) {

    val sprite = Sprite(texture, srcWidth, srcHeight)
    private var upcomingPos = Array(2, { _ -> Vector<Float>()})
    private var posX: Float = depX
    private var posY: Float = depY
    var forceFacing: Fighter? = null
    private var blink = 0

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
        if (upcomingPos[0].size > 0) posX = upcomingPos[0].removeAt(0)
        if (upcomingPos[1].size > 0) posY = upcomingPos[1].removeAt(0)

        if (blink > 0) {
            sprite.setAlpha((blink / 5 + 1) % 2f)
            blink--
        }

        val oX = camera.center[0]
        when (forceFacing) {
            null -> sprite.setFlip(sprite.x + 16 < oX, false)
            else -> sprite.setFlip(forceFacing!!.sprite.x + 16 >= oX, false)
        }

        sprite.draw(batch)
    }

    infix fun moveTo(fighter: Fighter) {
        val finalX = fighter.posX
        val finalY = (Math.abs(fighter.posY) - 0.15f) * Math.signum(fighter.posY)

        upcomingPos[0].addAll(Array(30, {i -> (i + 1) / 30f * (finalX - depX) + depX}))
        upcomingPos[1].addAll(Array(30, {i -> (i + 1) / 30f * (finalY - depY) + depY}))
    }

    fun resetPos() {
        val fromX = posX
        val fromY = posY

        upcomingPos[0].addAll(Array(30, {i -> (i + 1) / 30f * (depX - fromX) + fromX}))
        upcomingPos[1].addAll(Array(30, {i -> (i + 1) / 30f * (depY - fromY) + fromY}))
    }

    fun blink() {
        blink = 30
    }

}