package com.llgames.ia.battle

import com.llgames.ia.battle.logic.*

/**
 * [Turn] implements [IAHandler].
 * Turn is used to **show** the different actions happening in a turn.
 * Here are some [TurnAction]s :
 * - [frame, "cam", [], "foe" / "ally"]
 * - [frame, "txt", [], "Text to write"]
 * - [frame, "def", [0], ""]
 */

data class TurnAction(val frame: Int = 0, val id: String = "cam", val strContent: String = "Default text.", val fighterContent: Array<Int>? = null)

class Turn : IAHandler {

    private val actions: MutableList<TurnAction> = mutableListOf()

    override fun play(fighters: Array<out LFighter>, state: State) {

        actions.clear()

        // Move the camera to the playing character        
        val text = if (fighters[state.charTurn].team == 0) "ally" else "foe"
        actions.add(TurnAction(0, "cam", strContent = text))

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
                    //TODO: Make it better...
                        "move" -> fighters[it.fighterContent!![0]] moveTo fighters[it.fighterContent[1]]
                        "resetPos" -> fighters[it.fighterContent!![0]].resetPos()
                        "face" -> fighters[it.fighterContent!![0]].forceFacing = fighters[it.fighterContent[1]]
                        "releaseFace" -> fighters[it.fighterContent!![0]].forceFacing = null
                        "blink" -> fighters[it.fighterContent!![0]].blink()
                    }
                }

    }

    override fun def(fighters: Array<out LFighter>, state: State) {
        super.def(fighters, state)

        actions.add(TurnAction(15, "txt", strContent = "${fighters[state.charTurn].name} is defending himself!"))
    }

    override fun pro(fighters: Array<out LFighter>, state: State, target: LFighter?) {
        super.pro(fighters, state, target)

        val actor = fighters[state.charTurn]
        target?.let {
            actions.add(TurnAction(15, "txt", strContent = "${actor.name} protects " + (if (target.id != actor.id) target.name else "itself") + "!"))
        }
    }

    override fun wait(fighters: Array<out LFighter>, state: State) {
        actions.add(TurnAction(15, "txt", strContent = "${fighters[state.charTurn].name} does nothing."))
    }

    override fun atk(fighters: Array<out LFighter>, state: State, target: LFighter?, weapon: Weapon?) {
        super.atk(fighters, state, target, weapon)
        val actor = fighters[state.charTurn]

        target?.let {

            if (target.id != actor.id) {
                actions.add(TurnAction(15, "move", fighterContent = arrayOf(actor.id, target.id)))
                actions.add(TurnAction(15, "face", fighterContent = arrayOf(actor.id, target.id)))
                actions.add(TurnAction(15, "txt", strContent = "${actor.name} attacks ${target.name}!"))
                actions.add(TurnAction(105, "resetPos", fighterContent = arrayOf(actor.id)))
                actions.add(TurnAction(120, "releaseFace", fighterContent = arrayOf(actor.id)))
            } else {
                actions.add(TurnAction(15, "txt", strContent = "${actor.name} attacks itself!"))
            }

        }
    }

    override fun wrm(fighters: Array<out LFighter>, state: State, target: LFighter?) {
        super.wrm(fighters, state, target)
        actions.add(TurnAction(15, "txt", strContent = "${fighters[state.charTurn].name} warms up!"))
    }

    override fun wpn(fighters: Array<out LFighter>, state: State, target: LFighter?) {
        TODO("not implemented")
    }

    override fun spl(fighters: Array<out LFighter>, state: State, target: LFighter?) {
        TODO("not implemented")
    }

    override fun notarget(actor: LFighter) {
        actions.add(TurnAction(15, "txt", strContent = "${actor.name} doesn't have any target!"))
    }

    override fun damage(actor: LFighter, target: LFighter, amount: String) {
        actions.add(TurnAction(75, "txt", strContent = "${target.name} lost $amount HP!"))
        actions.add(TurnAction(75, "blink", fighterContent = arrayOf(target.id)))
        actions.add(TurnAction(75, "gui"))
    }

    override fun dies(actor: LFighter) {
        actions.add(TurnAction(120, "txt", strContent = "${actor.name} dies!"))
    }

}

fun hpLost(jets: ArrayList<Jet>): String {
    var damage = 0

    if (jets.size == 1) return "${jets[0].damage}"

    jets.forEach { damage += it.damage }
    val detail = jets.fold("", { acc, jet -> "$acc${jet.damage}+" })
    return "$damage (${detail.dropLast(1)})"
}