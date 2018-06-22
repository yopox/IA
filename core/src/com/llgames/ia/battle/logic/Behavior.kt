package com.llgames.ia.battle.logic

object ACTIONS {
    val atk = IA.Action("ATK",
            IA.Target("ELHP"),
            Weapon(arrayOf(Jet(Stats.BLADE, Stats.NEUTRAL, 8)), name = "Night Sword"))

    val def = IA.Action("DEF",
            IA.Target("SELF"))

    val leechAtk = IA.Action("SPL",
            IA.Target("EMST", { it.stats.atk[Stats.GENERAL] }),
            spell = Spell(jets = arrayOf(Jet(Stats.BLADE, Stats.NEUTRAL, 2)),
                    boosts = arrayOf(Boost(Stats.ATTACK, Stats.GENERAL, -20, 3, false)), name = "Fira"))

    val leechDef = IA.Action("SPL",
            IA.Target("EMDF", { it.stats.def[Stats.GENERAL] }),
            spell = Spell(jets = arrayOf(Jet(Stats.BLADE, Stats.NEUTRAL, 2)),
                    boosts = arrayOf(Boost(Stats.DEFENSE, Stats.GENERAL, -20, 3, false)), name = "Ice Shards"))

    val pro = IA.Action("PRO",
            IA.Target("ALHP"))
}

object CONDITIONS {
    val eachTurn = IA.Condition("E1T")
    val each2Turn = IA.Condition("EXT", value = 2)
    val each3Turn = IA.Condition("EXT", value = 3)
    val each4Turn = IA.Condition("EXT", value = 4)
    val lowLifeSelf = IA.Condition("LXHP", IA.Target("SELF"), value = 25)
    val lowLifeAlly = IA.Condition("LXHP", IA.Target("ALHP"), value = 25)
    val lowLifeEnn = IA.Condition("LXHP", IA.Target("ELHP"), value = 25)
}

object RULES {
    val atkBase = IA.Rule(IA.LogicG("ID", CONDITIONS.eachTurn), ACTIONS.atk)
    val defBase = IA.Rule(IA.LogicG("ID", CONDITIONS.eachTurn), ACTIONS.def)
    val quick = IA.Rule(IA.LogicG("ID", CONDITIONS.each2Turn), ACTIONS.leechDef)
    val long = IA.Rule(IA.LogicG("ID", CONDITIONS.each3Turn), ACTIONS.leechAtk)
    val protec = IA.Rule(IA.LogicG("ID", CONDITIONS.lowLifeAlly), ACTIONS.pro)
    val def = IA.Rule(IA.LogicG("ID", CONDITIONS.each2Turn), ACTIONS.def)
}

object IA_TEST {
    val prudent = arrayOf(RULES.def, RULES.atkBase)
    val noBrain = arrayOf(RULES.atkBase)
    val tank = arrayOf(RULES.protec, RULES.atkBase, RULES.long)
    val leecher = arrayOf(RULES.quick, RULES.atkBase)
}