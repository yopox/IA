package com.project.ia.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.project.ia.IAGame
import com.project.ia.battle.*
import com.project.ia.data.Save
import com.project.ia.def.Behavior
import com.project.ia.def.JOBS
import ktx.app.KtxScreen

data class BattleState(var turn: Int = 1, var frame: Int = -1, var charTurn: Int = 0, var winner: Int = -1) {
    fun newTurn() {
        charTurn = 0
        turn++
    }
}

/**
 * Game State correspondant aux combats.
 * [Battle] gère l'affichage et le déroulement du combat.
 *
 * TODO: Bouton accélérer
 * TODO: Bouton pause
 * TODO: Animation en fin/début de tour ?
 */
class Battle(private val game: IAGame) : KtxScreen {

    private val batch = SpriteBatch()
    private val bg = Texture("Colosso.gif")
    private val font = BitmapFont(Gdx.files.internal("fonts/softsquare.fnt"), false)
    private val IAfont = BitmapFont(Gdx.files.internal("fonts/skullboy.fnt"), false)

    private var camera = Camera()
    private val viewport = ExtendViewport(320f, 180f, 360f, 180f, camera)
    private var console = Console()
    private var gui = GUI()

    private var turnManager = Turn()
    private var bState: BattleState = BattleState()
    private var fighters = arrayOf<Fighter>()
    private var pos: Array<FloatArray>

    init {

        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        // Fighters positions
        pos = arrayOf(floatArrayOf(-0.2f, -0.45f),
                floatArrayOf(0f, -0.45f),
                floatArrayOf(0.2f, -0.45f),
                floatArrayOf(-0.2f, 0.45f),
                floatArrayOf(0f, 0.45f),
                floatArrayOf(0.2f, 0.45f))

        viewport.apply()
        camera.init()
    }

    /**
     * Affichage de l'état : création des [Fighter] et MAJ du GUI.
     */
    override fun show() {

        super.show()

        // Création de l'équipe du joueur
        val team = Save.loadTeam("main_team")
        val teamPlayer = Team(0)
        teamPlayer.import(team)

        // Création de l'équipe adverse
        val teamEnemy = Team(1)
        teamEnemy.import(getEnemies())

        // Ajout des [Fighter] dans [fighters]
        val tempTeam = mutableListOf<Fighter>()
        teamPlayer.fighters.map { tempTeam.add(it) }
        teamEnemy.fighters.map { tempTeam.add(it) }
        fighters = tempTeam.toTypedArray()

        // Préparation au combat
        fighters.map { it.resetStats(true) }
        fighters.sortByDescending { it.stats.spd }

        // Mise à jour des éléments visuels
        gui.setFighters(fighters)
        bState = BattleState()

        // Début du premier tour
        turnManager.play(fighters, bState)

    }

    /**
     * Renvoie une [com.llgames.ia.data.Team] d'adversaires.
     * TODO: Gérer les équipements
     */
    private fun getEnemies(): com.project.ia.data.Team {
        val team = com.project.ia.data.Team()

        // Liste de noms inspirés de Twitter
        val names =
                mutableListOf<String>("Kili", "Helo", "Harka", "Arupal", "Val",
                        "Jean", "Paul", "ZerO", "Xen9", "Luc", "Alex", "Jean", "Sam",
                        "Psoukh", "Klaf", "Mesho", "Hiba", "Clemba", "Lou", "Ixo", "Tib")

        // Nom, IA et Job aléatoire
        for (i in 0..2) {
            team.fighters[i].name = names.shuffled().first()
            team.fighters[i].changeIA(Behavior.getRandom())
            team.fighters[i].changeJob(JOBS.randomJob())
        }

        return team
    }

    override fun render(delta: Float) {

        // Update
        update(fighters)

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Prepare for drawing
        batch.projectionMatrix = camera.combined
        batch.begin()

        // Draw Background
        batch.draw(bg, -192f, -90f + 39f, (80 * camera.angle).toInt() - 20, 16, 384, 102)

        // Draw GUI
        gui.draw(batch, font)
        console.draw(batch, font)

        // Draw fighters
        val fcopy = fighters.toMutableList()
        fcopy.sortByDescending { it.sprite.y }
        fcopy.map { it.drawChar(batch, camera, IAfont) }

        // Draw IA and turn number
        debug(batch, IAfont, fighters)

        batch.end()

    }

    override fun dispose() {
        batch.dispose()
        bg.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    private fun update(fighters: Array<Fighter>) {

        // Update frame
        bState.frame = bState.frame + 1
        if (bState.frame == 120) {
            bState.frame = 0

            if (!checkWin(fighters)) {

                do {

                    bState.charTurn++

                    if (bState.charTurn == fighters.size) {
                        bState.newTurn()
                        fighters.sortByDescending { it.stats.spd }
                    }

                    if (!fighters[bState.charTurn].alive)
                        fighters[bState.charTurn].endTurn()

                } while (!fighters[bState.charTurn].alive)

                turnManager.play(fighters, bState)

            } else {
                game.setScreen<TitleScreen>()
            }

        }

        // Frame subactions
        if (bState.winner < 0)
            turnManager.update(bState.frame, camera, console, fighters, gui)

        // Update components
        camera.update()
        fighters.forEach { it.updatePos(camera) }
        if (bState.frame % 2 == 0) {
            console.update()
        }

    }

    /**
     * Vérifie si le combat est terminé.
     */
    private fun checkWin(fighters: Array<Fighter>): Boolean {
        if (fighters.none { it.team == 0 && it.alive }) {
            bState.winner = 1
            return true
        } else if (fighters.none { it.team == 1 && it.alive }) {
            bState.winner = 0
            return true
        }
        return false
    }

    fun debug(batch: Batch, font: BitmapFont, fighters: Array<Fighter>) {
        gui.debug(batch, font, fighters, bState)
    }

}