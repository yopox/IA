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
    private var frame:Int = 0
    var camera = Camera()

    fun getChars():Array<Perso> {
        val pos = arrayOf(floatArrayOf(-0.2f, 0.5f),
                    floatArrayOf(0f, 0.5f),
                    floatArrayOf(0.2f, 0.5f),
                    floatArrayOf(-0.2f, -0.5f),
                    floatArrayOf(0f, -0.5f),
                    floatArrayOf(0.2f, -0.5f))
        val textures = arrayOf("char2.png", "char.png")
        return Array(6, { i -> Perso(Texture(textures[i / 3]), 16, 32, pos[i][0], pos[i][1]) })
    }

    fun init() {
        camera.init()
    }

    fun update(chars:Array<Perso>) {

        frame = (frame + 1) % 75

        if (frame == 1) {
            if (Math.random() > 0.5) camera moveTo "foe" else camera moveTo "ally"
            console.writeText(Math.random().toString())
        }

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