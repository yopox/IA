package com.llgames.ia.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen


class SelectIA : KtxScreen {
    val batch = SpriteBatch()
    val bg = Texture("ia_screen.png")
    val camera = OrthographicCamera().apply {
        position.set(160f, 90f, 0f)
    }
    val viewport = FitViewport(320f, 180f, camera).apply { apply() }

    override fun render(delta: Float) {

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Prepare for drawing
        batch.projectionMatrix = camera.combined
        batch.begin()

        // Draw Background
        batch.draw(bg, 0f, 0f)
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
