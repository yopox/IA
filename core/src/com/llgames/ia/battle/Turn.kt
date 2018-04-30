package com.llgames.ia.battle

import com.llgames.ia.battle.logic.IAHandler
import com.llgames.ia.battle.logic.Weapon
import com.llgames.ia.battle.logic.damageCalculation

/**
 * Turn is used to manage the different actions happening in a turn.
 * Here are the different actions :
 * - [frame, "cam", [], "foe" / "ally"]
 * - [frame, "txt", [], "Text to write"]
 * - [frame, "def", [0], ""]
 */

data class TurnAction(val frame: Int = 0, val id: String = "cam", val strContent: String = "Default text.", val fighterContent: Array<Fighter>? = null)

class Turn() : IAHandler {

    private val actions: MutableList<TurnAction> = mutableListOf()

    override fun newTurn(fighters: Array<Fighter>, state: State) {

        actions.clear()

        // Move the camera to the playing character        
        val text = if (fighters[state.charTurn].team == 0) "ally" else "foe"
        actions.add(TurnAction(0, "cam", strContent = text))

        super.newTurn(fighters, state)

    }

    fun update(frame: Int, camera: Camera, console: Console, fighters: Array<Fighter>, gui: GUI) {

        actions
                .filter { frame == it.frame }
                .forEach {
                    when (it.id) {
                        "cam" -> camera moveTo it.strContent
                        "txt" -> console write it.strContent
                        "gui" -> gui.update(fighters)
                        "move" -> it.fighterContent!![0] moveTo it.fighterContent[1]
                        "resetPos" -> it.fighterContent!![0].resetPos()
                        "face" -> it.fighterContent!![0].forceFacing = it.fighterContent[1]
                        "releaseFace" -> it.fighterContent!![0].forceFacing = null
                        "blink" -> it.fighterContent!![0].blink()
                    }
                }

    }

    override fun def(fighters: Array<Fighter>, state: State) {
        actions.add(TurnAction(15, "txt", strContent = fighters[state.charTurn].name + " is defending himself!"))
    }

    override fun wpn(fighters: Array<Fighter>, state: State, target: Fighter) {
        TODO("not implemented")
    }

    override fun spl(fighters: Array<Fighter>, state: State, target: Fighter) {
        TODO("not implemented")
    }

    override fun pro(fighters: Array<Fighter>, state: State, target: Fighter) {
        TODO("not implemented")
    }

    override fun wait(fighters: Array<Fighter>, state: State) {
        actions.add(TurnAction(15, "txt", strContent = fighters[state.charTurn].name + " does nothing."))
    }

    override fun atk(fighters: Array<Fighter>, state: State, target: Fighter, weapon: Weapon?) {
        super.atk(fighters, state, target, weapon)
        val actor = fighters[state.charTurn]

        if (target.id != actor.id || target.team != actor.team) {
            actions.add(TurnAction(15, "move", fighterContent = arrayOf(actor, target)))
            actions.add(TurnAction(15, "face", fighterContent = arrayOf(actor, target)))
            actions.add(TurnAction(15, "txt", strContent = actor.name + " attacks " + target.name + "!"))
            actions.add(TurnAction(105, "resetPos", fighterContent = arrayOf(actor)))
            actions.add(TurnAction(120, "releaseFace", fighterContent = arrayOf(actor)))
        } else {
            actions.add(TurnAction(15, "txt", strContent = actor.name + " attacks itself!"))
        }

        actions.add(TurnAction(75, "txt", strContent = target.name + " lost " + damageCalculation(actor, target, weapon) + "HP!"))
        actions.add(TurnAction(75, "gui"))
        actions.add(TurnAction(75, "blink", fighterContent = arrayOf(target)))

    }

}