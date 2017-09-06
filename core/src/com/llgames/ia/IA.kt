package com.llgames.ia

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import java.awt.Color
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport


class IA : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var bg: Texture
    private lateinit var char: Texture
    private lateinit var viewport: FitViewport
    private lateinit var camera: OrthographicCamera
    var angle = 0.toDouble()

    override fun create() {
        batch = SpriteBatch()
        bg = Texture("bg.png")
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        char = Texture("char.png")

        camera = OrthographicCamera()
        viewport = FitViewport(320f, 180f, camera)
        viewport.apply()
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f)

    }

    override fun render() {
        angle += 0.02
        camera.update()
        val clearColor = Color.getColor("5850c0")
        Gdx.gl.glClearColor(49 / 255f, 49 / 255f, 54 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        var camX = Math.cos(angle)
        var camY = Math.sin(angle)
        var oX = camera.viewportWidth / 2
        var oY = camera.viewportHeight / 2
        var charX = 0.45f
        var charY = 0.3f

        var beta = ((charY - camY.toFloat()) angle (charX - camX.toFloat())).toDouble()
        var dist = Math.sqrt(Math.pow(charY.toDouble(), 2.0) + Math.pow(charX.toDouble(), 2.0))

        var posX = (oX - dist * Math.sin(beta) * 140 - 8).toFloat() - 16
        var posY = (oY + dist * Math.cos(beta) * 30 - 8).toFloat()

        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(bg, 0f, 78f, (64 * angle).toInt(), 0, 320, 102)
        if (posX + 16 < oX) {
            batch.draw(char, posX + 16, posY,-16f,32f)
        } else {
            batch.draw(char, posX, posY)
        }
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        bg.dispose()
    }

    private infix fun Float.angle(x:Float): Float {
        return Math.atan2(this.toDouble(), x.toDouble()).toFloat();
    }

}
