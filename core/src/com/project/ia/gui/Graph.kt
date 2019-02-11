package com.project.ia.gui

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.project.ia.def.General
import com.project.ia.logic.Stats
import kotlin.math.*

class Graph() : Actor() {

    private val verticesSize = 12
    private var vertices = IntArray(verticesSize)
    private var verticesMax = IntArray(verticesSize)
    private var texture: Texture? = null
    private val SIZE = 48f

    init {
        setSize(SIZE, SIZE)
    }

    fun updateTexture(stats: Stats) {
        // Liste des stats
        val statsValue = arrayOf(stats.hp / 4, stats.atk, stats.lt, stats.def, stats.spd, stats.dk)

        for (i in 0..5) {
            val position = min(max(statsValue[i], -20) + 20, 125) / 200.0 * SIZE
            vertices[2 * i] = (SIZE / 2 + position * cos(8 * Math.PI / 12 - i * Math.PI / 3)).roundToInt()
            vertices[2 * i + 1] = (SIZE / 2 - position * sin(8 * Math.PI / 12 - i * Math.PI / 3)).roundToInt()
            verticesMax[2 * i] = (SIZE / 2 + SIZE / 2 * cos(8 * Math.PI / 12 - i * Math.PI / 3)).roundToInt()
            verticesMax[2 * i + 1] = (SIZE / 2 - SIZE / 2 * sin(8 * Math.PI / 12 - i * Math.PI / 3)).roundToInt()
        }

        val pm = Pixmap(SIZE.toInt(), SIZE.toInt(), Pixmap.Format.RGBA8888)


        for (i in 0..5) {
            pm.setColor(General.COLOR2)
            pm.fillTriangle(vertices[(2 * i) % verticesSize],
                    vertices[(2 * i + 1) % verticesSize],
                    (SIZE/2).toInt(),
                    (SIZE/2).toInt(),
                    vertices[(2 * i + 2) % verticesSize],
                    vertices[(2 * i + 3) % verticesSize])
            pm.setColor(General.COLOR4)
            pm.drawLine(verticesMax[(2 * i) % verticesSize],
                    verticesMax[(2 * i + 1) % verticesSize],
                    verticesMax[(2 * i + 2) % verticesSize],
                    verticesMax[(2 * i + 3) % verticesSize])
        }

        texture = Texture(pm)
        pm.dispose()
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(texture, x, y)
        super.draw(batch, parentAlpha)
    }

}