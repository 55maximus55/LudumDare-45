package com.shrekislove.ld46.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.shrekislove.ld46.Main
import ktx.actors.onChange
import ktx.vis.table

class MainMenuScreen : LibScreen() {

    val view = table {
        setFillParent(true)
        center()

        textButton(text = "New Game").apply {
            onChange {
                Main.context.inject<GameScreen>().isNewGame = true
                Main.instance.setScreen<GameScreen>()
            }
        }
        row()
        textButton(text = "Exit").apply {
            onChange {
                Gdx.app.exit()
            }
        }
    }

    override fun show() {
        Main.context.inject<Stage>().addActor(view)
    }

    override fun hide() {
        view.remove()
    }

}