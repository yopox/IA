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
    private lateinit var viewport: Viewport
    private lateinit var camera: OrthographicCamera

    override fun create() {
        batch = SpriteBatch()
        bg = Texture("bg.png")
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

        camera = OrthographicCamera()
        viewport = FitViewport(320f, 180f, camera)
        viewport.apply()
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f)

    }

    override fun render() {
        camera.update()
        val clearColor = Color.getColor("5850c0")
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(bg, 0f, 78f, 128, 0, 320, 102)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        bg.dispose()
    }
}
