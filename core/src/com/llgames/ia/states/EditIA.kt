package com.llgames.ia.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.llgames.ia.IAGame
import ktx.app.KtxScreen
import ktx.scene2d.*
import com.badlogic.gdx.utils.Align
import com.llgames.ia.data.Editor
import com.llgames.ia.def.Runes
import com.llgames.ia.logic.RT


/**
 * Game State correspondant à l'écran d'édition d'IA.
 */
class EditIA(game: IAGame) : KtxScreen {
    private var stage = Stage()
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(640f, 360f, 720f, 360f, camera)

    private var mainTable: KTableWidget

    private val runesColor: Map<RT, Triple<Float, Float, Float>> =
            mapOf(RT.GATE to Triple(0.75f, 0.75f, 0.75f),
                    RT.CONDITION to Triple(1f, 1f, 0.5f),
                    RT.VALUE to Triple(1f, 0.75f, 1f),
                    RT.TARGET to Triple(0.75f, 0.75f, 1f),
                    RT.ACTION to Triple(1f, 0.9f, 0.6f),
                    RT.ERROR to Triple(0f, 0f, 0f))

    init {

        stage.viewport = viewport

        mainTable = table {

            table {
                pad(16f)
                label("") {
                    setFontScale(2f)
                }
                imageTextButton("OK") {
                    addListener(object : InputListener() {
                        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                            return true
                        }

                        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                            game.setScreen<EditTeam>()
                        }
                    })
                }
            }

            row()

            for (i in 0..4) {

                pad(8f)

                table {
                    debug()
                    it.pad(8f)
                    it.fillX()
                    left()

                    for (j in 0..6) {
                        imageTextButton("") {
                            isVisible = false
                        }
                    }
                }

                if (i < 4)
                    row()
            }
        }

        stage.addActor(mainTable)

    }

    override fun show() {
        super.show()
        Gdx.input.inputProcessor = stage

        ((mainTable.children[0] as KTableWidget)
                .children[0] as Label)
                .setText("Choose IA rules for ${Editor.editedLFighter.name} - ")

        val r = Editor.editedLFighter.getIAString()
        val runes = r.split(" - ").map { Runes.fromString(it) }.toMutableList()

        for (i in 0..runes.lastIndex) {
            for (j in 0..runes[i].lastIndex) {
                val tib = (mainTable.children[i+1]
                        as KTableWidget).children[j]
                        as KImageTextButton
                tib.isVisible = true
                val color = runesColor[runes[i][j].type]!!
                tib.setColor(color.first, color.second, color.third, 1f)
                tib.text = runes[i][j].id
            }
        }

        if (runes.lastIndex < 4) {
            val tib = (mainTable.children[runes.lastIndex + 2]
                    as KTableWidget).children[0]
                    as KImageTextButton
            tib.isVisible = true
            val color = runesColor[RT.GATE]!!
            tib.setColor(color.first, color.second, color.third, 1f)
        }

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