package com.project.ia.logic

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

    /**
     * Permet de recopier un objet [Stats] existant.
     */
    fun setTo(maxStats: Stats, setHp: Boolean) {
        atk.general = maxStats.atk.general
        atk.elements = maxStats.atk.elements.toMutableMap()
        atk.types = maxStats.atk.types.toMutableMap()
        def.general = maxStats.def.general
        def.elements = maxStats.def.elements.toMutableMap()
        def.types = maxStats.def.types.toMutableMap()
        if (setHp) hp = maxStats.hp
        spd = maxStats.spd
    }

    override fun toString(): String {
        return "hp: $hp ; atk: ${atk.general} ; def: ${def.general} ; spd: $spd"
    }

    fun applyBuff(buff: Pair<STAT_ENUM, Int>) = when (buff.first) {
        STAT_ENUM.HP -> hp += buff.second
        STAT_ENUM.SPD -> spd += buff.second
        STAT_ENUM.ATKG -> atk.general += buff.second
        STAT_ENUM.ATKP -> atk.types[TYPES.PHYSICAL] = atk.types[TYPES.PHYSICAL]!! + buff.second
        STAT_ENUM.ATKM -> atk.types[TYPES.MAGICAL] = atk.types[TYPES.MAGICAL]!! + buff.second
        STAT_ENUM.ATKL -> atk.elements[ELEMENTS.LIGHT] = atk.elements[ELEMENTS.LIGHT]!! + buff.second
        STAT_ENUM.ATKD -> atk.elements[ELEMENTS.DARK] = atk.elements[ELEMENTS.DARK]!! + buff.second
        STAT_ENUM.DEFG -> def.general += buff.second
        STAT_ENUM.DEFP -> def.types[TYPES.PHYSICAL] = def.types[TYPES.PHYSICAL]!! + buff.second
        STAT_ENUM.DEFM -> def.types[TYPES.MAGICAL] = def.types[TYPES.MAGICAL]!! + buff.second
        STAT_ENUM.DEFL -> def.elements[ELEMENTS.LIGHT] = def.elements[ELEMENTS.LIGHT]!! + buff.second
        STAT_ENUM.DEFD -> def.elements[ELEMENTS.DARK] = def.elements[ELEMENTS.DARK]!! + buff.second
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

enum class STAT_ENUM {
    HP, SPD, ATKG, DEFG, ATKP, DEFP, ATKM, DEFM, ATKL, DEFL, ATKD, DEFD
}