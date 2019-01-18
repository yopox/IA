package com.project.ia.def

import com.project.ia.logic.*
import java.util.*

data class Weapon(val name: String,
                  val jets: Array<Jet>,
                  val STAT: Array<Pair<STAT, Int>>)

data class Spell(val name: String,
                 val jets: Array<Jet>,
                 val boosts: Array<Boost>? = null)

data class Relic(val name: String,
                 val STAT: Array<Pair<STAT, Int>>)

object Equip {

    val WEAPONS: Map<String, Weapon> = mapOf(
            "NONE" to Weapon(
                    name = "Fists",
                    jets = arrayOf(
                            Jet(ELEMENTS.PHYSICAL, 5)),
                    STAT = arrayOf()
            )
    )

    val SPELLS: Map<String, Spell> = mapOf(
            "NONE" to Spell(
                    name = "None",
                    jets = arrayOf()
            ),
            "PRAY" to Spell(
                    name = "Pray",
                    jets = arrayOf(),
                    boosts = arrayOf(Boost(STAT.HP, 6, 1, false))
            )
    )

    val RELICS: Map<String, Relic> = mapOf(
            "NONE" to Relic(
                    name = "None",
                    STAT = arrayOf()
            )
    )

    fun getWeapon(weapon: String): Weapon = WEAPONS[weapon] ?: WEAPONS["NONE"]!!
    fun getSpell(spell: String): Spell = SPELLS[spell] ?: SPELLS["NONE"]!!
    fun getRelic(relic: String): Relic = RELICS[relic] ?: RELICS["NONE"]!!

    fun randomWeapon(): String = WEAPONS.keys.elementAt(Random().nextInt(Equip.WEAPONS.size))
    fun randomSpell(): String = SPELLS.keys.elementAt(Random().nextInt(Equip.SPELLS.size))
    fun randomRelic(): String = RELICS.keys.elementAt(Random().nextInt(Equip.RELICS.size))

    /**
     * Renvoie la description d'une arme avec nom, dommages, et bonus de STAT.
     */
    fun weaponDesc(weapon: String): CharSequence? {
        val wp = WEAPONS[weapon] ?: WEAPONS["NONE"]!!
        var description = wp.name
        description += "\nDamage :${jetsToString(wp.jets)}"
        description += "\n${statsToString(wp.STAT).drop(1)}"
        return description
    }

    /**
     * Renvoie la description d'un spell avec nom, dommages, et boosts.
     */
    fun spellDesc(spell: String): CharSequence? {
        val sp = SPELLS[spell] ?: SPELLS["NONE"]!!
        var description = sp.name
        description += "\nDamage :${jetsToString(sp.jets)}"
        description += "\nBuffs :${boostsToString(sp.boosts).drop(1)}"
        return description
    }

    /**
     * Renvoie la description d'une relique avec nom et bonus de STAT.
     */
    fun relicDesc(relic: String): CharSequence? {
        val rc = RELICS[relic] ?: RELICS["NONE"]!!
        var description = rc.name
        description += "\n${statsToString(rc.STAT).drop(1)}"
        return description
    }

    private fun jetsToString(jets: Array<Jet>): String {
        val abbrE = mapOf<ELEMENTS, String>(ELEMENTS.PHYSICAL to "P", ELEMENTS.DARK to "D")
        var description = ""
        for (jet in jets)
            description += " " + jet.damage.toString() + " " + abbrE[jet.elem]
        return if (description == "") " None" else description
    }

    private fun statsToString(STAT: Array<Pair<STAT, Int>>): String {
        var description = ""
        for ((stat, amount) in STAT)
            description += " $amount $stat"
        return if (description == "") " None" else description
    }

    private fun boostsToString(boosts: Array<Boost>?): String {
        var description = ""
        boosts?.let {
            for ((a, b, c, d) in boosts)
                description += ", $b $a${c}T${if (d) "self" else ""}"
        }
        return if (description == "") "  None" else description
    }

}