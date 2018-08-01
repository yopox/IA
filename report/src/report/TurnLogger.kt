package report

import com.llgames.ia.logic.IAHandler
import com.llgames.ia.logic.LFighter
import com.llgames.ia.states.BattleState

object TurnLogger: IAHandler {
    override fun wait(fighters: Array<out LFighter>, state: BattleState) {
        HTMLBuilder.write("${fighters[state.charTurn].name} waits.")
    }

    override fun wpn(fighters: Array<out LFighter>, state: BattleState, target: LFighter?) {
        TODO("not implemented")
    }

    override fun damage(actor: LFighter, target: LFighter, amount: String) {
        HTMLBuilder.write("${target.name} lost $amount HP!")
    }

    override fun dies(actor: LFighter) {
        HTMLBuilder.write("${actor.name} dies!")
    }

    override fun notarget(actor: LFighter) {
        HTMLBuilder.write("${actor.name} doesn't have any target.")
    }

    override fun atk(fighters: Array<out LFighter>, state: BattleState, target: LFighter?) {
        HTMLBuilder.write("${fighters[state.charTurn].name} attacks ${target?.name}!")
        super.atk(fighters, state, target)
    }

    override fun def(fighters: Array<out LFighter>, state: BattleState) {
        HTMLBuilder.write("${fighters[state.charTurn].name} defends himself!")
        super.def(fighters, state)
    }

    override fun pro(fighters: Array<out LFighter>, state: BattleState, target: LFighter?) {
        HTMLBuilder.write("${fighters[state.charTurn].name} protects ${target?.name}!")
        super.pro(fighters, state, target)
    }

    override fun wrm(fighters: Array<out LFighter>, state: BattleState, target: LFighter?) {
        HTMLBuilder.write("${fighters[state.charTurn].name} warms up!")
        super.wrm(fighters, state, target)
    }

}
