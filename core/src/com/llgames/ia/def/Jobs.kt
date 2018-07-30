package com.llgames.ia.def

import com.llgames.ia.logic.*

/**
 * Définition des différentes classes.
 * Voir [Job] pour les propriétés d'une classe.
 */
object JOBS {

    val DARKMAGE = Job(
            "DARK MAGE",
            18,
            12,
            atkModif = ComplexStat(10,
                    mutableMapOf<TYPES, Int>(TYPES.MAGICAL to 5),
                    mutableMapOf<ELEMENTS, Int>(ELEMENTS.FIRE to 5, ELEMENTS.ICE to 5)),
            defModif = ComplexStat(-5),
            yPos = 336)

    val HUMAN = Job(
            "HUMAN",
            20,
            15,
            atkModif = ComplexStat(10,
                    mutableMapOf<TYPES, Int>(TYPES.MAGICAL to 5),
                    mutableMapOf<ELEMENTS, Int>(ELEMENTS.FIRE to 5, ELEMENTS.ICE to 5)),
            defModif = ComplexStat(-5))

    val MONK = Job(
            "MONK",
            45,
            20,
            atkModif = ComplexStat(10,
                    mutableMapOf<TYPES, Int>(TYPES.MAGICAL to 5),
                    mutableMapOf<ELEMENTS, Int>(ELEMENTS.FIRE to 5, ELEMENTS.ICE to 5)),
            defModif = ComplexStat(-5),
            yPos = 66)

    val PALADIN = Job(
            "PALADIN",
            35,
            18,
            atkModif = ComplexStat(-5),
            defModif = ComplexStat(5),
            yPos = 126)

    val THIEF = Job(
            "THIEF",
            20,
            33,
            atkModif = ComplexStat(5),
            defModif = ComplexStat(-5),
            yPos = 96)

    val WHITEMAGE = Job(
            "WHITE MAGE",
            22,
            7,
            atkModif = ComplexStat(-10,
                    mutableMapOf<TYPES, Int>(TYPES.MAGICAL to 5),
                    mutableMapOf<ELEMENTS, Int>(ELEMENTS.FIRE to 5, ELEMENTS.ICE to 5)),
            defModif = ComplexStat(5),
            yPos = 306)

    /**
     * TODO: Faire mieux que ces trois fonctions
     */
    fun getJob(job: String) : Job = when(job) {
        MONK.name -> MONK
        PALADIN.name -> PALADIN
        THIEF.name -> THIEF
        WHITEMAGE.name -> WHITEMAGE
        DARKMAGE.name -> DARKMAGE
        else -> HUMAN
    }

    /**
     * Utile depuis l'interface pour changer de job sur un personnage.
     */
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