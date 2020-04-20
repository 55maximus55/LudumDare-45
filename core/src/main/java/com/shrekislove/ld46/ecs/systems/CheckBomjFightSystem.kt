package com.shrekislove.ld46.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.shrekislove.ld46.Main
import com.shrekislove.ld46.bomj
import com.shrekislove.ld46.screens.BomjFightScreen

class CheckBomjFightSystem : EntitySystem() {

    override fun update(deltaTime: Float) {
        if (bomj) {
            Main.instance.setScreen<BomjFightScreen>()
        }
    }

}