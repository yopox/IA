package com.project.ia.def

import com.project.ia.logic.*
import java.util.*

/**
 * Définition des différentes classes.
 * Voir [Job] pour les propriétés d'une classe.
 * TODO: Créer enum et jobs map de l'enum vers [Job]
 */
object JOBS {

    private val jobs = mapOf(
            "FREELANCE" to Job(
                    "FREELANCE",
                    50,
                    20,
                    15,
                    0,
                    0),
            "PRIEST" to Job(
                    "PRIEST",
                    30,
                    15,
                    5,
                    30,
                    0))

    fun getJob(job: String): Job = jobs[job] ?: jobs["FREELANCE"]!!

    /**
     * Utile depuis l'interface pour changer de job sur un personnage.
     */
    fun nextJob(job: Job): Job {
        val keys = jobs.keys.toTypedArray()
        val index = keys.indexOf(job.name)
        val newIndex = if (index == keys.lastIndex) 0 else index + 1
        return jobs[keys[newIndex]]!!
    }

    fun previousJob(job: Job): Job {
        val keys = jobs.keys.toTypedArray()
        val index = keys.indexOf(job.name)
        val newIndex = if (index == 0) keys.lastIndex else index - 1
        return jobs[keys[newIndex]]!!
    }

    fun randomJob(): Job = getJob(JOBS.jobs.keys.elementAt(Random().nextInt(JOBS.jobs.size)))

}