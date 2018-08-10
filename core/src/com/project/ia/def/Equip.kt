package com.project.ia.def

import com.project.ia.logic.*
import java.util.*

data class Weapon(val name: String,
                  val jets: Array<Jet>,
                  val stats: Array<Pair<STAT_ENUM, Int>>)

data class Spell(val name: String,
                 val jets: Array<Jet>,
                 val boosts: Array<Boost>? = null)

data class Relic(val name: String,
                 val stats: Array<Pair<STAT_ENUM, Int>>)

object Equip {

    val WEAPONS: Map<String, Weapon> = mapOf(
            "NONE" to Weapon(
                    name = "Fists",
                    jets = arrayOf(
                            Jet(TYPES.PHYSICAL, ELEMENTS.DARK, 4)),
                    stats = arrayOf()
            ),
            "BASIC" to Weapon(
                    name = "Wooden Sword",
                    jets = arrayOf(
                            Jet(TYPES.PHYSICAL, ELEMENTS.LIGHT, 4),
                            Jet(TYPES.PHYSICAL, ELEMENTS.DARK, 4)),
                    stats = arrayOf(STAT_ENUM.ATKG to 5)
            ),
            "LIGHT" to Weapon(
                    name = "Sacred Sword",
                    jets = arrayOf(
                            Jet(TYPES.PHYSICAL, ELEMENTS.LIGHT, 8)),
                    stats = arrayOf(STAT_ENUM.ATKG to 3, STAT_ENUM.ATKL to 5)
            ),
            "DARK" to Weapon(
                    name = "Cursed Sword",
                    jets = arrayOf(
                            Jet(TYPES.PHYSICAL, ELEMENTS.DARK, 8)),
                    stats = arrayOf(STAT_ENUM.ATKG to 3, STAT_ENUM.ATKD to 5)
            ),
            "SHIELD" to Weapon(
                    name = "Spiky Shield",
                    jets = arrayOf(
                            Jet(TYPES.PHYSICAL, ELEMENTS.DARK, 4)),
                    stats = arrayOf(STAT_ENUM.DEFG to 5, STAT_ENUM.DEFP to 5)
            ),
            "STAFF" to Weapon(
                    name = "Fouras' Staff",
                    jets = arrayOf(
                            Jet(TYPES.PHYSICAL, ELEMENTS.LIGHT, 4)),
                    stats = arrayOf(STAT_ENUM.ATKM to 8, STAT_ENUM.DEFM to 4)
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
                    boosts = arrayOf(Boost(STAT_ENUM.HP, 6, 1, false))
            ),
            "SHELL" to Spell(
                    name = "Shell",
                    jets = arrayOf(),
                    boosts = arrayOf(Boost(STAT_ENUM.DEFG, 25, 2, false))
            ),
            "HALO" to Spell(
                    name = "Halo of Light",
                    jets = arrayOf(),
                    boosts = arrayOf(Boost(STAT_ENUM.HP, 4, 1, false),
                            Boost(STAT_ENUM.HP, 3, 1, true))
            ),
            "BOOST" to Spell(
                    name = "Boost",
                    jets = arrayOf(),
                    boosts = arrayOf(Boost(STAT_ENUM.ATKG, 35, 1, false))
            ),
            "FIREB" to Spell(
                    name = "Fireball",
                    jets = arrayOf(Jet(TYPES.MAGICAL, ELEMENTS.DARK, 8)),
                    boosts = arrayOf(Boost(STAT_ENUM.DEFP, -5, 3, false))
            ),
            "ICES" to Spell(
                    name = "Ice Shards",
                    jets = arrayOf(Jet(TYPES.MAGICAL, ELEMENTS.DARK, 7)),
                    boosts = arrayOf(Boost(STAT_ENUM.DEFM, -5, 3, false))
            )
    )

    val RELICS: Map<String, Relic> = mapOf(
            "NONE" to Relic(
                    name = "None",
                    stats = arrayOf()
            ),
            "NECRO" to Relic(
                    name = "Necronomicon",
                    stats = arrayOf(STAT_ENUM.ATKD to 20)
            ),
            "BIBLE" to Relic(
                    name = "Bible",
                    stats = arrayOf(STAT_ENUM.ATKL to 20)
            ),
            "MITSUCID" to Relic(
                    name = "Mitsu Cider",
                    stats = arrayOf(STAT_ENUM.HP to 10)
            ),
            "BACKPACK" to Relic(
                    name = "Traveler's Backpack",
                    stats = arrayOf(STAT_ENUM.ATKP to 10, STAT_ENUM.HP to 6, STAT_ENUM.SPD to -8)
            )
    )

    fun getWeapon(weapon: String): Weapon = WEAPONS[weapon] ?: WEAPONS["NONE"]!!
    fun getSpell(spell: String): Spell = SPELLS[spell] ?: SPELLS["NONE"]!!
    fun getRelic(relic: String): Relic = RELICS[relic] ?: RELICS["NONE"]!!

    fun randomWeapon(): String = WEAPONS.keys.elementAt(Random().nextInt(Equip.WEAPONS.size))
    fun randomSpell(): String = SPELLS.keys.elementAt(Random().nextInt(Equip.SPELLS.size))
    fun randomRelic(): String = RELICS.keys.elementAt(Random().nextInt(Equip.RELICS.size))

    /**
     * Renvoie la description d'une arme avec nom, dommages, et bonus de stats.
     */
    fun weaponDesc(weapon: String): CharSequence? {
        val wp = WEAPONS[weapon] ?: WEAPONS["NONE"]!!
        var description = wp.name
        description += "\nDamage :${jetsToString(wp.jets)}"
        description += "\n${statsToString(wp.stats).drop(1)}"
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
     * Renvoie la description d'une relique avec nom et bonus de stats.
     */
    fun relicDesc(relic: String): CharSequence? {
        val rc = RELICS[relic] ?: RELICS["NONE"]!!
        var description = rc.name
        description += "\n${statsToString(rc.stats).drop(1)}"
        return description
    }

    fun jetsToString(jets: Array<Jet>): String {
        val abbrE = mapOf<ELEMENTS, String>(ELEMENTS.DARK to "Dark", ELEMENTS.LIGHT to "Light")
        var description = ""
        for (jet in jets)
            description += " " + jet.damage.toString() + " " + abbrE[jet.elem]
        return if (description == "") " None" else description
    }

    fun statsToString(stats: Array<Pair<STAT_ENUM, Int>>): String {
        var description = ""
        for ((stat, amount) in stats)
            description += " $amount $stat"
        return if (description == "") " None" else description
    }

    fun boostsToString(boosts: Array<Boost>?): String {
        var description = ""
        boosts?.let {
            for ((a, b, c, d) in boosts)
                description += ", $b $a${c}T${if (d) "self" else ""}"
        }
        return if (description == "") "  None" else description
    }

}