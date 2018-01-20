@file:JvmName("DesktopLauncher")

package com.llgames.ia.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.llgames.ia.SelectIA

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration().apply {
        title = "Battle Desktop"
        width = 640
        height = 360
    }
    LwjglApplication(SelectIA(), config)
}
