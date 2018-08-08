package com.project.ia.genetic

/**
 * Gère une équipe de [GFighter].
 *
 * TODO: Génération de [GFighter] aléatoires
 * TODO: Calcul du fitness
 * TODO: Mutations
 */
class GTeam(var teamNb: Int = 0) {
    var fighters: Array<GFighter> = arrayOf()
    var fitness = 0

    init {
        val fighter1 = GFighter("Hadri", teamNb, 0)
        val fighter2 = GFighter("yopox", teamNb, 1)
        val fighter3 = GFighter("Ixous", teamNb, 2)

        fighters = arrayOf(fighter1, fighter2, fighter3)
        fighters.map { it.changeIA("ID EXT 1 ATK ELHP") }
    }
}