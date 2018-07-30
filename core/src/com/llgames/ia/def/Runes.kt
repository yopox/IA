package com.llgames.ia.def

import com.llgames.ia.logic.RT
import com.llgames.ia.logic.Rune
import com.llgames.ia.logic.RuneTarget

/**
 * Définition des runes utilisables.
 * Une rune peut être sous forme de [String] (forme écrite) ou sous forme de [Rune] (implémentation).
 *
 * Cet objet permet d'associer l'un à l'autre.
 * Ainsi en utilisant [fromString] on peut récupérer les objets correspondants à une règle, par
 * ex. fromString("ID MXHP 25 SELF ATK ELHP").
 */
object Runes {

    /**
     * Associe une rune sous forme de texte à son objetde type [Rune].
     */
    private fun getRune(rune: String): Rune = when (rune) {

        // Portes logiques
        "ID" -> Rune("ID", RT.GATE, arrayOf(RT.CONDITION, RT.ACTION))
        "NOT" -> Rune("NOT", RT.GATE, arrayOf(RT.CONDITION, RT.ACTION))
        "AND" -> Rune("AND", RT.GATE, arrayOf(RT.CONDITION, RT.ACTION, RT.ACTION))
        "OR" -> Rune("OR", RT.GATE, arrayOf(RT.CONDITION, RT.CONDITION, RT.ACTION))
        "XOR" -> Rune("XOR", RT.GATE, arrayOf(RT.CONDITION, RT.CONDITION, RT.ACTION))
        "NAND" -> Rune("NAND", RT.GATE, arrayOf(RT.CONDITION, RT.CONDITION, RT.ACTION))
        "NOR" -> Rune("NOR", RT.GATE, arrayOf(RT.CONDITION, RT.CONDITION, RT.ACTION))
        "NXOR" -> Rune("NXOR", RT.GATE, arrayOf(RT.CONDITION, RT.CONDITION, RT.ACTION))

        // Conditions
        "E1T" -> Rune("E1T", RT.CONDITION, arrayOf())
        "EXT" -> Rune("EXT", RT.CONDITION, arrayOf(RT.VALUE))
        "MXHP" -> Rune("MXHP", RT.CONDITION, arrayOf(RT.VALUE, RT.TARGET))
        "LXHP" -> Rune("LXHP", RT.CONDITION, arrayOf(RT.VALUE, RT.TARGET))

        // Cibles
        "SELF" -> RuneTarget("SELF")
        "aMHP" -> RuneTarget("aMHP", { it.stats.hp })
        "aLHP" -> RuneTarget("aLHP", { it.stats.hp })
        "AMHP" -> RuneTarget("AMHP", { it.stats.hp })
        "ALHP" -> RuneTarget("ALHP", { it.stats.hp })
        "EMHP" -> RuneTarget("EMHP", { it.stats.hp })
        "ELHP" -> RuneTarget("ELHP", { it.stats.hp })

        // Valeurs
        "1" -> Rune("1", RT.VALUE, arrayOf())
        "2" -> Rune("2", RT.VALUE, arrayOf())
        "3" -> Rune("3", RT.VALUE, arrayOf())
        "4" -> Rune("4", RT.VALUE, arrayOf())
        "5" -> Rune("5", RT.VALUE, arrayOf())
        "10" -> Rune("10", RT.VALUE, arrayOf())
        "15" -> Rune("15", RT.VALUE, arrayOf())
        "20" -> Rune("20", RT.VALUE, arrayOf())
        "25" -> Rune("25", RT.VALUE, arrayOf())
        "30" -> Rune("30", RT.VALUE, arrayOf())
        "35" -> Rune("35", RT.VALUE, arrayOf())
        "40" -> Rune("40", RT.VALUE, arrayOf())
        "45" -> Rune("45", RT.VALUE, arrayOf())
        "50" -> Rune("50", RT.VALUE, arrayOf())
        "55" -> Rune("55", RT.VALUE, arrayOf())
        "60" -> Rune("60", RT.VALUE, arrayOf())
        "65" -> Rune("65", RT.VALUE, arrayOf())
        "70" -> Rune("70", RT.VALUE, arrayOf())
        "75" -> Rune("75", RT.VALUE, arrayOf())
        "80" -> Rune("80", RT.VALUE, arrayOf())
        "85" -> Rune("85", RT.VALUE, arrayOf())
        "90" -> Rune("90", RT.VALUE, arrayOf())

        // Actions
        "DEF" -> Rune("DEF", RT.ACTION, arrayOf())
        "ATK" -> Rune("ATK", RT.ACTION, arrayOf())
        "SPL" -> Rune("SPL", RT.ACTION, arrayOf())
        "WAIT" -> Rune("WAIT", RT.ACTION, arrayOf())

        else -> Rune("", RT.ERROR, arrayOf())
    }

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
        tab.map { rule.add(getRune(it)) }
        return rule.toTypedArray()
    }

    /**
     * Vérifie si une règle d'IA est valide.
     */
    fun isValid(rule: Array<Rune>): Boolean {
        val pile = mutableListOf<RT>()
        for (rune in rule) {
            if (pile.size > 0) {
                val type = pile.removeAt(pile.lastIndex)
                if (type == rune.type) {
                    pile.addAll(rune.next)
                } else {
                    return false
                }
            }
        }
        return true
    }

}