package com.shrekislove.ld46.end

import com.shrekislove.ld46.screens.LibScreen

class BomjLoseScr : LibScreen() {

    init {
        ecsEngine.addSystem(BomjLoseSystem())
    }

}