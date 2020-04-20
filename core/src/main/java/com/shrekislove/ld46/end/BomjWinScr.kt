package com.shrekislove.ld46.end

import com.shrekislove.ld46.screens.LibScreen

class BomjWinScr : LibScreen() {

    init {
        ecsEngine.addSystem(BomjWinSystem())
    }

}