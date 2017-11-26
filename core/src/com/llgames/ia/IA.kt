package com.llgames.ia

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport


class IA : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var bg: Texture
    private lateinit var viewport: FitViewport
    private lateinit var camera: Camera
    private lateinit var font: BitmapFont
    var counter = 0.0
    var oX = 0f
    var oY = 0f
    val pos = arrayOf(floatArrayOf(-0.2f, 0.5f),
            floatArrayOf(0f, 0.5f),
            floatArrayOf(0.2f, 0.5f),
            floatArrayOf(-0.2f, -0.5f),
            floatArrayOf(0f, -0.5f),
            floatArrayOf(0.2f, -0.5f))
    private lateinit var chars1: Array<Perso>
    private lateinit var chars2: Array<Perso>
    private lateinit var gui: GUI
    private lateinit var console: Console

    override fun create() {

        batch = SpriteBatch()
        bg = Texture("bg.png").apply {
            setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        }
        chars1 = Array(3, { i -> Perso(Texture("char.png"), 16, 32, pos[i][0], pos[i][1]) })
        chars2 = Array(3, { i -> Perso(Texture("char.png"), 16, 32, pos[i+3][0], pos[i+3][1]) })
        gui = GUI().apply { update(chars1, chars2) }
        console = Console()

        camera = Camera()
        viewport = FitViewport(320f, 180f, camera).apply { apply() }

        oX = camera.viewportWidth / 2
        oY = camera.viewportHeight / 2
        camera.position.set(oX, oY, 0f)

        font = BitmapFont(Gdx.files.internal("m5x7.fnt"), false)
    }

    override fun render() {

        // TODO : Handle camera properly
        counter += 0.02
        camera.angle = 3.2 + Math.sin(1.5 * counter) / 2
        chars1.map { it.updatePos(camera.angle, oX, oY) }
        chars2.map { it.updatePos(camera.angle, oX, oY) }

        // TODO : Update GUI

        camera.update()
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.projectionMatrix = camera.combined
        batch.begin()

        // GUI
        gui.draw(batch, font)
        console.draw(batch, font)

        // BACKGROUND
        batch.draw(bg, 0f, 39f, (80 * camera.angle).toInt(), 0, 320, 102)

        // CHARS
        chars1.map { it.drawChar(batch, oX) }
        chars2.map { it.drawChar(batch, oX) }

        batch.end()

    }

    override fun dispose() {
        batch.dispose()
        bg.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

}
