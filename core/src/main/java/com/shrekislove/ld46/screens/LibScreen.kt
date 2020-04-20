package com.shrekislove.ld46.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import ktx.app.KtxScreen

open class LibScreen : KtxScreen {

    val ecsEngine = Engine()

    override fun render(delta: Float) {
        ecsEngine.update(delta)
    }

}