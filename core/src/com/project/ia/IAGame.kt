package com.project.ia

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.project.ia.states.*
import ktx.app.KtxGame
import ktx.scene2d.Scene2DSkin

/**
 * Created by yopox on 08/12/2017.
 */

class IAGame(val onlineServices: OnlineServices) : KtxGame<Screen>() {

    override fun create() {

        // Valeurs par défaut
        val uiSkin = Skin(Gdx.files.internal("uiskin.json"))
        Scene2DSkin.defaultSkin = uiSkin

        // On ajoute tous les écrans
        addScreen(TitleScreen(this))
        addScreen(EditTeam(this))
        addScreen(EditEquip(this))
        addScreen(EditIA(this))
        addScreen(Battle(this))
        addScreen(Online(this))

        setScreen<TitleScreen>()

    }

}