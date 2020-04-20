package com.shrekislove.ld46.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.shrekislove.ld46.ecs.components.SpriteComponent
import com.shrekislove.ld46.ecs.components.SukaComponent

class PizdecToEnemy {

    fun create(position: Vector2, dir: Float): Entity {
        return Entity().apply {
            add(SukaComponent().apply {
                angle = dir
                pos = position.cpy()
            })
            add(SpriteComponent(Sprite(Texture("sprites/splash.png"))))
        }
    }

}