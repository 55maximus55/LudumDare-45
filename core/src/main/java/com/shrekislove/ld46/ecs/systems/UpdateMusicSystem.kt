package com.shrekislove.ld46.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.shrekislove.ld46.pyat_robbed

class UpdateMusicSystem : EntitySystem() {

    companion object {
        val music1 = Gdx.audio.newMusic(Gdx.files.internal("music/song1.wav"))
        val music2 = Gdx.audio.newMusic(Gdx.files.internal("music/song2.wav"))

        fun stop() {
            music1.stop()
            music2.stop()
        }
    }

    override fun update(deltaTime: Float) {
        if (!pyat_robbed && !music1.isPlaying) {
            music1.play()
        }
        if (pyat_robbed) {
            music1.stop()
            if (!music2.isPlaying) {
                music2.play()
            }
        }
    }

}