package com.llgames.ia.battle.logic

class Job(val name: String, val hp: Int, val spd: Int, val atkModif: Array<Pair<Int, Int>>, val defModif: Array<Pair<Int, Int>>, val yPos: Int = 6) {
    var stats = Stats()

    init {
        stats.apply {
            this.hp = this@Job.hp
            this.spd = this@Job.spd
            atkModif.forEach { (stat, value) -> atk[stat] = value }
            defModif.forEach { (stat, value) -> def[stat] = value }
        }
    }

}

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
}
