package com.llgames.ia.battle.logic

class Stats {

    private val TYPES = arrayOf("blade", "magical")
    private val ELEMENTS = arrayOf("neutral", "fire", "water")
    private var TE_MAP = HashMap<String, Int>()

    var atk: MutableMap<String, Int>
    var def: MutableMap<String, Int>
    var atkB: MutableMap<String, Int>
    var defB: MutableMap<String, Int>

    var hp = 100
    var mp = 0

    init {
        TE_MAP["general"] = 0
        TYPES.forEach { TE_MAP[it] = 0 }
        ELEMENTS.forEach { TE_MAP[it] = 0 }

        atk = HashMap<String, Int>(TE_MAP)
        def = HashMap<String, Int>(TE_MAP)
        atkB = HashMap<String, Int>(TE_MAP)
        defB = HashMap<String, Int>(TE_MAP)
    }

    fun setTo(maxStats: Stats) {
        atk = HashMap<String, Int>(maxStats.atk)
        def = HashMap<String, Int>(maxStats.def)
        hp = maxStats.hp
        mp = maxStats.mp
    }

}