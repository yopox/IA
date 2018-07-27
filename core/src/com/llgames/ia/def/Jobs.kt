package com.llgames.ia.def

import com.llgames.ia.logic.Job
import com.llgames.ia.logic.Stats

/**
 * Définition des différentes classes.
 * Voir [Job] pour les propriétés d'une classe.
 */
object JOBS {

    val DARKMAGE = Job(
            "DARK MAGE",
            18,
            12,
            atkModif = arrayOf(Stats.GENERAL to 10, Stats.MAGICAL to 5, Stats.FIRE to 5, Stats.ICE to 5),
            defModif = arrayOf(Stats.GENERAL to -5),
            yPos = 336)

    val HUMAN = Job(
            "HUMAN",
            20,
            15,
            atkModif = arrayOf(Stats.GENERAL to 5, Stats.MAGICAL to -5, Stats.NEUTRAL to 5),
            defModif = arrayOf(Stats.NEUTRAL to 5))

    val MONK = Job(
            "MONK",
            45,
            20,
            atkModif = arrayOf(Stats.GENERAL to 5),
            defModif = arrayOf(Stats.GENERAL to -15),
            yPos = 66)

    val PALADIN = Job(
            "PALADIN",
            35,
            18,
            atkModif = arrayOf(Stats.GENERAL to -5),
            defModif = arrayOf(Stats.GENERAL to 5),
            yPos = 126)

    val THIEF = Job(
            "THIEF",
            20,
            33,
            atkModif = arrayOf(Stats.GENERAL to 5),
            defModif = arrayOf(Stats.GENERAL to -5),
            yPos = 96)

    val WHITEMAGE = Job(
            "WHITE MAGE",
            22,
            7,
            atkModif = arrayOf(Stats.GENERAL to -10),
            defModif = arrayOf(Stats.MAGICAL to 5, Stats.FIRE to 5, Stats.ICE to 5),
            yPos = 306)

    fun nextJob(job: Job) : Job = when(job) {
        HUMAN -> MONK
        MONK -> PALADIN
        PALADIN -> THIEF
        THIEF -> WHITEMAGE
        WHITEMAGE -> DARKMAGE
        else -> HUMAN
    }

    fun previousJob(job: Job) : Job = when(job) {
        PALADIN -> MONK
        THIEF -> PALADIN
        WHITEMAGE -> THIEF
        DARKMAGE -> WHITEMAGE
        HUMAN -> DARKMAGE
        else -> HUMAN
    }

}