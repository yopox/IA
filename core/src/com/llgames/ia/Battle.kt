package com.llgames.ia

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.profiling.GLProfiler
import com.llgames.ia.battle.Manager
import com.llgames.ia.battle.Fighter
import com.llgames.ia.battle.logic.JOBS
import com.llgames.ia.gif.GifRecorder
import ktx.app.KtxScreen

/**
 * Game State correspondant aux combats.
 * [Battle] crée les fighters et gère l'affichage mais laisse [Manager] gérer le combat.
 * TODO: Bouton accélérer
 * TODO: Bouton pause
 */
class Battle : KtxScreen {
    private val batch = SpriteBatch()
    private val bg = Texture("Cliffs.gif")
    private val fighters: Array<Fighter>
    private val manager: Manager
    private val font = BitmapFont(Gdx.files.internal("softsquare.fnt"), false)
    private val recorder = GifRecorder(batch)

    init {
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        // Fighters positions
        val pos = arrayOf(floatArrayOf(-0.2f, -0.45f),
                floatArrayOf(0f, -0.45f),
                floatArrayOf(0.2f, -0.45f),
                floatArrayOf(-0.2f, 0.45f),
                floatArrayOf(0f, 0.45f),
                floatArrayOf(0.2f, 0.45f))
        //TODO: Find better names
        val names = arrayListOf("yopox", "B", "C", "D", "E", "F")

        // Fighters creation
        fighters = Array(6, { i -> Fighter(pos[i][0], pos[i][1], names[i], i / 3, i) })

        fighters.forEach {
            it.apply {
                val jobs = arrayListOf(
                        JOBS.DARKMAGE, JOBS.HUMAN, JOBS.MONK, JOBS.WHITEMAGE,
                        JOBS.PALADIN, JOBS.THIEF, JOBS.SAMURAI, JOBS.BERSERKER,
                        JOBS.TIMEMAGE)
                changeJob(jobs.shuffled().first())
                setIA()
                prepare()
            }
        }
        fighters.sortByDescending { it.stats.spd }

        manager = Manager().apply { init(fighters) }

    }

    override fun render(delta: Float) {

        // Update
        manager.update(fighters)

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Prepare for drawing
        batch.projectionMatrix = manager.camera.combined
        batch.begin()

        // Draw Background
        batch.draw(bg, -192f, -90f + 39f, (80 * manager.camera.angle).toInt() - 20, 16, 384, 102)

        // Draw GUI
        manager.draw(batch, font)

        // Draw fighters
        val fcopy = fighters.toMutableList()
        fcopy.sortByDescending { it.sprite.y }
        fcopy.map { it.drawChar(batch, manager.camera) }

        // Draw IA and turn number
        manager.debug(batch, font, fighters)

        batch.end()
        recorder.update()

    }

    override fun dispose() {
        batch.dispose()
        bg.dispose()
    }

    override fun resize(width: Int, height: Int) {
        manager.resize(width, height)
    }

}