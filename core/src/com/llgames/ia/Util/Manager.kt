package com.llgames.ia.Util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont

/**
 * Created by yopox on 27/11/2017.
 */
 class Manager() {
    private var gui = GUI()
    private var console = Console()
    private var frame:Int = -1
    var camera = Camera()
    var turnManager = Turn()

    fun getChars():Array<Perso> {
        val pos = arrayOf(floatArrayOf(-0.2f, 0.5f),
                    floatArrayOf(0f, 0.5f),
                    floatArrayOf(0.2f, 0.5f),
                    floatArrayOf(-0.2f, -0.5f),
                    floatArrayOf(0f, -0.5f),
                    floatArrayOf(0.2f, -0.5f))
        val textures = arrayOf("char2.png", "char.png")
        return Array(6, { i -> Perso(Texture(textures[i / 3]), 16, 32, pos[i][0], pos[i][1], "Char" + i.toString()  ,i / 3, i % 3) })
    }

    fun init(chars: Array<Perso>) {
        camera.init()
        gui.init(chars)
    }

    fun update(chars:Array<Perso>) {

        frame = (frame + 1) % 120

        if (frame == 0)
            turnManager.update(camera, console, chars)

        camera.update()
        chars.map { it.updatePos(camera) }

        if (frame % 2 == 0){
            console.update()
        }

    }

    fun draw(batch: Batch, font: BitmapFont) {
        gui.draw(batch, font)
        console.draw(batch, font)
    }

}