package com.shrekislove.ld46.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.kotcrab.vis.ui.widget.VisLabel
import com.shrekislove.ld46.hungry
import com.shrekislove.ld46.money

class GameHudUpdateSystem(val moneyLabel: VisLabel, val hungryLabel: VisLabel) : EntitySystem() {

    override fun update(deltaTime: Float) {
        if (money < 0) {
            moneyLabel.isVisible = false
            hungryLabel.isVisible = false
        }
        else {
            moneyLabel.isVisible = true
            hungryLabel.isVisible = true

            moneyLabel.setText("Money: $money₽")
            hungryLabel.setText("Hunger: ${hungry}%")
        }
    }

}