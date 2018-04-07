package com.llgames.ia

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.llgames.ia.Gif.GifRecorder
import com.llgames.ia.Util.*
import ktx.app.KtxScreen


class Battle : KtxScreen {
    val batch = SpriteBatch()
    val bg = Texture("bg.png").apply {
        setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    }
    private val chars = prepareFighters()
    private val manager = Manager().apply { init(chars) }
    val font = BitmapFont(Gdx.files.internal("softsquare.fnt"), false)
    val recorder = GifRecorder(batch)
    val debug: Boolean = true

    private fun prepareFighters(): Array<Fighter> {
        val pos = arrayOf(floatArrayOf(-0.2f, 0.45f),
                floatArrayOf(0.2f, 0.45f),
                floatArrayOf(-0.2f, -0.45f),
                floatArrayOf(0.2f, -0.45f))
        val textures = arrayOf("char2.png", "char.png")
        val names = arrayListOf("elyopox", "Skaama_", "RiptoGamer", "Bydl0_")
        return Array(4, { i -> Fighter(Texture(textures[i / 2]), 16, 32, pos[i][0], pos[i][1], names[i], i / 2, i % 2) })
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
