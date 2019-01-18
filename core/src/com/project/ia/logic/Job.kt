package com.project.ia.logic

/**
 * Permet de définir des classes des personnages.
 *
 * Propriétés d'une classe :
 * [name] : Nom de la classe
 * [hp] : Base hp de la classe
 * [spd] : Base spd de la classe
 * [atk] : Base atk
 * [lt] : Base lt
 * [dk] : Base dk
 */
class Job(val name: String, val hp: Int, val spd: Int, val atk: Int, val lt: Int, val dk: Int) {
    var stats = Stats()

    init {
        stats.apply {
            this.hp = this@Job.hp
            this.spd = this@Job.spd
            this.atk = this@Job.atk
            this.lt = this@Job.lt
            this.dk = this@Job.dk
        }
    }

}

