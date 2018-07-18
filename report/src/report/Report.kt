package report

import com.llgames.ia.battle.Fighter
import com.llgames.ia.battle.State
import com.llgames.ia.logic.LFighter
import java.io.File

/**
 * Génère un rapport HTML du combat voulu.
 */

fun main(args: Array<String>) {
    HTMLBuilder.declareTeams(TeamBuilder.fighters)

    var state = State(1, -1, -1, -1)
    HTMLBuilder.newTurn(state.turn)

    while (!checkWin(TeamBuilder.fighters, state)) {

        do {
            state.charTurn++
            if (state.charTurn == TeamBuilder.fighters.size) {
                state.newTurn()
                HTMLBuilder.newTurn(state.turn)
            }
        } while (!TeamBuilder.fighters[state.charTurn].alive)

        HTMLBuilder.startP()
        TurnLogger.play(TeamBuilder.fighters, state)
        HTMLBuilder.endP()

    }

    File("index.html").printWriter().use { out ->
        out.println(HTMLBuilder.getHTML(state.winner + 1))
    }
    print("done")
}

fun checkWin(fighters: Array<LFighter>, state: State): Boolean {
    if (fighters.none { it.team == 0 && it.alive }) {
        state.winner = 1
        return true
    } else if (fighters.none { it.team == 1 && it.alive }) {
        state.winner = 0
        return true
    }
    return false
}
