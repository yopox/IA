package com.project.ia.battle

import com.badlogic.gdx.graphics.OrthographicCamera

/**
 * Classe qui gère à la fois la camera pour le viewport de LibGDX, et à la fois les rotations
 * du combat ([angle]).
 */
class Camera : OrthographicCamera() {
    var angle = CAM_1
    var center = arrayOf(viewportWidth / 2, viewportHeight / 2)
    private var nextPos: ArrayList<Double> = ArrayList()

    fun init() {
        center = arrayOf(viewportWidth / 2, viewportHeight / 2)
        position.set(center[0], center[1], 0f)
    }

    override fun update() {
        super.update()
        if (nextPos.isNotEmpty()) {
            angle = nextPos.removeAt(0)
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
        val calculatedPos = when (method) {
            "sin" -> Array(duration) { i -> angle + shift * Math.sin(Math.PI * (i + 1) / (2 * (duration - 1))) }
            "exp" -> Array(duration) { i -> angle + shift * (1 - Math.exp(-5.0 * (i + 1) / (duration - 1))) }
            else -> Array(duration) { i -> angle + shift * (i + 1) / duration }
        }
        nextPos.let {
            it.clear()
            it.addAll(calculatedPos)
            it[it.size - 1] = angle + shift
        }
    }

    companion object {
        private const val MOVE_DURATION = 60
        private const val CAM_1 = 0.55
        private const val CAM_2 = -0.55
    }

}