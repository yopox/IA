package com.llgames.ia

import com.badlogic.gdx.Screen
import ktx.app.KtxGame

/**
 * Created by yopox on 08/12/2017.
 */

class IAGame : KtxGame<Screen>() {
    override fun create() {
        // Registering ExampleScreen in the game object: it will be
        // accessible through ExampleScreen class:
        addScreen(Battle())
        // Changing current screen to the registered instance of the
        // ExampleScreen class:
        setScreen<Battle>()
    }

}