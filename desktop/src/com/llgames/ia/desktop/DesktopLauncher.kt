package com.llgames.ia.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.llgames.ia.IA

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        var config = LwjglApplicationConfiguration().apply {
            title = "IA Desktop"
            width = 1280
            height = 720
        }
        LwjglApplication(IA(), config)
    }
}
