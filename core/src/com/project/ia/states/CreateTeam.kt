package com.project.ia.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.project.ia.IAGame
import com.project.ia.data.Save
import com.project.ia.data.Team
import com.project.ia.def.General
import com.project.ia.def.JOBS
import ktx.app.KtxScreen
import ktx.scene2d.*

/**
 * Game State correspondant à l'écran de création de team.
 */
class CreateTeam(game: IAGame) : KtxScreen {
    private var stage = Stage()
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(320f, 180f, 360f, 180f, camera)
    private var team = Team()
    private var charTables = mutableListOf<KTableWidget>()
    private val charsSprite = Texture("chars.png")
    private var classSprites = mutableListOf<Sprite>()

    companion object {
        var editedTeam = 0
    }

    init {

        stage.viewport = viewport

        val topTable = table {
            label("Create your team") {
                color = General.COLOR4
                it.pad(8f)
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
                    textButton("<") {
                        addListener(object : InputListener() {
                            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                updateTeam()
                                return true
                            }

                            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                team.changeJob(i) { j -> JOBS.previousJob(j) }
                                updateGUI()
                            }
                        })
                    }
                    label(JOBS.getJob("").name) {
                        it.pad(8f)
                        it.width(56f)
                        setAlignment(Align.center)
                        color = General.COLOR4
                    }
                    textButton(">") {
                        addListener(object : InputListener() {
                            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                updateTeam()
                                return true
                            }

                            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                team.changeJob(i) { j -> JOBS.nextJob(j) }
                                updateGUI()
                            }
                        })
                    }
                }
                row()
                classSprites.add(Sprite())
                //image()
                table {
                    pad(8f)
                    label("HP : 0") { it.width(40f) ; color = General.COLOR4 }
                    row()
                    label("SPD : 0") { it.width(40f) ; color = General.COLOR4 }
                    row()
                    label("ATK : 0") { it.width(40f) ; color = General.COLOR4 }
                    row()
                    label("LT : 0") { it.width(40f) ; color = General.COLOR4 }
                    row()
                    label("DK : 0") { it.width(40f) ; color = General.COLOR4 }
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
            row()
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
        })

    }

    /**
     * Permet de mettre à jour les statistiques affichées.
     */
    private fun updateGUI() {

        for (i in 0..2) {
            // Nom
            (charTables[i].children[0] as TextField).text = team.fighters[i].name

            // Job
            ((charTables[i].children[1] as KTableWidget).children[1] as Label).setText(team.fighters[i].job.name)

            // Stats
            team.fighters[i].updateMaxStats()
            ((charTables[i].children[2] as KTableWidget).children[0] as Label).setText("HP: ${team.fighters[i].maxStats.hp}")
            ((charTables[i].children[2] as KTableWidget).children[1] as Label).setText("SPD: ${team.fighters[i].maxStats.spd}")
            ((charTables[i].children[2] as KTableWidget).children[2] as Label).setText("ATK: ${team.fighters[i].maxStats.atk}")
            ((charTables[i].children[2] as KTableWidget).children[3] as Label).setText("LT: ${team.fighters[i].maxStats.lt}")
            ((charTables[i].children[2] as KTableWidget).children[4] as Label).setText("DK: ${team.fighters[i].maxStats.dk}")
        }

    }

    /**
     * Permet de mettre à jour la team.
     */
    private fun updateTeam() {
        for (i in 0..2) {
            team.fighters[i].name = (charTables[i].children[0] as TextField).text
        }
    }

    override fun show() {
        super.show()
        Gdx.input.inputProcessor = stage
        team = Save.loadTeam("team$editedTeam")
        updateGUI()
    }

    override fun hide() {
        updateTeam()
        Save.saveTeam(team)
        super.hide()
    }

    override fun render(delta: Float) {

        camera.update()
        stage.act(Gdx.graphics.deltaTime);

        // Clear screen
        Gdx.gl.glClearColor(General.COLOR1.r, General.COLOR1.g, General.COLOR1.b, 1f)
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