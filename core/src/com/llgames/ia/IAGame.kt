package com.llgames.ia

import com.badlogic.gdx.Screen
import com.llgames.ia.states.Battle
import ktx.app.KtxGame

/**
 * Created by yopox on 08/12/2017.
 */

class IAGame : KtxGame<Screen>() {
    override fun create() {

        // On ajoute tous les states
        addScreen(Battle())

        setScreen<Battle>()
    }

}