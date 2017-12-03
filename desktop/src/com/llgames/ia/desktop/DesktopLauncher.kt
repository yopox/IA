@file:JvmName("DesktopLauncher")

package com.llgames.ia.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.llgames.ia.Main

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration().apply {
        title = "Main Desktop"
        width = 640
        height = 360
    }
    LwjglApplication(Main(), config)
}
