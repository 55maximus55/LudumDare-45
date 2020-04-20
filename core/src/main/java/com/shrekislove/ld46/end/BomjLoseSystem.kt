package com.shrekislove.ld46.end

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.shrekislove.ld46.Main
import ktx.app.use
import java.lang.Exception

class BomjLoseSystem : EntitySystem() {
    val textures = ArrayList<Texture>().apply {
        add(Texture("bjl/1.png"))
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