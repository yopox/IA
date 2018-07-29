package com.llgames.ia

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.llgames.ia.states.Battle
import com.llgames.ia.states.EditTeam
import com.llgames.ia.states.EditIA
import com.llgames.ia.states.TitleScreen
import ktx.app.KtxGame
import ktx.scene2d.Scene2DSkin

/**
 * Created by yopox on 08/12/2017.
 */

class IAGame : KtxGame<Screen>() {
    override fun create() {

        // Valeurs par défaut
        val uiSkin = Skin(Gdx.files.internal("uiskin.json"))
        Scene2DSkin.defaultSkin = uiSkin

        // On ajoute tous les écrans
        addScreen(TitleScreen(this))
        addScreen(EditTeam(this))
        addScreen(EditIA(this))
        addScreen(Battle())

        setScreen<TitleScreen>()

    }

}