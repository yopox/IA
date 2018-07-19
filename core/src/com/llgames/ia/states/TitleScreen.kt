package com.llgames.ia.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.llgames.ia.def.General
import ktx.app.KtxScreen
import ktx.scene2d.*


/**
 * Game State correspondant à l'écran titre.
 * TODO: Faire le layout Stage2D
 */
class TitleScreen : KtxScreen {
    private val font = BitmapFont(Gdx.files.internal("softsquare.fnt"), false)
    private val rune = Texture("rune.png")
    private val stage = Stage()
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(320f, 180f, 360f, 180f, camera)

    init {
        font.color = Color.BLACK

        Gdx.input.inputProcessor = stage
        stage.viewport = viewport

        val uiSkin = Skin(Gdx.files.internal("uiskin.json"))
        Scene2DSkin.defaultSkin = uiSkin

        stage.addActor(table {
            pad(4f) // Setting table padding.
            label("Test.") {
                color = Color.BLACK // Setting text's color.
                setWrap(true) // Setting label's text wrapping.
            }.apply { skin = uiSkin }
        })

    }

    override fun render(delta: Float) {

        camera.update()
        stage.act(Gdx.graphics.getDeltaTime());

        // Clear screen
        Gdx.gl.glClearColor(0.518f, 0.494f, 0.529f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        /*font.data.setScale(4f)
        font.draw(batch, "IAProject", -15f, 80f)
        font.data.setScale(1f)
        font.draw(batch, "build ${General.BUILD_NB}", 83f, 40f)*/

        stage.draw()

    }

    override fun dispose() {
        stage.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

}