package report

import com.llgames.ia.def.JOBS
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
        f1.changeJob(JOBS.getJob("DARKMAGE"))
        f1.changeIA("ID EXT 1 ATK ELHP")
        f2.changeJob(JOBS.getJob("DARKMAGE"))
        f2.changeIA("ID EXT 1 ATK ELHP")
        f3.changeJob(JOBS.getJob("DARKMAGE"))
        f3.changeIA("ID EXT 1 ATK ELHP")

        f4.changeJob(JOBS.getJob("DARKMAGE"))
        f4.changeIA("ID EXT 1 ATK ELHP")
        f5.changeJob(JOBS.getJob("DARKMAGE"))
        f5.changeIA("ID EXT 1 ATK ELHP")
        f6.changeJob(JOBS.getJob("DARKMAGE"))
        f6.changeIA("ID EXT 1 ATK ELHP")

        fighters = arrayOf(f1, f2, f3, f4, f5, f6)
        fighters.forEach { it.resetStats(true) }
    }
}