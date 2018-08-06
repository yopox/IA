package com.llgames.ia.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.llgames.ia.IAGame
import com.llgames.ia.data.Save
import com.llgames.ia.data.Team
import ktx.app.KtxScreen
import ktx.scene2d.*
import com.llgames.ia.def.Runes
import com.llgames.ia.logic.LFighter
import com.llgames.ia.logic.RT
import com.llgames.ia.logic.Rune
import com.llgames.ia.logic.IA


/**
 * Game State correspondant à l'écran d'édition d'IA.
 *
 * Cette classe s'attaque aux difficultés suivantes :
 *
 *  - Pour afficher une règle avec deux conditions, on utilise deux
 *  lignes. L'objet [currentRules] qui s'apparente à [IA] ne peut
 *  donc pas être directement utilisé pour un [LFighter],
 *  cf. [loadCurrentRunes] et [saveCurrentRunes].
 *
 *  - Lorsque l'on modifie une rune ([clickGUI]), il faut potentiellement en ajouter
 *  ou en modifier directement à sa suite.
 *
 *  TODO: Supprimer la règle en cas d'erreur !
 *
 */
class EditIA(game: IAGame) : KtxScreen {
    private var stage = Stage()
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(640f, 360f, 720f, 360f, camera)

    private var mainTable: KTableWidget
    private var selectedRune = Pair(0, 0)
    private var selectedGUI = Pair(0, 0)
    private var guiType = RT.GATE
    private var selectedPage = 0
    private var currentRules = mutableListOf(mutableListOf<Rune>())
    private var complexRules = mutableListOf<Int>()

    private val runesColor: Map<RT, Int> =
            mapOf(RT.GATE to 0xFFFFFF,
                    RT.CONDITION to 0xFFF59D,
                    RT.VALUE to 0x90CAF9,
                    RT.TARGET to 0xA5D6A7,
                    RT.ACTION to 0xEF9A9A,
                    RT.BLANK to 0xFFFFFF,
                    RT.ERROR to 0xB71C1C)
    private val coolGates = arrayOf("ID", "NOT", "")

