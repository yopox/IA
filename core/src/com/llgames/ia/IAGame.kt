package com.llgames.ia

import com.badlogic.gdx.Screen
import com.llgames.ia.states.Battle
import com.llgames.ia.states.TitleScreen
import ktx.app.KtxGame

/**
 * Created by yopox on 08/12/2017.
 */

class IAGame : KtxGame<Screen>() {
    override fun create() {

        // On ajoute tous les states
        addScreen(TitleScreen())
        addScreen(Battle())

        setScreen<TitleScreen>()
    }

}