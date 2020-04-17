package com.shrekislove.ld46

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.strongjoshua.console.Console
import ktx.app.KtxGame
import ktx.async.enableKtxCoroutines
import ktx.inject.Context
import com.shrekislove.ld46.console.GameConsole
import com.shrekislove.ld46.screens.GameScreen
import com.shrekislove.ld46.screens.LibScreen

class Main : KtxGame<LibScreen>() {

    companion object {
        lateinit var instance: Main
        val context = Context()
    }

    override fun create() {
        instance = this

        enableKtxCoroutines(asynchronousExecutorConcurrencyLevel = 1)
        context.register {
            bindSingleton<Console>(GameConsole.createConsole())
            bindSingleton<Batch>(SpriteBatch())

            bindSingleton(GameScreen())
        }

        addScreen(context.inject<GameScreen>())

        setScreen<GameScreen>()
    }

    override fun render() {
        super.render()
        context.inject<Console>().draw()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        context.inject<Console>().refresh()
    }

}