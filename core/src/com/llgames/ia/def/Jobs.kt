package com.llgames.ia.def

import com.llgames.ia.logic.*

/**
 * Définition des différentes classes.
 * Voir [Job] pour les propriétés d'une classe.
 */
object JOBS {

    private val jobs = mapOf(
            "DARK MAGE" to Job(
                    "DARK MAGE",
                    18,
                    12,
                    atkModif = ComplexStat(10,
                            mutableMapOf<TYPES, Int>(TYPES.MAGICAL to 5),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.FIRE to 5, ELEMENTS.ICE to 5)),
                    defModif = ComplexStat(-5),
                    yPos = 336),

            "HUMAN" to Job(
                    "HUMAN",
                    20,
                    15,
                    atkModif = ComplexStat(10,
                            mutableMapOf<TYPES, Int>(TYPES.MAGICAL to 5),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.FIRE to 5, ELEMENTS.ICE to 5)),
                    defModif = ComplexStat(-5)),

            "MONK" to Job(
                    "MONK",
                    45,
                    20,
                    atkModif = ComplexStat(10,
                            mutableMapOf<TYPES, Int>(TYPES.MAGICAL to 5),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.FIRE to 5, ELEMENTS.ICE to 5)),
                    defModif = ComplexStat(-5),
                    yPos = 66),
            "PALADIN" to Job(
                    "PALADIN",
                    35,
                    18,
                    atkModif = ComplexStat(-5),
                    defModif = ComplexStat(5),
                    yPos = 126),

            "THIEF" to Job(
                    "THIEF",
                    20,
                    33,
                    atkModif = ComplexStat(5),
                    defModif = ComplexStat(-5),
                    yPos = 96),

            "WHITE MAGE" to Job(
                    "WHITE MAGE",
                    22,
                    7,
                    atkModif = ComplexStat(-10,
                            mutableMapOf<TYPES, Int>(TYPES.MAGICAL to 5),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.FIRE to 5, ELEMENTS.ICE to 5)),
                    defModif = ComplexStat(5),
                    yPos = 306))

    fun getJob(job: String): Job = jobs[job] ?: jobs["HUMAN"]!!

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

    fun randomJob(): Job {
        val keys = jobs.keys.toMutableList().shuffled()
        return getJob(keys.first())
    }

}