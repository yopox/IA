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


class Battle : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var bg: Texture
    private lateinit var viewport: FitViewport
    private lateinit var font: BitmapFont
    private lateinit var chars: Array<Perso>
    private lateinit var recorder: GifRecorder
    private val debug: Boolean = true
    private val manager = Manager()

    override fun create() {
        batch = SpriteBatch()
        recorder = GifRecorder(batch)
        bg = Texture("bg.png").apply {
            setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        }
        chars = manager.getChars()
        viewport = FitViewport(320f, 180f, manager.camera).apply { apply() }
        manager.init(chars)
        font = BitmapFont(Gdx.files.internal("m5x7.fnt"), false)
    }

    override fun render() {

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
        chars.sortByDescending { - 3 * it.team - it.id }

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
        viewport.update(width, height)
    }

}
