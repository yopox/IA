package com.llgames.ia.def

import com.llgames.ia.logic.*

/**
 * Définition des différentes classes.
 * Voir [Job] pour les propriétés d'une classe.
 *
 * ★ 4 ★★ 10 ★★★ 20 ★★★★ 34
 */
object JOBS {

    private val jobs = mapOf(

            "WARRIOR" to Job(
                    "WARRIOR",
                    40,
                    10,
                    atkModif = ComplexStat(10,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 10, TYPES.MAGICAL to 10),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 10, ELEMENTS.LIGHT to 10)),
                    defModif = ComplexStat(10,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 10, TYPES.MAGICAL to 10),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 10, ELEMENTS.LIGHT to 10))),

            "DARK MAGE" to Job(
                    "DARK MAGE",
                    20,
                    20,
                    atkModif = ComplexStat(20,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 4, TYPES.MAGICAL to 20),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 20, ELEMENTS.LIGHT to 4)),
                    defModif = ComplexStat(10,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 4, TYPES.MAGICAL to 10),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 20, ELEMENTS.LIGHT to 4)),
                    yPos = 336),

            "WHITE MAGE" to Job(
                    "WHITE MAGE",
                    20,
                    20,
                    atkModif = ComplexStat(20,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 4, TYPES.MAGICAL to 20),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 4, ELEMENTS.LIGHT to 20)),
                    defModif = ComplexStat(10,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 4, TYPES.MAGICAL to 10),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 4, ELEMENTS.LIGHT to 20)),
                    yPos = 306),

            "MONK" to Job(
                    "MONK",
                    40,
                    34,
                    atkModif = ComplexStat(34,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 20, TYPES.MAGICAL to 4),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 4, ELEMENTS.LIGHT to 10)),
                    defModif = ComplexStat(4,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 10, TYPES.MAGICAL to 4),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 4, ELEMENTS.LIGHT to 4)),
                    yPos = 66),

            "PALADIN" to Job(
                    "PALADIN",
                    20,
                    10,
                    atkModif = ComplexStat(10,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 10, TYPES.MAGICAL to 10),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 4, ELEMENTS.LIGHT to 10)),
                    defModif = ComplexStat(20,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 20, TYPES.MAGICAL to 10),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 4, ELEMENTS.LIGHT to 20)),
                    yPos = 66),

            "THIEF" to Job(
                    "THIEF",
                    20,
                    55,
                    atkModif = ComplexStat(20,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 10, TYPES.MAGICAL to 4),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 20, ELEMENTS.LIGHT to 10)),
                    defModif = ComplexStat(4,
                            mutableMapOf<TYPES, Int>(TYPES.PHYSICAL to 10, TYPES.MAGICAL to 4),
                            mutableMapOf<ELEMENTS, Int>(ELEMENTS.DARK to 20, ELEMENTS.LIGHT to 4)),
                    yPos = 96))

    fun getJob(job: String): Job = jobs[job] ?: jobs["WARRIOR"]!!

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