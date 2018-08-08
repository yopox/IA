package com.project.ia.data

import com.badlogic.gdx.Gdx
import com.project.ia.def.JOBS
import com.project.ia.logic.IA
import com.project.ia.logic.LFighter
import com.project.ia.logic.Stats
import org.json.JSONObject


/**
 * Permet de sauvegarder des données et d'accéder aux sauvegardes.
 */
object Save {

    fun saveTeam(team: Team) {
        val isLocAvailable = Gdx.files.isLocalStorageAvailable()
        if (isLocAvailable) {
            val handle = Gdx.files.local("teams/${team.name}.ias")
            handle.writeString(team.serialize(), false)
        }
    }

    fun loadTeam(team: String) : Team {

        val loadedTeam = Team()

        val isLocAvailable = Gdx.files.isLocalStorageAvailable
        if (isLocAvailable) {

            try {
                val handle = Gdx.files.local("teams/$team.ias")

                val json = JSONObject(handle.readString())

                // On recopie les attributs lus dans la sauvegarde
                loadedTeam.name = json.getString("name")

                // Attributs de chaque fighter
                for (i in 0..2) {
                    val objFighter = json.getJSONObject("team").getJSONObject("fighter$i")

                    // Nom
                    loadedTeam.fighters[i].name = objFighter.getString("name")

                    // Classe
                    loadedTeam.fighters[i].changeJob(JOBS.getJob(objFighter.getString("job")))

                    // Equipement
                    loadedTeam.fighters[i].weapon = objFighter.getString("weapon")
                    loadedTeam.fighters[i].spell1 = objFighter.getString("spell1")
                    loadedTeam.fighters[i].spell2 = objFighter.getString("spell2")
                    loadedTeam.fighters[i].relic = objFighter.getString("relic")

                    // IA
                    val rules = objFighter.getString("ia")
                    loadedTeam.fighters[i].changeIA(rules)

                }

            } catch (r: RuntimeException) { println(r) }

        }

        return loadedTeam

    }

}