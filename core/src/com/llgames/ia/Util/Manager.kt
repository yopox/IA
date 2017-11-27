package com.llgames.ia.Util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.sun.jmx.remote.internal.ArrayQueue

/**
 * Created by yopox on 27/11/2017.
 */
 class Manager() {
    private var gui = GUI()
    private var console = Console()
    private var frame:Int = 0
    var camera = Camera()

    fun getChars1():Array<Perso> {
        val pos = arrayOf(floatArrayOf(-0.2f, 0.5f),
                    floatArrayOf(0f, 0.5f),
                    floatArrayOf(0.2f, 0.5f))
        return Array(3, { i -> Perso(Texture("char.png"), 16, 32, pos[i][0], pos[i][1]) })
    }

    fun getChars2():Array<Perso> {
        val pos = arrayOf(floatArrayOf(-0.2f, -0.5f),
                    floatArrayOf(0f, -0.5f),
                    floatArrayOf(0.2f, -0.5f))
        return Array(3, { i -> Perso(Texture("char.png"), 16, 32, pos[i][0], pos[i][1]) })
    }

    fun init() {
        camera.init()
    }

    fun update(chars1:Array<Perso>, chars2:Array<Perso>) {

        frame++

        camera.angle = 3.2 + Math.sin(frame/20.0) / 2
        chars1.map { it.updatePos(camera) }
        chars2.map { it.updatePos(camera) }

        camera.update()
        if (frame % 2 == 0){
            console.update()
        }

    }

    fun draw(batch: Batch, font: BitmapFont) {
        gui.draw(batch, font)
        console.draw(batch, font)
    }

}