package com.project.ia.logic

enum class ELEMENTS {
    PHYSICAL, DARK
}

enum class STAT {
    HP, SPD, ATK, DEF, LT, DK
}

/**
 * Objet correspondant aux statistiques d'un personnage.
 */
class Stats {

    var hp = 100
    var spd = 0
    var atk = 0
    var def = 0
    var lt = 0
    var dk = 0

    /**
     * Permet de recopier un objet [Stats] existant.
     */
    fun setTo(maxStats: Stats, setHp: Boolean) {
        if (setHp) hp = maxStats.hp
        spd = maxStats.spd
        atk = maxStats.atk
        lt = maxStats.lt
        dk = maxStats.dk
        def = maxStats.def
    }

    override fun toString(): String {
        return "hp: $hp ; spd: $spd ; atk: $atk ; def: $def ; lt: $lt ; dk: $dk"
    }

    fun applyBuff(buff: Pair<STAT, Int>) = when (buff.first) {
        STAT.HP -> hp += buff.second
        STAT.SPD -> spd += buff.second
        STAT.ATK -> atk += buff.second
        STAT.LT -> lt += buff.second
        STAT.DK -> dk += buff.second
        STAT.DEF -> def += buff.second
    }

    fun getAtk(elem: ELEMENTS) = when(elem) {
        ELEMENTS.PHYSICAL -> atk
        ELEMENTS.DARK -> dk
    }

    fun getDef(elem: ELEMENTS) = when(elem) {
        ELEMENTS.PHYSICAL -> def
        ELEMENTS.DARK -> (dk + lt) / 2
    }

}