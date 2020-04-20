package com.shrekislove.ld46.end

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.shrekislove.ld46.Main
import com.shrekislove.ld46.screens.MainMenuScreen
import ktx.app.use
import java.lang.Exception

class EndSystem : EntitySystem() {

    val textures = ArrayList<Texture>().apply {
        add(Texture("end/1.png"))
        add(Texture("end/2.png"))
        add(Texture("end/3.png"))
        add(Texture("end/4.png"))
        add(Texture("end/5.png"))
        add(Texture("end/6.png"))
        add(Texture("end/7.png"))
        add(Texture("end/8.png"))
        add(Texture("end/9.png"))
    }

    var timer = 2f
    var i = 0

    val batch = Main.context.inject<Batch>()
    val camera = OrthographicCamera()

    override fun update(deltaTime: Float) {
        timer -= deltaTime
        if (timer < 0f) {
            i++
            timer = 2f
        }

        val fov = if (Gdx.graphics.width.toFloat() / Gdx.graphics.height.toFloat() > 768f / 768f)
            768f
        else
            768f / Gdx.graphics.width.toFloat() * Gdx.graphics.height.toFloat()

        try {
            camera.apply {
                viewportWidth = fov * Gdx.graphics.width.toFloat() / Gdx.graphics.height.toFloat()
                viewportHeight = fov
                update()
            }
            batch.apply {
                projectionMatrix = camera.combined
                use {
                    draw(textures[i], -textures[i].width.toFloat() / 2, -textures[i].height.toFloat() / 2)
                }
            }
        } catch (e: Exception) {
            Gdx.app.exit()
        }
    }

}