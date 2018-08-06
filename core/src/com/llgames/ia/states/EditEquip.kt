package com.llgames.ia.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.llgames.ia.IAGame
import com.llgames.ia.data.Team
import com.llgames.ia.logic.LFighter
import ktx.app.KtxScreen
import ktx.scene2d.*

class EditEquip(game: IAGame) : KtxScreen {
    private var stage = Stage()
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(320f, 180f, 360f, 180f, camera)
    private var team = Team()
    private var charTables = mutableListOf<KTableWidget>()

    init {

        stage.viewport = viewport

        val topTable = table {

        }

        stage.addActor(topTable)

    }

    companion object {
        var editedLFighter = LFighter("")
        var editedTeam: Team? = null
    }

    override fun show() {
        super.show()
        Gdx.input.inputProcessor = stage
    }

    override fun hide() {
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

}
