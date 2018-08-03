package com.llgames.ia.logic

/**
 * Objet correspondant aux stats d'un personnage.
 * [ComplexStat] est utilisé pour une stat qui se subdivise en une partie générale,
 * une partie par type et une partie par élément.
 */
class Stats {

    var hp = 100
    var spd = 0
    var atk = ComplexStat()
    var def = ComplexStat()
    var atkB = ComplexStat()
    var defB = ComplexStat()

    fun setTo(maxStats: Stats, setHp: Boolean) {
        atk = maxStats.atk.copy()
        def = maxStats.def.copy()
        if (setHp) hp = maxStats.hp
        spd = maxStats.spd
    }

    override fun toString(): String {
        return "hp: $hp ; atk: ${atk.general} ; def: ${def.general} ; spd: $spd"
    }

}

data class ComplexStat(var general: Int = 0,
                       var types: MutableMap<TYPES, Int> = mutableMapOf(),
                       var elements: MutableMap<ELEMENTS, Int> = mutableMapOf())

enum class TYPES {
    MAGICAL, PHYSICAL
}

enum class ELEMENTS {
    DARK, LIGHT
}