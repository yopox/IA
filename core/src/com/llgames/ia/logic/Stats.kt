package com.llgames.ia.logic

class Stats {

    var atk = MutableList(N, { 0 })
    var def = MutableList(N, { 0 })
    var atkB = MutableList(N, { 0 })
    var defB = MutableList(N, { 0 })
    var hp = 100
    var spd = 0

    fun setTo(maxStats: Stats) {
        atk.withIndex().map { (i, _) -> atk[i] = maxStats.atk[i] }
        def.withIndex().map { (i, _) -> def[i] = maxStats.def[i] }
        hp = maxStats.hp
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