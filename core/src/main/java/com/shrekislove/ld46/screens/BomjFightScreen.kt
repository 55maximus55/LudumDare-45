package com.shrekislove.ld46.screens

import com.shrekislove.ld46.ecs.systems.FourInRowSystem

class BomjFightScreen : LibScreen() {

    override fun show() {
        ecsEngine.addSystem(FourInRowSystem())
    }

    override fun hide() {
        for (i in ecsEngine.systems) {
            ecsEngine.removeSystem(i)
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
    }
}