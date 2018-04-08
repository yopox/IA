package com.llgames.ia.battle

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.viewport.FitViewport

/**
 * Created by yopox on 27/11/2017.
 */

data class State(var turn: Int = 1, var frame: Int = -1, var charTurn: Int = 0)

class Manager() {
    private var gui = GUI()
    private var console = Console()
    private var state: State = State()
    var camera = Camera()
    var turnManager = Turn()
    val viewport = FitViewport(320f, 180f, camera)

    fun init(fighters: Array<Fighter>) {
        viewport.apply()
        camera.init()
        gui.init(fighters)
        turnManager.newTurn(fighters, state)
    }

    fun update(fighters: Array<Fighter>) {

        // Update frame
        state.frame = state.frame + 1
        if (state.frame == 180) {
            state.frame = 0
            //TODO: Handle turn order
            state.charTurn = (state.charTurn + 1)
            if (state.charTurn == fighters.size) {
                state.charTurn = 0
                state.turn++
            }
            turnManager.newTurn(fighters, state)
        }

        // Frame subactions
        turnManager.update(state.frame, camera, console, fighters, gui)

        // Update components
        camera.update()
        fighters.map { it.updatePos(camera) }
        if (state.frame % 2 == 0) {
            console.update()
        }

    }

    fun draw(batch: Batch, font: BitmapFont) {
        gui.draw(batch, font)
        console.draw(batch, font)
    }

    fun debug(batch: Batch, font: BitmapFont, fighters: Array<Fighter>) {
        gui.debug(batch, font, camera, fighters, state)
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

}