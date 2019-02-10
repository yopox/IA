package com.project.ia.logic

import com.project.ia.def.JOB

/**
 * Permet de définir des classes des personnages.
 *
 * Propriétés d'une classe :
 * [name] : Nom de la classe
 * [hp] : Base hp de la classe
 * [spd] : Base spd de la classe
 * [atk] : Base atk
 * [atk] : Base def
 * [lt] : Base lt
 * [dk] : Base dk
 */
class Job(val value: JOB, val name: String,
          val hp: Int, val atk: Int, val def: Int,
          val lt: Int, val dk: Int, val spd: Int) {
    var stats = Stats()

    init {
        stats.apply {
            this.hp = this@Job.hp
            this.spd = this@Job.spd
            this.atk = this@Job.atk
            this.def = this@Job.def
            this.lt = this@Job.lt
            this.dk = this@Job.dk
        }
    }

}

