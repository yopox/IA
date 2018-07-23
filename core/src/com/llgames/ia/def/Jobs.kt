package com.llgames.ia.def

import com.llgames.ia.logic.Job
import com.llgames.ia.logic.Stats

object JOBS {

    val HUMAN = Job(
            "HUMAN",
            20,
            15,
            atkModif = arrayOf(Stats.GENERAL to 5, Stats.MAGICAL to -5, Stats.NEUTRAL to 5),
            defModif = arrayOf(Stats.NEUTRAL to 5))

    val DARKMAGE = Job(
            "DARK MAGE",
            18,
            12,
            atkModif = arrayOf(Stats.GENERAL to 10, Stats.MAGICAL to 5, Stats.FIRE to 5, Stats.ICE to 5),
            defModif = arrayOf(Stats.GENERAL to -5),
            yPos = 336)

    val WHITEMAGE = Job(
            "WHITE MAGE",
            22,
            7,
            atkModif = arrayOf(Stats.GENERAL to -10),
            defModif = arrayOf(Stats.MAGICAL to 5, Stats.FIRE to 5, Stats.ICE to 5),
            yPos = 306)

    val PALADIN = Job(
            "PALADIN",
            35,
            18,
            atkModif = arrayOf(Stats.GENERAL to -5),
            defModif = arrayOf(Stats.GENERAL to 5),
            yPos = 126)

    val MONK = Job(
            "MONK",
            45,
            20,
            atkModif = arrayOf(Stats.GENERAL to 5),
            defModif = arrayOf(Stats.GENERAL to -15),
            yPos = 66)

    val THIEF = Job(
            "THIEF",
            20,
            33,
            atkModif = arrayOf(Stats.GENERAL to 5),
            defModif = arrayOf(Stats.GENERAL to -5),
            yPos = 96)

    val SAMURAI = Job(
            "SAMURAI",
            25,
            30,
            atkModif = arrayOf(Stats.GENERAL to 15),
            defModif = arrayOf(Stats.GENERAL to 5),
            yPos = 186)

    val BERSERKER = Job(
            "BERSERKER",
            35,
            5,
            atkModif = arrayOf(Stats.GENERAL to 20),
            defModif = arrayOf(),
            yPos = 216)

    val TIMEMAGE = Job(
            "TIME MAGE",
            30,
            20,
            atkModif = arrayOf(Stats.GENERAL to 1),
            defModif = arrayOf(Stats.GENERAL to 1),
            yPos = 366)
}