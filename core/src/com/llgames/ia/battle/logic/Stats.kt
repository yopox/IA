package com.llgames.ia.battle.logic

class Stats {

    var atk = HashMap(TypesElemMap)
    var def = HashMap(TypesElemMap)
    var atkB = HashMap(TypesElemMap)
    var defB = HashMap(TypesElemMap)
    var hp = 100
    var spd = 0

    fun setTo(maxStats: Stats) {
        atk = HashMap(maxStats.atk)
        def = HashMap(maxStats.def)
        hp = maxStats.hp
    }

    companion object {
        val GENERAL = "general"

        val MAGICAL = "magical"
        val BLADE = "blade"
        val TYPES = arrayOf(MAGICAL, BLADE)

        val NEUTRAL = "neutral"
        val FIRE = "fire"
        val ICE = "ice"
        val ELEMENTS = arrayOf(NEUTRAL, FIRE, ICE)
        private val TypesElemMap = HashMap<String, Int>()

        init {
            TypesElemMap[GENERAL] = 0
            TYPES.forEach { TypesElemMap[it] = 0 }
            ELEMENTS.forEach { TypesElemMap[it] = 0 }
        }

    }

}

object HUMAN {
    var stats = Stats()

    init {
        stats.apply {
            hp = 150
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
            hp = 125
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
            hp = 175
            spd = 15
            atk[Stats.GENERAL] = -10
            atk[Stats.MAGICAL] = 5
            def[Stats.GENERAL] = 5
            def[Stats.FIRE] = 5
            def[Stats.ICE] = 5
        }
    }
}