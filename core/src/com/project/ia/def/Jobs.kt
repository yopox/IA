package com.project.ia.def

import com.project.ia.logic.*
import java.util.*

enum class JOB {
    FREELANCE, WARRIOR, ROGUE, PALADIN, PRIEST, NECROMANCER
}

/**
 * Définition des différentes classes.
 * Voir [Job] pour les propriétés d'une classe.
 */
object Jobs {

    private val jobs = mapOf(
            JOB.FREELANCE to Job(
                    JOB.FREELANCE,
                    "Freelance",
                    100,
                    20,
                    5,
                    0,
                    0,
                    25),
            JOB.WARRIOR to Job(
                    JOB.WARRIOR,
                    "Warrior",
                    90,
                    30,
                    10,
                    5,
                    5,
                    20),
            JOB.ROGUE to Job(
                    JOB.ROGUE,
                    "Rogue",
                    75,
                    15,
                    0,
                    0,
                    10,
                    50),
            JOB.PALADIN to Job(
                    JOB.PALADIN,
                    "Paladin",
                    150,
                    18,
                    15,
                    25,
                    -5,
                    25),
            JOB.PRIEST to Job(
                    JOB.PRIEST,
                    "Priest",
                    85,
                    5,
                    5,
                    30,
                    -5,
                    20),
            JOB.NECROMANCER to Job(
                    JOB.NECROMANCER,
                    "Necromancer",
                    85,
                    10,
                    0,
                    -10,
                    30,
                    22))

    fun getJob(job: JOB): Job = jobs[job] ?: jobs[JOB.FREELANCE]!!

    fun getJob(job: String): Job {
        val subJobs = jobs.filter { it.value.name == job }.toList()
        return if (subJobs.any()) subJobs[0].second else jobs[JOB.FREELANCE]!!
    }

    /**
     * Utile depuis l'interface pour changer de job sur un personnage.
     */
    fun nextJob(job: Job): Job {
        val jobValues = JOB.values()
        val index = jobValues.indexOf(job.value)
        val newIndex = if (index == jobValues.lastIndex) 0 else index + 1
        return jobs[jobValues[newIndex]]!!
    }

    fun previousJob(job: Job): Job {
        val jobValues = JOB.values()
        val index = jobValues.indexOf(job.value)
        val newIndex = if (index == 0) jobValues.lastIndex else index - 1
        return jobs[jobValues[newIndex]]!!
    }

    fun randomJob(): Job = getJob(Jobs.jobs.keys.random())

}