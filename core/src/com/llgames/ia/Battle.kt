package com.llgames.ia

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.llgames.ia.battle.Manager
import com.llgames.ia.battle.Fighter
import com.llgames.ia.gif.GifRecorder
import ktx.app.KtxScreen


class Battle : KtxScreen {
    private val batch = SpriteBatch()
    private val bg = Texture("bg.png")
    private val fighters: Array<Fighter>
    private val manager: Manager
    private val font = BitmapFont(Gdx.files.internal("softsquare.fnt"), false)
    private val recorder = GifRecorder(batch)

    init {
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        val pos = arrayOf(floatArrayOf(-0.2f, -0.45f),
                floatArrayOf(0f, -0.45f),
                floatArrayOf(0.2f, -0.45f),
                floatArrayOf(-0.2f, 0.45f),
                floatArrayOf(0f, 0.45f),
                floatArrayOf(0.2f, 0.45f))
        val textures = arrayOf("char2.png", "char.png")
        val names = arrayListOf("A", "B", "C", "D", "E", "F")
        fighters = Array(6, { i -> Fighter(Texture(textures[i / 3]), 16, 32, pos[i][0], pos[i][1], names[i], i / 3, i % 3) })
        fighters.forEach { it.prepare() }
        manager = Manager().apply { init(fighters) }
    }

    override fun render(delta: Float) {

        // Update
        manager.update(fighters)

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

        // Draw fighters
        fighters.sortByDescending { it.sprite.y }
        fighters.map { it.drawChar(batch, manager.camera) }
        fighters.sortByDescending { -3 * it.team - it.id }

        manager.debug(batch, font, fighters)

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