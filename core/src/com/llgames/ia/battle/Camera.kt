package com.llgames.ia.battle

import com.badlogic.gdx.graphics.OrthographicCamera

/**
 * Created by yopox on 23/08/2017.
 */
class Camera() : OrthographicCamera() {
    var angle = 0.0
    var center = arrayOf(viewportWidth / 2, viewportHeight / 2)
    var nextPos = Array(0, { _ -> 0.0 })

    fun init() {
        center = arrayOf(viewportWidth / 2, viewportHeight / 2)
        position.set(center[0], center[1], 0f)
    }

    override fun update() {
        super.update()
        if (nextPos.isNotEmpty()) {
            angle = nextPos[0]
            val copy = nextPos;
            nextPos = Array(copy.size - 1, { i -> copy[i + 1] })
        }
    }

    infix fun moveTo(destination: String) {
        val bonusShift = Math.PI * (Math.random() - 0.5) / 8
        when (destination) {
            "foe" -> moveCam(CAM_2 - angle + bonusShift, MOVE_DURATION, "sin")
            else -> moveCam(CAM_1 - angle + bonusShift, MOVE_DURATION, "sin")
        }
    }

    private fun moveCam(shift: Double, duration: Int, method: String = "linear") {
        nextPos = when (method) {
            "sin" -> Array(duration, { i -> angle + shift * Math.sin(Math.PI * (i + 1) / (2 * (duration - 1))) })
            "exp" -> Array(duration, { i -> angle + shift * (1 - Math.exp(-5.0 * (i + 1) / (duration - 1))) })
            else -> Array(duration, { i -> angle + shift * (i + 1) / duration })
        }
        nextPos[nextPos.size - 1] = angle + shift
    }

    companion object {
        private const val MOVE_DURATION = 60
        private const val CAM_1 = 0.55
        private const val CAM_2 = -0.55
    }

}