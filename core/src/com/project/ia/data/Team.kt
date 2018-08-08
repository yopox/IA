package com.project.ia.data

import com.project.ia.def.JOBS
import com.project.ia.def.Runes
import com.project.ia.logic.Job
import com.project.ia.logic.LFighter

/**
 * Définit une équipe de personnages.
 * TODO: [name] ne doit pas contenir de "
 */
class Team {

    var name = "main_team"

    var fighters = mutableListOf<LFighter>(
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
            str += """   "weapon": "${fighters[i].weapon}", """ + "\n"
            str += """   "spell1": "${fighters[i].spell1}", """ + "\n"
            str += """   "spell2": "${fighters[i].spell2}", """ + "\n"
            str += """   "relic": "${fighters[i].relic}", """ + "\n"
            str += """   "ia": "${fighters[i].getIAString()}"""" + "\n  }" + if (i < 2) ",\n" else "\n"
        }

        str += " }\n}"

        return str
    }

}