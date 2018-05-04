package com.llgames.ia.battle.logic

class Stats {

    var atk = MutableList(N, { 0 })
    var def = MutableList(N, { 0 })
    var atkB = MutableList(N, { 0 })
    var defB = MutableList(N, { 0 })
    var hp = 100
    var spd = 0

    fun setTo(maxStats: Stats, setHp: Boolean = false) {
        atk.withIndex().map { (i, j) -> atk[i] = maxStats.atk[i] }
        def.withIndex().map { (i, j) -> def[i] = maxStats.def[i] }
        if (setHp) hp = maxStats.hp
        spd = maxStats.spd
    }

    //TODO: Get rid of the companion object
    //TODO: Create corresponding enum
    companion object {

        const val ATTACK = "ATK"
        const val DEFENSE = "DEF"

        const val GENERAL = 0

        const val MAGICAL = 1
        const val BLADE = 2

        const val NEUTRAL = 3
        const val FIRE = 4
        const val ICE = 5
        const val N = 6

    }

}

object HUMAN {
    var stats = Stats()

    init {
        stats.apply {
            hp = 20
            spd = 50
            atk[Stats.GENERAL] = 5
            atk[Stats.MAGICAL] = -5
            atk[Stats.NEUTRAL] = 5
            def[Stats.NEUTRAL] = 5
        }
    }
}

object DARK_MAGE {
    var stats = Stats()

    init {
        stats.apply {
            hp = 18
            spd = 35
            atk[Stats.GENERAL] = 10
            atk[Stats.MAGICAL] = 5
            atk[Stats.FIRE] = 5
            atk[Stats.ICE] = 5
            def[Stats.GENERAL] = -5
        }
    }
}

object WHITE_MAGE {
    var stats = Stats()

    init {
        stats.apply {
            hp = 22
            spd = 15
            atk[Stats.GENERAL] = -10
            atk[Stats.MAGICAL] = 5
            def[Stats.GENERAL] = 5
            def[Stats.FIRE] = 5
            def[Stats.ICE] = 5
        }
    }
}