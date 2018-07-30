package com.llgames.ia.logic

class Stats {

    var hp = 100
    var spd = 0
    var atk = ComplexStat()
    var def = ComplexStat()
    var atkB = ComplexStat()
    var defB = ComplexStat()

    fun setTo(maxStats: Stats) {
        atk = maxStats.atk.copy()
        def = maxStats.def.copy()
        hp = maxStats.hp
        spd = maxStats.spd
    }

}

data class ComplexStat(var general: Int = 0,
                       var types: MutableMap<TYPES, Int> = mutableMapOf(),
                       var elements: MutableMap<ELEMENTS, Int> = mutableMapOf())

enum class TYPES {
    MAGICAL, PHYSICAL, BLADE
}

enum class ELEMENTS {
    NEUTRAL, FIRE, ICE
}