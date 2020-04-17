package com.shrekislove.ld46.console

import com.strongjoshua.console.GUIConsole

class GameConsole {

    companion object {
        fun createConsole(): GUIConsole {
            return GUIConsole().apply {
                displayKeyID = com.badlogic.gdx.Input.Keys.GRAVE
                enableSubmitButton(true)
                setCommandExecutor(GameConsoleCommandExecutor())
            }
        }
    }

}