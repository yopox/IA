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
import ktx.scene2d.label
import ktx.scene2d.table
import ktx.scene2d.textButton

class Online(game: IAGame) : KtxScreen {
    private val stage = Stage()
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(320f, 180f, 360f, 180f, camera)

    init {

        stage.viewport = viewport

        val maintable = table {
            table {
                pad(16f)
                label("Online - ")
                textButton("Done") {
                    pad(4f)
                    addListener(object : InputListener() {
                        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                            return true
                        }

                        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                            game.setScreen<TitleScreen>()
                        }
                    })
                }
            }
            row()
            table {
                pad(16f)

                textButton("SHARE") {
                    pad(16f)
                    addListener(object : InputListener() {
                        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                            return true
                        }

                        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                            game.onlineServices.sendTeam()
                        }
                    })
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