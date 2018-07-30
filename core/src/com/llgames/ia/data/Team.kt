package com.llgames.ia.data

import com.llgames.ia.def.JOBS
import com.llgames.ia.def.Runes
import com.llgames.ia.logic.Job
import com.llgames.ia.logic.LFighter

/**
 * Définit une équipe de trois personnages.
 * TODO: [name] ne doit pas contenir de "
 */
class Team {

    var name = "main_team"

    val fighters = arrayOf(
            LFighter("Bartz"),
            LFighter("Faris", id = 1),
            LFighter("Galuf", id = 2))


    /**
     * Change le job du fighter [nfi] dans la direction [dir]
     */
    fun changeJob(nfi: Int, dir: (Job) -> Job) {
        fighters[nfi].changeJob(dir(fighters[nfi].job))
    }

    fun serialize() : String {
        var str = "{\n"

        str += """ "name": "$name", """ + "\n"
        str += """ "team": { """ + "\n"

        for (i in 0..2) {
            str += """  "fighter$i": { """ + "\n"
            str += """   "name": "${fighters[i].name}", """ + "\n"
            str += """   "job": "${fighters[i].job.name}", """ + "\n"
            str += """   "equip": {}, """ + "\n"
            str += """   "ia": "${fighters[i].serializeIA()}"  }""" + if (i < 2) ",\n" else "\n"
        }

        str += " }\n}"

        return str
    }

}