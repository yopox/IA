package com.llgames.ia.logic

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

