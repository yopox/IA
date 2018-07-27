package com.llgames.ia.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextArea
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.llgames.ia.IAGame
import com.llgames.ia.data.Save
import com.llgames.ia.data.Team
import com.llgames.ia.def.JOBS
import ktx.app.KtxScreen
import ktx.scene2d.*

/**
 * Game State correspondant à l'écran de création de team.
 */
class BuildTeam(game : IAGame) : KtxScreen {
    private val font = BitmapFont(Gdx.files.internal("softsquare.fnt"), false)
    private var stage = Stage()
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(320f, 180f, 360f, 180f, camera)
    private var team = Team()
    private var charTables = mutableListOf<KTableWidget>()

    init {

        font.color = Color.BLACK

        stage.viewport = viewport

        val topTable = table {
            label("Edit your team (it will be saved)  -  ")
            textButton("DONE") {
                it.spaceRight(8f)
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

        for (i in 0..2) {

            charTables.add(table {
                width = 96f
                pad(4f)
                textField("Perso 1") {
                    maxLength = 9
                    it.width(64f)
                }
                row()
                table {
                    textButton("<")
                    label(JOBS.HUMAN.name) {
                        it.pad(8f)
                        it.width(56f)
                        setAlignment(Align.center)
                    }
                    textButton(">")
                }
                row()
                table {
                    pad(8f)
                    label("HP : 79") { it.width(40f) }
                    label("SPD : 17") { it.width(40f) }
                    row()
                    label("ATK : 31") { it.width(40f) }
                    label("DEF : 07") { it.width(40f) }
                }
                row()
                textButton("EQUIP") {
                    it.spaceBottom(8f)
                    pad(0f, 8f, 0f, 8f)
                }
                row()
                textButton("IA") {
                    it.spaceTop(8f)
                    pad(0f, 16f, 0f, 16f)
                }
            })
        }

        stage.addActor(table {
            appendActor(topTable)
            row()
            table {
                appendActor(charTables[0])
                appendActor(charTables[1])
                appendActor(charTables[2])
            }
        })

    }

    override fun show() {
        super.show()
        Gdx.input.inputProcessor = stage
        team = Save.loadTeam("main_team")

        // On relie les éléments visuels à la team en cours d'édition
        for (i in 0..2) {
            (charTables[i].children[0] as TextField).text = team.fighters[i].name
        }
    }

    override fun hide() {
        super.hide()

        // On lit les éléments visuels
        for (i in 0..2) {
            team.fighters[i].name = (charTables[i].children[0] as TextField).text
        }

        Save.saveTeam(team)
    }

    override fun render(delta: Float) {

        camera.update()
        stage.act(Gdx.graphics.getDeltaTime());

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