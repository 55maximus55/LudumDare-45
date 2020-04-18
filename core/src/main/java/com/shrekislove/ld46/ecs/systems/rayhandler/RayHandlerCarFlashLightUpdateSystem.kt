package com.shrekislove.ld46.ecs.systems.rayhandler

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.shrekislove.ld46.ecs.components.SpriteComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dFootersControlComponent
import com.shrekislove.ld46.ecs.components.rayhandler.RayHandlerBodyComponent
import com.shrekislove.ld46.ecs.components.rayhandler.RayHandlerFlashLightComponent

class RayHandlerCarFlashLightUpdateSystem : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val b2dBodyMapper = ComponentMapper.getFor(Box2dBodyComponent::class.java)
    private val rhb2dBodyMapper = ComponentMapper.getFor(RayHandlerBodyComponent::class.java)
    private val fMapper = ComponentMapper.getFor(Box2dFootersControlComponent::class.java)
    private val rhb2dFlashLightMapper = ComponentMapper.getFor(RayHandlerFlashLightComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(Box2dBodyComponent::class.java, RayHandlerFlashLightComponent::class.java, RayHandlerBodyComponent::class.java, Box2dFootersControlComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val body = rhb2dBodyMapper[i].body
            val light = rhb2dFlashLightMapper[i].light

            val angle = b2dBodyMapper[i].body.linearVelocity.angle()

            light.setPosition(body.position.x, body.position.y)
            light.setDirection(angle)
        }
    }

}