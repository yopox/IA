package com.project.ia.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.project.ia.IAGame
import com.project.ia.data.Save
import com.project.ia.data.Team
import com.project.ia.def.Equip
import com.project.ia.logic.ELEMENTS
import com.project.ia.logic.LFighter
import ktx.app.KtxScreen
import ktx.scene2d.*

/**
 * Game State correspondant à l'écran d'édition de l'équipement des personnages.
 */
class EditEquip(game: IAGame) : KtxScreen {
    private var stage = Stage()
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(320f, 180f, 360f, 180f, camera)
    private var mainTable: KTableWidget

    init {

        stage.viewport = viewport

        mainTable = table {

            table {
                label("")
                textButton("DONE") {
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

            table {

                val labelNames= listOf("WEAPON", "SPELL 1", "SPELL 2", "RELIC")

                table {
                    pad(8f)
                    for (i in 0..3) {
                        table {
                            width = 300f
                            pad(2f)
                            label(labelNames[i]) {
                                it.padRight(8f)
                                it.width(32f)
                                setAlignment(Align.center)
                            }
                            textButton("<") {
                                addListener(object : InputListener() {
                                    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                        return true
                                    }

                                    override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                        moveCursor(i, -1)
                                    }
                                })
                            }
                            label("None") {
                                it.width(112f)
                                it.pad(0f, 8f, 0f, 8f)
                                it.expand(true, false)
                                setAlignment(Align.center)
                            }
                            textButton(">") {
                                addListener(object : InputListener() {
                                    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                        return true
                                    }

                                    override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                        moveCursor(i, 1)
                                    }
                                })
                            }
                        }
                        row()
                    }
                }

                table {
                    pad(8f)
                    it.width(96f)
                    label("  HP: 0 ATK: 0")
                    row()
                    label("  LT: 75 DK: 20")
                    row()
                    label(" SPD: 100")
                }

            }

        }

        stage.addActor(mainTable)

    }

    companion object {
        var editedLFighter = LFighter("")
        var editedTeam: Team = Team()
        var cursor = mutableListOf(0, 0, 0, 0)
    }

    override fun show() {
        super.show()
        Gdx.input.inputProcessor = stage

        val table = mainTable.children[0] as KTableWidget

        (table.children[0] as Label)
                .setText("Choose equipment for ${EditEquip.editedLFighter.name} - ")

        update()

    }

    override fun hide() {
        saveEquip()
        super.hide()
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

    private fun moveCursor(i: Int, i1: Int) {
        cursor[i] += i1

        val limit = when(i1) {
            0 -> Equip.WEAPONS.size
            else -> 0
        }

        if (cursor[i] == limit) cursor[i] = 0

        val table = ((mainTable.children[1] as KTableWidget)
                                    .children[0] as KTableWidget)
                                    .children[i] as KTableWidget

        if (i == 0) {
            // On change l'arme du perso
            val keys = Equip.WEAPONS.keys.toList()
            val index = keys.indexOf(editedLFighter.weapon)

            if (index == -1)
                editedLFighter.weapon = keys[0]
            else {
                editedLFighter.weapon = keys[(index + i1 + keys.size) % keys.size]
            }

            (table.children[2] as Label).setText(Equip.weaponDesc(editedLFighter.weapon))

        } else if (i == 1) {
            // On change un sort du perso
            val keys = Equip.SPELLS.keys.toList()
            val index = keys.indexOf(editedLFighter.spell1)

            if (index == -1)
                editedLFighter.spell1 = keys[0]
            else {
                editedLFighter.spell1 = keys[(index + i1 + keys.size) % keys.size]
            }

            (table.children[2] as Label).setText(Equip.spellDesc(editedLFighter.spell1))

        } else if (i == 2) {
            // On change un sort du perso
            val keys = Equip.SPELLS.keys.toList()
            val index = keys.indexOf(editedLFighter.spell2)

            if (index == -1)
                editedLFighter.spell2 = keys[0]
            else {
                editedLFighter.spell2 = keys[(index + i1 + keys.size) % keys.size]
            }

            (table.children[2] as Label).setText(Equip.spellDesc(editedLFighter.spell2))

        } else if (i == 3) {
            // On change la relique du perso
            val keys = Equip.RELICS.keys.toList()
            val index = keys.indexOf(editedLFighter.relic)

            if (index == -1)
                editedLFighter.relic = keys[0]
            else {
                editedLFighter.relic = keys[(index + i1 + keys.size) % keys.size]
            }

            (table.children[2] as Label).setText(Equip.relicDesc(editedLFighter.relic))
        }

        update()

    }

    private fun saveEquip() {
        Save.saveTeam(editedTeam)
    }

    /**
     * Met à jour les caractéristiques affichées.
     */
    private fun update() {

        val tableEquip = ((mainTable.children[1] as KTableWidget).children[0] as KTableWidget)

        ((tableEquip.children[0] as KTableWidget).children[2] as Label).setText(Equip.weaponDesc(editedLFighter.weapon))
        ((tableEquip.children[1] as KTableWidget).children[2] as Label).setText(Equip.spellDesc(editedLFighter.spell1))
        ((tableEquip.children[2] as KTableWidget).children[2] as Label).setText(Equip.spellDesc(editedLFighter.spell2))
        ((tableEquip.children[3] as KTableWidget).children[2] as Label).setText(Equip.relicDesc(editedLFighter.relic))

        val table = (mainTable.children[1] as KTableWidget).children[1] as KTableWidget

        // MAJ des STAT de [editedLFighter]
        editedLFighter.updateMaxStats()

        val stats = editedLFighter.maxStats
        (table.children[0] as Label)
                .setText("  HP: ${stats.hp} ATK: ${stats.atk}")
        (table.children[1] as Label)
                .setText("  LT: ${stats.lt} DK: ${stats.dk}")
        (table.children[2] as Label)
                .setText(" SPD: ${stats.spd}")
    }

}
