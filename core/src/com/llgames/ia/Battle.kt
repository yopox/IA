package com.llgames.ia

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.llgames.ia.Gif.GifRecorder
import com.llgames.ia.Util.*
import ktx.app.KtxScreen


class Battle : KtxScreen {
    val batch = SpriteBatch()
    val bg = Texture("bg.png").apply {
        setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    }
    val chars = prepareChars()
    val manager = Manager().apply { init(chars) }
    val font = BitmapFont(Gdx.files.internal("m5x7.fnt"), false)
    val recorder = GifRecorder(batch)
    val debug: Boolean = true

    private fun prepareChars(): Array<Perso> {
        val pos = arrayOf(floatArrayOf(-0.2f, 0.5f),
                floatArrayOf(0f, 0.5f),
                floatArrayOf(0.2f, 0.5f),
                floatArrayOf(-0.2f, -0.5f),
                floatArrayOf(0f, -0.5f),
                floatArrayOf(0.2f, -0.5f))
        val textures = arrayOf("char2.png", "char.png")
        return Array(6, { i -> Perso(Texture(textures[i / 3]), 16, 32, pos[i][0], pos[i][1], "Char" + i.toString(), i / 3, i % 3) })
    }

    override fun render(delta: Float) {

        // Update
        manager.update(chars)

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Prepare for drawing
        batch.projectionMatrix = manager.camera.combined
        batch.begin()

        // Draw GUI
        manager.draw(batch, font)

        // Draw Background
        batch.draw(bg, 0f, 39f, (80 * manager.camera.angle).toInt(), 0, 320, 102)

        // Draw chars
        chars.sortByDescending { it.y }
        chars.map { it.drawChar(batch, manager.camera) }
        chars.sortByDescending { -3 * it.team - it.id }

        if (debug) {
            manager.debug(batch, font, chars)
        }

        batch.end()

        recorder.update()

    }

    override fun dispose() {
        batch.dispose()
        bg.dispose()
    }

    override fun resize(width: Int, height: Int) {
        manager.resize(width, height)
    }

}
