package report

import com.llgames.ia.logic.JOBS
import com.llgames.ia.logic.LFighter

object TeamBuilder {
    val f1 = LFighter("Ixous", 0, 0)
    val f2 = LFighter("yopox", 0, 1)
    val f3 = LFighter("Art", 0, 2)

    val f4 = LFighter("suoxI", 1, 3)
    val f5 = LFighter("xopoy", 1, 4)
    val f6 = LFighter("trA", 1, 5)

    val fighters: Array<LFighter>

    init {
        f1.changeJob(JOBS.DARKMAGE)
        f1.setIA()
        f2.changeJob(JOBS.DARKMAGE)
        f2.setIA()
        f3.changeJob(JOBS.DARKMAGE)
        f3.setIA()

        f4.changeJob(JOBS.DARKMAGE)
        f4.setIA()
        f5.changeJob(JOBS.DARKMAGE)
        f5.setIA()
        f6.changeJob(JOBS.DARKMAGE)
        f6.setIA()

        fighters = arrayOf(f1, f2, f3, f4, f5, f6)
        fighters.forEach { it.prepare() }
    }
}