    init {

        stage.viewport = viewport

        mainTable = table {

            table {

                table {
                    pad(16f)
                    padTop(0f)
                    label("") {
                        setFontScale(2f)
                    }
                    imageTextButton("OK") {
                        addListener(object : InputListener() {
                            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                return true
                            }

                            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                saveCurrentRunes()
                                game.setScreen<EditTeam>()
                            }
                        })
                    }
                }

                row()

                for (i in 0..4) {

                    pad(8f)

                    table {
                        it.pad(8f)
                        it.fillX()
                        left()

                        textButton("/\\") {
                            it.space(4f)
                            addListener(object : InputListener() {
                                override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                    return true
                                }

                                override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                    moveRune("UP", i)
                                }
                            })
                        }
                        textButton("\\/") {
                            it.space(4f)
                            it.spaceRight(8f)
                            addListener(object : InputListener() {
                                override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                    return true
                                }

                                override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                    moveRune("DOWN", i)
                                }
                            })
                        }
                        textButton("X") {
                            it.space(4f)
                            it.spaceRight(8f)
                            addListener(object : InputListener() {
                                override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                    return true
                                }

                                override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                    delRule(i)
                                }
                            })
                        }

                        for (j in 0..6) {
                            imageTextButton("") {
                                isVisible = false
                                touchable = Touchable.disabled
                                it.width(50f)
                                addListener(object : InputListener() {
                                    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                        return true
                                    }

                                    override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                        selectedRune = Pair(i, j)
                                        clickRune()
                                    }
                                })
                            }
                        }

                    }

                    if (i < 4)
                        row()
                }
            }
            table {
                pad(16f)
                padTop(32f)

                for (i in 0..3) {
                    row()
                    table {
                        it.fillX()
                        pad(8f)

                        if (i == 3)
                            padBottom(16f)

                        for (j in 0..1) {
                            imageTextButton("") {
                                it.space(16f)
                                it.spaceRight(32f)
                                it.width(50f)
                                addListener(object : InputListener() {
                                    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                        return true
                                    }

                                    override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                        selectedGUI = Pair(i, j)
                                        clickGUI()
                                    }
                                })
                            }
                        }
                    }
                }

                row()

                table {
                    padTop(16f)
                    it.fillX()

                    textButton("<-") {
                        it.spaceRight(16f)
                        pad(8f, 16f, 8f, 16f)
                        addListener(object : InputListener() {
                            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                return true
                            }

                            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                changePage(-1)
                            }
                        })
                    }
                    textButton("->") {
                        it.spaceLeft(16f)
                        pad(8f, 16f, 8f, 16f)
                        addListener(object : InputListener() {
                            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                return true
                            }

                            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                                changePage(1)
                            }
                        })
                    }
                }
            }
        }

        stage.addActor(mainTable)

    }

    companion object {
        var editedLFighter = LFighter("")
        var editedTeam: Team? = null
    }

    override fun show() {
        super.show()
        Gdx.input.inputProcessor = stage

        val leftTable = (mainTable.children[0] as KTableWidget)
                .children[0] as KTableWidget

        (leftTable.children[0] as Label)
                .setText("Choose IA rules for ${editedLFighter.name} - ")

        selectedRune = Pair(0, 0)
        loadCurrentRunes()
        update()
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

    private fun getITBLeft(line: Int, nb: Int): KImageTextButton {
        return ((mainTable.children[0] as KTableWidget)
                .children[line + 1] as KTableWidget)
                .children[nb + 3] as KImageTextButton
    }

    private fun getITBRight(line: Int, nb: Int): KImageTextButton {
        return ((mainTable.children[1] as KTableWidget)
                .children[line] as KTableWidget)
                .children[nb] as KImageTextButton
    }

    /**
     * Appelé quand on clique sur une des runes du joueur.
     */
    private fun clickRune() {
        update()
    }

    /**
     * Appelé quand on clique sur une rune de l'interface.
     */
    private fun clickGUI() {
        // Modification de [selectedRune]

        val sR = Runes.runes[getITBRight(selectedGUI.first, selectedGUI.second).text.toString()]
                ?: Rune("", RT.ERROR)

        if (selectedRune.first > currentRules.lastIndex) {
            // Ajout d'une règle
            currentRules.add(mutableListOf(sR))
        } else {
            // Modification d'une rune
            currentRules[selectedRune.first][selectedRune.second] = sR
        }

        val cR = currentRules[selectedRune.first]

        // Il faut modifier la règle selon [sR.next]
        for (type in sR.next.reversed()) {
            val wR = workingRules(cR, sR)

            if (wR.none { it.type == type }) {

                // Cas particulier : la condition veut une cible et une valeur et il y a
                // déjà une rune valeur
                if (sR.type == RT.CONDITION
                        && sR.next.size == 2
                        && wR.any { it.type == RT.VALUE }
                        && wR.none { it.type == RT.TARGET }) {

                    cR.add(selectedRune.second + 2, Rune("", type))
                } else {
                    cR.add(selectedRune.second + 1, Rune("", type))
                }
            }
        }

        // Retirer les runes superflues si la rune est invalide
        if (!Runes.isValid(currentRules[selectedRune.first].toTypedArray()) && cR[0].type == RT.GATE && sR.type in arrayOf(RT.CONDITION, RT.ACTION)) {
            // Règle normale
            val toRemove = mutableListOf<Int>()
            val index = selectedRune.second
            val wR = workingRules(cR, sR)
            for (i in 0..wR.lastIndex) {
                if (wR[i].type !in sR.next) {
                    toRemove.add(i + index + 1)
                }
            }
            toRemove.reversed().map { cR.removeAt(it) }
        } else if (cR[0].type == RT.BLANK) {
            // On modifie la seconde condition d'une règle
            val toRemove = mutableListOf<Int>()
            for (i in 2..cR.lastIndex) {
                if (cR[i].type !in cR[1].next) {
                    toRemove.add(i)
                }
            }
            toRemove.reversed().map { cR.removeAt(it) }
        }

        // Édition d'une nouvelle rune
        if (sR.type == RT.GATE && cR.size == 1) {
            cR.add(Rune("", RT.CONDITION))
            cR.add(Rune("", RT.ACTION))
        }

        // Retirer ou ajouter une règle complexe
        if (selectedRune.first + 1 in complexRules) {
            // La règle était complexe
            if (cR[0].id in coolGates) {
                // Elle ne l'est plus
                currentRules.removeAt(selectedRune.first + 1)
            }
        } else if (cR[0].id !in coolGates && selectedRune.first !in complexRules) {
            // La règle est complexe
            currentRules.add(selectedRune.first + 1, mutableListOf(Rune("", RT.BLANK), Rune("", RT.CONDITION)))
        }
        updateComplexRules()
        update()
    }

    private fun workingRules(runes: MutableList<Rune>, sR: Rune): MutableList<Rune> = when (sR.type) {
        RT.CONDITION -> Runes.getCondPart(runes)
        RT.ACTION -> Runes.getActPart(runes)
        else -> runes
    }

    /**
     * Permet de modifier les runes affichées dans l'interface.
     */
    private fun setGUI(type: RT) {
        val runes = Runes.runes.filter { (_, rune) -> rune.type == type }.toList()
        if (guiType != type) {
            selectedPage = 0
            guiType = type
        } else {
            if (runes.size >= 8) {
                selectedPage = (selectedPage + runes.size / 8) % (runes.size / 8)
            } else {
                selectedPage = 0
            }
        }
        for (i in 0..3) {
            for (j in 0..1) {

                val index = 2 * i + j + 8 * selectedPage
                val tib = getITBRight(i, j)

                // On affiche les runes sélectionnables
                val sR = if (currentRules.lastIndex >= selectedRune.first) currentRules[selectedRune.first][selectedRune.second] else Rune("", RT.ERROR)

                // On ne veut pas toujours afficher toutes les portes logiques
                val casParticulier = type == RT.GATE && index > 1 && sR.id in coolGates && (currentRules.lastIndex == 4 || selectedRune.first == 4)
                if (index <= runes.lastIndex && !casParticulier) {
                    setColor(tib, type)
                    tib.text = runes[index].second.id
                    tib.isVisible = true
                } else {
                    tib.isVisible = false
                }
            }
        }
    }

    /**
     * Change la couleur d'un [KImageTextButton] selon le [RT] désiré.
     */
    private fun setColor(tib: KImageTextButton, type: RT) {
        val color = runesColor[type] ?: 0
        val r = (color and 0xFF0000 shr 16) / 255f
        val g = (color and 0xFF00 shr 8) / 255f
        val b = (color and 0xFF) / 255f
        tib.setColor(r, g, b, 1f)
    }

    /**
     * Change la page de runes affichée.
     */
    private fun changePage(i: Int) {
        selectedPage += i
        update()
    }

    /**
     * Permet de supprimer une règle.
     */
    private fun delRule(i: Int) {
        if (currentRules.size > i) {
            currentRules.removeAt(i)
            if (i in complexRules) {
                currentRules.removeAt(i - 1)
            } else if (i + 1 in complexRules) {
                currentRules.removeAt(i)
            }

            updateComplexRules()

            selectedRune = Pair(0, 0)
            update()
        }
    }

    private fun updateComplexRules() {
        complexRules.clear()
        for (i in 0..currentRules.lastIndex) {
            if (currentRules[i][0].type == RT.BLANK)
                complexRules.add(i)
        }
    }

    /**
     * Change la priorité d'une règle.
     */
    private fun moveRune(s: String, i: Int) {
        val cxR = i + 1 in complexRules
        if (s == "UP") {
            val cxRSwitch = i - 1 in complexRules
            if (!cxRSwitch) {
                // On échange avec une règle simple
                val rR = currentRules.removeAt(i - 1)
                if (cxR) {
                    currentRules.add(i + 1, rR)
                } else {
                    currentRules.add(i, rR)
                }
            } else {
                // On échange avec une règle complexe
                val rR2 = currentRules.removeAt(i - 1)
                val rR1 = currentRules.removeAt(i - 2)
                val offset = if (cxR) 1 else 0
                currentRules.add(i - 1 + offset, rR1)
                currentRules.add(i + offset, rR2)
            }
        } else if (s == "DOWN") {
            // On utilise le code du UP sur la rune à échanger
            if (cxR) moveRune("UP", i + 2) else moveRune("UP", i + 1)
        }

        selectedRune = Pair(0, 0)

        updateComplexRules()
        update()
    }

    /**
     * Met à jour les runes du personnages selon [currentRules].
     */
    fun update() {

        for (i in 0..4) {
            for (j in 0..6) {
                val tib = getITBLeft(i, j)
                // On affiche les règles d'IA du personnage
                if (i <= currentRules.lastIndex && j <= currentRules[i].lastIndex && currentRules[i][j].type != RT.BLANK) {
                    tib.isVisible = true
                    tib.text = currentRules[i][j].id
                    setColor(tib, currentRules[i][j].type)
                    tib.touchable = Touchable.enabled
                } else {
                    tib.isVisible = false
                    tib.text = ""
                    tib.touchable = Touchable.disabled
                }
            }
        }

        if (currentRules.lastIndex < 4) {
            val tib = getITBLeft(currentRules.lastIndex + 1, 0)
            tib.isVisible = true
            setColor(tib, RT.GATE)
            tib.touchable = Touchable.enabled
        }

        // MAJ du GUI
        if (currentRules.size > 0 && selectedRune.first <= currentRules.lastIndex) {
            setGUI(currentRules[selectedRune.first][selectedRune.second].type)
        } else {
            setGUI(RT.GATE)
        }


        for (i in 0..4) {
            val table = ((mainTable.children[0] as KTableWidget)
                    .children[i + 1] as KTableWidget)
            val upButton = table.children[0] as KTextButton
            val downButton = table.children[1] as KTextButton
            val delButton = table.children[2] as KTextButton
            val lI = currentRules.lastIndex
            val cR = if (i <= lI) currentRules[i][0] else Rune("", RT.ERROR)
            upButton.isVisible = i in 1..lI && cR.type != RT.BLANK
            downButton.isVisible = i < lI && !(i + 1 == lI && i + 1 in complexRules) && cR.type != RT.BLANK
            delButton.isVisible = i <= lI && cR.type != RT.BLANK
        }

    }

    /**
     * Met à jour [currentRules] selon [editedLFighter].
     */
    private fun loadCurrentRunes() {
        val r = editedLFighter.getIAString()
        val runes = r.split(" - ").map { Runes.fromString(it) }.toMutableList()

        currentRules.clear()
        complexRules.clear()

        for (rule in runes) {

            if (rule[0].id in coolGates) {
                // Cas simple : on ajoute la règle
                currentRules.add(rule.toMutableList())

            } else if (Runes.isValid(rule)) {
                // Cas compliqué : la règle prend deux lignes
                var count = 0
                // On récupère les places des runes destinées à la seconde ligne
                val secondLine = mutableListOf<Int>()
                for (i in 0..rule.lastIndex) {
                    if (rule[i].type == RT.CONDITION)
                        count++
                    if (rule[i].type == RT.ACTION)
                        count--
                    if (count == 2) {
                        secondLine.add(i)
                    }
                }
                currentRules.add(rule.filterIndexed { index, _ -> index !in secondLine }.toMutableList())
                currentRules.add(rule.filterIndexed { index, _ -> index in secondLine }.toMutableList())
                currentRules[currentRules.lastIndex].add(0, Rune("", RT.BLANK))
                complexRules.add(currentRules.lastIndex)
            }
        }

    }

    /**
     * Met à jour l'IA de [editedLFighter] selon [currentRules].
     */
    private fun saveCurrentRunes() {
        val rulesToSave = mutableListOf<MutableList<Rune>>()
        for (i in 0..currentRules.lastIndex) {
            if (i + 1 !in complexRules && i !in complexRules) {
                // Règle simple
                if (currentRules[i].none { it.id == "" })
                    rulesToSave.add(currentRules[i])
            } else if (i !in complexRules) {
                // Règle compliquée : il faut fusionner
                val rule = mutableListOf<Rune>()

                // Porte logique
                rule.add(currentRules[i][0])

                // Condition 1
                val actionIndex = currentRules[i].withIndex().first({ (_, r) -> r.type == RT.ACTION }).index
                for (j in 1..actionIndex - 1)
                    rule.add(currentRules[i][j])

                // Condition 2
                for (j in 1..currentRules[i + 1].lastIndex)
                    rule.add(currentRules[i + 1][j])

                // Reste
                for (j in actionIndex..currentRules[i].lastIndex)
                    rule.add(currentRules[i][j])

                if (rule.none { it.id == "" })
                    rulesToSave.add(rule)
            }
        }

        editedTeam?.let {
            editedLFighter.changeIA(rulesToSave.map { Runes.toString(it.toTypedArray()) }.joinToString(" - "))
            Save.saveTeam(editedTeam!!)
        }

    }

}