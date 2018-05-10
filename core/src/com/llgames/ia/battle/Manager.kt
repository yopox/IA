package com.llgames.ia.battle

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.viewport.ExtendViewport

data class State(var turn: Int = 1, var frame: Int = -1, var charTurn: Int = 0, var winner: Int = -1) {
    fun newTurn() {
        charTurn = 0
        turn++
    }
}

/**
 * Gère l'état et l'avancement du combat.
 * Vue : ExtendViewport (origine centre de l'écran, repère "usuel")
 */

class Manager {
    private var gui = GUI()
    private var console = Console()
    private var state: State = State()
    private var turnManager = Turn()
    var camera = Camera()
    private val viewport = ExtendViewport(320f, 180f,384f, 180f, camera)

    fun init(fighters: Array<Fighter>) {
        viewport.apply()
        camera.init()
        gui.init(fighters)
        turnManager.play(fighters, state)
    }

    fun update(fighters: Array<Fighter>) {

        // Update frame
        state.frame = state.frame + 1
        if (state.frame == 180) {
            state.frame = 0

            if (!checkWin(fighters)) {

                // Prochain combattant en vie
                do {
                    state.charTurn++
                    if (state.charTurn == fighters.size) {
                        state.newTurn()
                        fighters.sortByDescending { it.stats.spd }
                    }
                } while (!fighters[state.charTurn].alive)

                turnManager.play(fighters, state)

            } else {
                //TODO: Fin du combat
                print("Fight ended. Team #${state.winner} won!\n")
            }

        }

        // Frame subactions
        if (state.winner < 0)
            turnManager.update(state.frame, camera, console, fighters, gui)

        // Update components
        camera.update()
        fighters.map { it.updatePos(camera) }
        if (state.frame % 2 == 0) {
            console.update()
        }

    }

    /**
     * Vérifie si le combat est terminé.
     */
    private fun checkWin(fighters: Array<Fighter>): Boolean {
        if (fighters.none { it.team == 0 && it.alive }) {
            state.winner = 1
            return true
        } else if (fighters.none { it.team == 1 && it.alive }) {
            state.winner = 0
            return true
        }
        return false
    }

    fun draw(batch: Batch, font: BitmapFont) {
        gui.draw(batch, font)
        console.draw(batch, font)
    }

    fun debug(batch: Batch, font: BitmapFont, fighters: Array<Fighter>) {
        gui.debug(batch, font, fighters, state)
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

}