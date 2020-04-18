package com.shrekislove.ld46.ecs.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.shrekislove.ld46.ecs.components.PlayerAnimationTimer
import com.shrekislove.ld46.ecs.components.SpriteComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent

class PlayerSpriteUpdateSystem : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val pMapper = ComponentMapper.getFor(PlayerAnimationTimer::class.java)
    private val spriteMapper = ComponentMapper.getFor(SpriteComponent::class.java)
    private val b2dBodyMapper = ComponentMapper.getFor(Box2dBodyComponent::class.java)

    var isRight = false

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(SpriteComponent::class.java, Box2dBodyComponent::class.java, PlayerAnimationTimer::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val sprite = spriteMapper[i].sprite
            val body = b2dBodyMapper[i].body
            val frameTime = pMapper[i].frameTime
            pMapper[i].timer += deltaTime


            sprite.apply {
                var s = 0

                s = when {
                    body.linearVelocity.len() < 0.5f -> {
                        0
                    }
                    pMapper[i].timer % frameTime < frameTime / 2f -> {
                        27
                    }
                    else -> {
                        54
                    }
                }
                if (body.linearVelocity.len() > 0.5f)
                    isRight = body.linearVelocity.angle() in 90f..270f

                setRegion(s, if (isRight) 0 else 33, 27, 33)
                setSize(27f, 33f)
            }
        }
    }

}