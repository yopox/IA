package report

import com.llgames.ia.battle.logic.LFighter

object TeamBuilder {
    val f1 = LFighter("Ixous", 0, 0)
    val f2 = LFighter("yopox", 0, 1)
    val f3 = LFighter("Art", 0, 2)

    val f4 = LFighter("suoxI", 1, 3)
    val f5 = LFighter("xopoy", 1, 4)
    val f6 = LFighter("trA", 1, 5)

    val fighters: Array<LFighter>

    init {
        f1.changeJob("Dark Mage")
        f1.setIA("OFFENSIVE")
        f2.changeJob("White Mage")
        f2.setIA("DEFENSIVE")
        f3.changeJob("Human")
        f3.setIA("OFFENSIVE")

        f4.changeJob("White Mage")
        f4.setIA("DEFENSIVE")
        f5.changeJob("Dark Mage")
        f5.setIA("OFFENSIVE")
        f6.changeJob("Human")
        f6.setIA("OFFENSIVE")

        fighters = arrayOf(f1, f2, f3, f4, f5, f6)
        fighters.forEach { it.prepare() }
    }
}