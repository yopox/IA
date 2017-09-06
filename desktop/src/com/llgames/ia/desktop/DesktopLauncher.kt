@file:JvmName("DesktopLauncher")

package com.llgames.ia.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.llgames.ia.IA

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration().apply {
        title = "IA Desktop"
        width = 640
        height = 360
    }
    LwjglApplication(IA(), config)
}
