package com.llgames.ia

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import java.awt.Color
import com.badlogic.gdx.utils.viewport.FitViewport


class IA : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var bg: Texture
    private lateinit var viewport: FitViewport
    private lateinit var camera: OrthographicCamera
    var angle = 3.2
    var counter = 0.0
    var oX = 0f
    var oY = 0f
    val pos = arrayOf(floatArrayOf(-0.2f, 0.5f),
            floatArrayOf(0f, 0.5f),
            floatArrayOf(0.2f, 0.5f),
            floatArrayOf(-0.2f, -0.5f),
            floatArrayOf(0f, -0.5f),
            floatArrayOf(0.2f, -0.5f))
    private lateinit var chars: Array<Perso>

    override fun create() {

        batch = SpriteBatch()
        bg = Texture("bg.png").apply {
            setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        }
        chars = Array(6, { i -> Perso(Texture("char.png"), 16, 32, pos[i][0], pos[i][1]) })

        camera = OrthographicCamera()
        viewport = FitViewport(320f, 180f, camera).apply { apply() }

        oX = camera.viewportWidth / 2
        oY = camera.viewportHeight / 2
        camera.position.set(oX, oY, 0f)

    }

    override fun render() {

        // TODO : Handle camera properly
        counter += 0.02
        angle = 3.2 + Math.sin(counter) / 1.5
        chars.map { it.updatePos(angle, oX, oY) }

        camera.update()
        Gdx.gl.glClearColor(49 / 255f, 49 / 255f, 54 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(bg, 0f, 78f, (80 * angle).toInt(), 0, 320, 102)

        for (char in chars) {
            if (char.realX + 16 < oX) {
                batch.draw(char, char.realX + 16, char.realY, -16f, 32f)
            } else {
                batch.draw(char, char.realX, char.realY)
            }
        }

        batch.end()

    }

    override fun dispose() {
        batch.dispose()
        bg.dispose()
    }

}
