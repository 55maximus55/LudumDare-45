package com.shrekislove.ld46.ecs.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.physics.box2d.World
import com.shrekislove.ld46.ecs.components.Box2dBodyComponent
import com.shrekislove.ld46.ecs.components.SpriteComponent

class Box2dTopdownUpdateSpritePositionsSystem(val world: World, val PPM: Float) : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val spriteMapper = ComponentMapper.getFor(SpriteComponent::class.java)
    private val b2dBodyMapper = ComponentMapper.getFor(Box2dBodyComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(SpriteComponent::class.java, Box2dBodyComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val sprite = spriteMapper[i].sprite
            val body = b2dBodyMapper[i].body

            sprite.setPosition(
                    body.position.x * PPM - sprite.width / 2f,
                    body.position.y * PPM - sprite.height / 2f
            )
        }
    }

}