package com.llgames.ia.data

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonReader
import jdk.nashorn.internal.parser.JSONParser
import org.json.JSONObject
import com.badlogic.gdx.Gdx.files



/**
 * Permet de sauvegarder des données et d'accéder aux sauvegardes.
 */
object Save {

    fun saveTeam(team: Team) {
        val isLocAvailable = Gdx.files.isLocalStorageAvailable()
        if (isLocAvailable) {
            val handle = Gdx.files.local("teams/${team.name}.txt")
            handle.writeString(team.serialize(), false)

            print(team.serialize())

        }
    }

    fun loadTeam(team: String) : Team {

        val loadedTeam = Team()

        val isLocAvailable = Gdx.files.isLocalStorageAvailable()
        if (isLocAvailable) {

            val handle = Gdx.files.local("teams/$team.txt")
            val json = JSONObject(handle.readString())

            // On recopie les attributs lus dans la sauvegarde
            loadedTeam.name = json.getString("name")

            for (i in 0..2) {
                val objFighter = json.getJSONObject("team").getJSONObject("fighter$i")
                loadedTeam.fighters[i].name = objFighter.getString("name")
                print(loadedTeam.fighters[i].name)
            }

        }

        return loadedTeam

    }

}