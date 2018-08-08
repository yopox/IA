package com.project.ia.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.project.ia.IAGame
import com.project.ia.def.General
import ktx.app.KtxScreen
import ktx.scene2d.*


/**
 * Game State correspondant à l'écran titre.
 */
class TitleScreen(game: IAGame) : KtxScreen {
    private val stage = Stage()
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(320f, 180f, 360f, 180f, camera)

    init {

        stage.viewport = viewport

        val maintable = table {
            table {
                pad(16f)
                label("IAProject") {
                    setFontScale(4f)
                    color = Color.BLACK
                }
                row()
                label("build ${General.BUILD_NB}") {
                    it.right()
                    color = Color.BLACK
                }
            }
            row()
            table {
                pad(16f)
                textButton("Edit team") {
                    pad(8f)
                    it.spaceRight(16f)
                    addListener(object : InputListener() {
                        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                            return true
                        }

                        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                            game.setScreen<EditTeam>()
                        }
                    })
                }
                textButton("Play") {
                    pad(8f)
                    it.spaceRight(16f)
                    addListener(object : InputListener() {
                        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                            return true
                        }

                        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                            game.setScreen<Battle>()
                        }
                    })
                }
                textButton("Online") {
                    pad(8f)
                }
            }
        }

        stage.addActor(maintable)

    }

    override fun show() {
        super.show()
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {

        camera.update()
        stage.act(Gdx.graphics.deltaTime);

        // Clear screen
        Gdx.gl.glClearColor(0.518f, 0.494f, 0.529f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.draw()

    }

    override fun dispose() {
        stage.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

}