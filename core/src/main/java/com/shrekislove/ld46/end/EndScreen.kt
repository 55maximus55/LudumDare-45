package com.shrekislove.ld46.end

import com.shrekislove.ld46.screens.LibScreen

class EndScreen : LibScreen() {

    init {
        ecsEngine.addSystem(EndSystem())
    }

}