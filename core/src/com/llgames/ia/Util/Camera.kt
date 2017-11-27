package com.llgames.ia.Util

import com.badlogic.gdx.graphics.OrthographicCamera

/**
 * Created by yopox on 23/08/2017.
 */
class Camera() : OrthographicCamera() {
    var angle = 3.2;
    lateinit var center:Array<Float>

    fun init() {
        center = arrayOf(viewportWidth / 2, viewportHeight / 2)
        position.set(center[0], center[1], 0f)
    }

}