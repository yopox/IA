package com.project.ia.def

import com.project.ia.logic.RT
import com.project.ia.logic.Rune
import com.project.ia.logic.RuneTarget

/**
 * Définition des runes utilisables.
 * Une rune peut être sous forme de [String] (forme écrite) ou sous forme de [Rune] (implémentation).
 *
 * Cet objet permet d'associer l'un à l'autre.
 * Ainsi en utilisant [fromString] on peut récupérer les objets correspondants à une règle, par
 * ex. fromString("ID MXHP 25 SELF ATK ELHP").
 *
 * Pour ajouter une rune en jeu il faut l'ajouter dans [runes] puis gérer son comportement dans [Rune]
 */
object Runes {

    /**
     * Associe une rune sous forme de texte à son objetde type [Rune].
     */
    val runes: Map<String, Rune> = mapOf(

            // Portes logiques
            "ID" to Rune("ID", RT.GATE, arrayOf(RT.CONDITION, RT.ACTION)),
            "NOT" to Rune("NOT", RT.GATE, arrayOf(RT.CONDITION, RT.ACTION)),
            "AND" to Rune("AND", RT.GATE, arrayOf(RT.CONDITION, RT.CONDITION, RT.ACTION)),
            "OR" to Rune("OR", RT.GATE, arrayOf(RT.CONDITION, RT.CONDITION, RT.ACTION)),
            "XOR" to Rune("XOR", RT.GATE, arrayOf(RT.CONDITION, RT.CONDITION, RT.ACTION)),
            "NAND" to Rune("NAND", RT.GATE, arrayOf(RT.CONDITION, RT.CONDITION, RT.ACTION)),
            "NOR" to Rune("NOR", RT.GATE, arrayOf(RT.CONDITION, RT.CONDITION, RT.ACTION)),
            "NXOR" to Rune("NXOR", RT.GATE, arrayOf(RT.CONDITION, RT.CONDITION, RT.ACTION)),

            // Conditions
            "EXT" to Rune("EXT", RT.CONDITION, arrayOf(RT.VALUE)),
            "TX" to Rune("TX", RT.CONDITION, arrayOf(RT.VALUE)),
            "T>X" to Rune("T>X", RT.CONDITION, arrayOf(RT.VALUE)),
            "ONCE" to Rune("ONCE", RT.CONDITION),
            "MXHP" to Rune("MXHP", RT.CONDITION, arrayOf(RT.VALUE, RT.TARGET)),
            "LXHP" to Rune("LXHP", RT.CONDITION, arrayOf(RT.VALUE, RT.TARGET)),

            // Cibles
            "SELF" to RuneTarget("SELF"),
            "aMHP" to RuneTarget("aMHP", { it.stats.hp }),
            "aLHP" to RuneTarget("aLHP", { it.stats.hp }),
            "AMHP" to RuneTarget("AMHP", { it.stats.hp }),
            "ALHP" to RuneTarget("ALHP", { it.stats.hp }),
            "EMHP" to RuneTarget("EMHP", { it.stats.hp }),
            "ELHP" to RuneTarget("ELHP", { it.stats.hp }),

            // Valeurs
            "0" to Rune("0", RT.VALUE),
            "1" to Rune("1", RT.VALUE),
            "2" to Rune("2", RT.VALUE),
            "3" to Rune("3", RT.VALUE),
            "4" to Rune("4", RT.VALUE),
            "5" to Rune("5", RT.VALUE),
            "10" to Rune("10", RT.VALUE),
            "15" to Rune("15", RT.VALUE),
            "20" to Rune("20", RT.VALUE),
            "25" to Rune("25", RT.VALUE),
            "30" to Rune("30", RT.VALUE),
            "35" to Rune("35", RT.VALUE),
            "40" to Rune("40", RT.VALUE),
            "45" to Rune("45", RT.VALUE),
            "50" to Rune("50", RT.VALUE),
            "55" to Rune("55", RT.VALUE),
            "60" to Rune("60", RT.VALUE),
            "65" to Rune("65", RT.VALUE),
            "70" to Rune("70", RT.VALUE),
            "75" to Rune("75", RT.VALUE),
            "80" to Rune("80", RT.VALUE),
            "85" to Rune("85", RT.VALUE),
            "90" to Rune("90", RT.VALUE),
            "95" to Rune("95", RT.VALUE),

            // Actions
            "ATK" to Rune("ATK", RT.ACTION, arrayOf(RT.TARGET)),
            "SPL1" to Rune("SPL1", RT.ACTION, arrayOf(RT.TARGET)),
            "SPL2" to Rune("SPL2", RT.ACTION, arrayOf(RT.TARGET)),
            "DEF" to Rune("DEF", RT.ACTION),
            "WRM" to Rune("WRM", RT.ACTION),
            "PRO" to Rune("PRO", RT.ACTION, arrayOf(RT.TARGET)),
            "WAIT" to Rune("WAIT", RT.ACTION)

    )

    /**
     * Convertit un Array<Rune> en String.
     */
    fun toString(rule: Array<Rune>): String {
        var chaine = ""
        rule.map { chaine += it.id + " " }
        return chaine.dropLast(1)
    }

    /**
     * Convertit un String en Array<Rune>.
     */
    fun fromString(chaine: String): Array<Rune> {
        val tab = chaine.split(" ")
        val rule = mutableListOf<Rune>()
        tab.map { rule.add(runes[it] ?: Rune("", RT.ERROR)) }
        return rule.toTypedArray()
    }

    /**
     * Vérifie si une règle d'IA est valide.
     */
    fun isValid(rule: Array<Rune>): Boolean {
        val pile = mutableListOf(RT.GATE)
        for (i in 0..rule.lastIndex) {
            val rune = rule[i]
            if (pile.size > 0) {
                val type = pile.removeAt(pile.lastIndex)
                if (type == rune.type) {
                    pile.addAll(rune.next.reversed())
                } else {
                    return false
                }
            } else {
                return false
            }
        }
        return true
    }

    /**
     * Renvoie la partie conditionnelle de la règle.
     */
    fun getCondPart(rule: MutableList<Rune>): MutableList<Rune> {
        var lastIndex = 0
        while (rule.lastIndex >= lastIndex && rule[lastIndex].type != RT.ACTION) {
            lastIndex++
        }
        return rule.subList(2, lastIndex)
    }

    /**
     * Renvoie la partie action de la règle.
     */
    fun getActPart(rule: MutableList<Rune>): MutableList<Rune> {
        var lastIndex = 0
        while (rule.lastIndex >= lastIndex && rule[lastIndex].type != RT.ACTION) {
            lastIndex++
        }
        return rule.subList(lastIndex + 1, rule.lastIndex + 1)
    }

    /**
     * Renvoie une rune aléatoire d'un type donné.
     */
    fun getRandom(type: RT): Rune {
        val runesType = Runes.runes.filter { it.component2().type == type }
        val randomKey = runesType.keys.toList().shuffled().first()
        return Runes.runes[randomKey]!!
    }
}