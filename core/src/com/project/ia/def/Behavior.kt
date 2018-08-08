package com.project.ia.def

/**
 * Contient des comportements prédéfinis.
 */

object Behavior {

    private val comportement = mapOf(
            "NO_BRAIN" to "ID EXT 1 ATK ELHP",
            "WISER" to "NOT EXT 3 ATK ELHP - ID EXT 1 DEF",
            "TARGET_STRONG" to "ID EXT 1 ATK EMHP",
            "SHELL" to "ID LXHP 50 SELF DEF - ID EXT 1 ATK ELHP",
            "DEF" to "ID EXT 1 DEF"
    )

    fun getRandom(): String {
        val keys = comportement.keys.toMutableList().shuffled()
        return comportement[keys.first()]!!
    }

}