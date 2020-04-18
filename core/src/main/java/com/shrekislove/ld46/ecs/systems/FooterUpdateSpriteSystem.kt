package com.shrekislove.ld46.ecs.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.shrekislove.ld46.ecs.components.SpriteComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dFootersControlComponent

class FooterUpdateSpriteSystem : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val spriteMapper = ComponentMapper.getFor(SpriteComponent::class.java)
    private val b2dBodyMapper = ComponentMapper.getFor(Box2dBodyComponent::class.java)
    private val fMapper = ComponentMapper.getFor(Box2dFootersControlComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(SpriteComponent::class.java, Box2dBodyComponent::class.java, Box2dFootersControlComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val sprite = spriteMapper[i].sprite
            val body = b2dBodyMapper[i].body

            val angle = body.linearVelocity.angle()

            sprite.apply {
                val s = when (angle) {
                    in 45f..135f -> 0
                    in 135f..225f -> 64
                    in 225f..315f -> 32
                    else -> 96
                }
                setRegion(s, 0, 32, 32)
                setSize(32f, 32f)
            }
        }
    }

}