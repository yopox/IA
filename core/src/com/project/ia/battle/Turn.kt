package com.project.ia.battle

import com.project.ia.def.Equip
import com.project.ia.logic.IAHandler
import com.project.ia.logic.Jet
import com.project.ia.logic.LFighter
import com.project.ia.logic.BattleState

/**
 * [Turn] implements [IAHandler].
 * Turn is used to show the different actions happening in a turn.
 * See [Turn.update] for available TurnActions id.
 */

data class TurnAction(val frame: Int = 0, val id: String = "cam", val strContent: String = "Default text.", val actor: Int = 0, val target: Int = 0)

class Turn : IAHandler {

    private val actions: MutableList<TurnAction> = mutableListOf()

    override fun play(fighters: Array<out LFighter>, state: BattleState) {

        actions.clear()

        // Move the camera to the playing character        
        val text = if (fighters[state.charTurn].team == 0) "ally" else "foe"
        actions.add(TurnAction(0, "cam", strContent = text))
        actions.add(TurnAction(0, "pose", strContent = "idle", actor = fighters[state.charTurn].id))

        super.play(fighters, state)

    }

    fun update(frame: Int, camera: Camera, console: Console, fighters: Array<Fighter>, gui: GUI) {

        actions
                .filter { frame == it.frame }
                .forEach {
                    when (it.id) {
                        "cam" -> camera moveTo it.strContent
                        "txt" -> console write it.strContent
                        "gui" -> gui.update(fighters)
                        "move" -> getById(fighters, it.actor) moveTo getById(fighters, it.target)
                        "resetPos" -> getById(fighters, it.actor).resetPos()
                        "face" -> getById(fighters, it.actor).forceFacing = getById(fighters, it.target)
                        "releaseFace" -> getById(fighters, it.actor).forceFacing = null
                        "blink" -> getById(fighters, it.target).blink()
                        "pose" -> getById(fighters, it.actor).setFrame(it.strContent)
                    }
                }

    }

    override fun def(fighters: Array<out LFighter>, state: BattleState) {
        super.def(fighters, state)

        actions.add(TurnAction(15, "txt", strContent = "${fighters[state.charTurn].name} is defending himself!"))
        actions.add(TurnAction(15, "pose", strContent = "defend", actor = fighters[state.charTurn].id))
    }

    override fun pro(fighters: Array<out LFighter>, state: BattleState, target: LFighter?) {
        super.pro(fighters, state, target)

        val actor = fighters[state.charTurn]
        target?.let {
            actions.add(TurnAction(15, "txt", strContent = "${actor.name} protects " + (if (target.id != actor.id) target.name else "itself") + "!"))
        }
    }

    override fun wait(fighters: Array<out LFighter>, state: BattleState) {
        actions.add(TurnAction(15, "txt", strContent = "${fighters[state.charTurn].name} does nothing."))
    }

    override fun atk(fighters: Array<out LFighter>, state: BattleState, target: LFighter?) {
        super.atk(fighters, state, target)
        val actor = fighters[state.charTurn]

        target?.let {

            if (target.id != actor.id) {
                actions.add(TurnAction(15, "move", actor = actor.id, target = target.id))
                actions.add(TurnAction(15, "face", actor = actor.id, target = target.id))
                actions.add(TurnAction(15, "txt", strContent = "${actor.name} attacks ${target.name}!"))
                actions.add(TurnAction(105, "resetPos", actor = actor.id))
                actions.add(TurnAction(120, "releaseFace", actor = actor.id))
            } else {
                actions.add(TurnAction(15, "txt", strContent = "${actor.name} attacks itself!"))
            }

        }
    }

    override fun wrm(fighters: Array<out LFighter>, state: BattleState) {
        super.wrm(fighters, state)
        actions.add(TurnAction(15, "txt", strContent = "${fighters[state.charTurn].name} warms up!"))
    }

    override fun spl(fighters: Array<out LFighter>, state: BattleState, target: LFighter?, nSpl: Int) {
        super.spl(fighters, state, target, nSpl)
        val actor = fighters[state.charTurn]
        val spell = when (nSpl) {
            1 -> Equip.getSpell(actor.spell1)
            else -> Equip.getSpell(actor.spell2)
        }

        target?.let {

            actions.add(TurnAction(15, "pose", strContent = "cast", actor = actor.id))
            actions.add(TurnAction(15, "txt", strContent = "${actor.name} uses ${spell.name} on ${if (target == actor) "itself" else target.name}!"))
            actions.add(TurnAction(65, "pose", strContent = "idle", actor = actor.id))
            actions.add(TurnAction(75, "gui"))


        }
    }

    override fun notarget(actor: LFighter) {
        actions.add(TurnAction(15, "txt", strContent = "${actor.name} doesn't have any target!"))
    }

    override fun damage(actor: LFighter, target: LFighter, amount: String) {
        actions.add(TurnAction(75, "txt", strContent = "${target.name} lost $amount HP!"))
        actions.add(TurnAction(75, "blink", target = target.id))
        actions.add(TurnAction(75, "pose", strContent = "damage", actor = target.id))
        actions.add(TurnAction(75, "gui"))
        actions.add(TurnAction(105, "pose", (target as Fighter).pose, actor = target.id))

    }

    override fun dies(actor: LFighter) {
        actions.add(TurnAction(120, "txt", strContent = "${actor.name} dies!"))
        actions.add(TurnAction(120, "pose", strContent = "death", actor = actor.id))
    }

}

fun hpLost(jets: ArrayList<Jet>): String {
    var damage = 0

    if (jets.size == 1) return "${jets[0].damage}"

    jets.forEach { damage += it.damage }
    val detail = jets.fold("", { acc, jet -> "$acc${jet.damage}+" })
    return "$damage (${detail.dropLast(1)})"
}

fun getById(fighters: Array<Fighter>, id: Int): Fighter = fighters.find { it.id == id }!!