package com.llgames.ia.logic

/**
 * Permet de définir des classes des personnages.
 *
 * Propriétés d'une classe :
 * [name] : Nom de la classe
 * [hp] : base hp de la classe
 * [spd] : base spd de la classe
 * [atkModif] : Pair<Int, Int> reliant une stat d'attaque à sa valeur
 * [defModif] : Pair<Int, Int> reliant une stat de défense à sa valeur
 * [yPos] : Position y des sprites de la classe dans le spritesheet
 */
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

