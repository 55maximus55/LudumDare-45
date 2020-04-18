package com.shrekislove.ld46

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import com.strongjoshua.console.Console
import ktx.app.KtxGame
import ktx.async.enableKtxCoroutines
import ktx.inject.Context
import com.shrekislove.ld46.console.GameConsole
import com.shrekislove.ld46.screens.GameScreen
import com.shrekislove.ld46.screens.LibScreen
import com.shrekislove.ld46.screens.MainMenuScreen

class Main : KtxGame<LibScreen>() {

    companion object {
        lateinit var instance: Main
        val context = Context()
    }

    override fun create() {
        instance = this

        enableKtxCoroutines(asynchronousExecutorConcurrencyLevel = 1)
        context.register {
            VisUI.load()
            bindSingleton(Stage(ScreenViewport()))
            bindSingleton<Console>(GameConsole.createConsole())
            bindSingleton<Batch>(SpriteBatch())

            bindSingleton(MainMenuScreen())
            bindSingleton(GameScreen())
        }
        Gdx.input.inputProcessor = context.inject<Stage>()

        addScreen(context.inject<MainMenuScreen>())
        addScreen(context.inject<GameScreen>())

        setScreen<MainMenuScreen>()
    }

    override fun render() {
        super.render()
        context.inject<Stage>().apply {
            act()
            draw()
        }
        context.inject<Console>().draw()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        context.inject<Console>().refresh()
        context.inject<Stage>().viewport.update(width, height, true)
    }

    override fun <Type : LibScreen> setScreen(type: Class<Type>) {
        currentScreen.hide()
        currentScreen = getScreen(type)
        currentScreen.show()
        currentScreen.resize(Gdx.graphics.width, Gdx.graphics.height)
    }

}