package com.llgames.ia.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.llgames.ia.def.General
import ktx.app.KtxScreen

/**
 * Game State correspondant à l'écran titre.
 * TODO: Boutons
 */
class TitleScreen : KtxScreen {
    private val batch = SpriteBatch()
    private val font = BitmapFont(Gdx.files.internal("softsquare.fnt"), false)
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(320f, 180f, 360f, 180f, camera)

    init {
        font.color = Color.BLACK
    }

    override fun render(delta: Float) {

        camera.update()

        // Clear screen
        Gdx.gl.glClearColor(0.518f, 0.494f, 0.529f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Prepare for drawing
        batch.projectionMatrix = camera.combined
        batch.begin()

        font.data.setScale(4f)
        font.draw(batch, "IAProject", -15f, 80f)
        font.data.setScale(1f)
        font.draw(batch, "build ${General.BUILD_NB}", 83f, 40f)

        batch.end()

    }

    override fun dispose() {
        batch.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

}