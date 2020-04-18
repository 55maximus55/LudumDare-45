package com.shrekislove.ld46.ecs.systems.box2d

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.shrekislove.ld46.ecs.components.SpeedComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dTeleportComponent

class Box2dTeleportSystem : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val b2dBodyMapper = ComponentMapper.getFor(Box2dBodyComponent::class.java)
    private val b2dTeleportMapper = ComponentMapper.getFor(Box2dTeleportComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(Box2dBodyComponent::class.java, Box2dTeleportComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val body = b2dBodyMapper[i].body
            val isTeleport = b2dTeleportMapper[i].isTeleport
            val teleportPos = b2dTeleportMapper[i].newPosition

            if (isTeleport) {
                body.setTransform(teleportPos, 0f)
                b2dTeleportMapper[i].isTeleport = false
            }
        }
    }

}