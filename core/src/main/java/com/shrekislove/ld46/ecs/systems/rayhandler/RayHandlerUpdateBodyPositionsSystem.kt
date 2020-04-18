package com.shrekislove.ld46.ecs.systems.rayhandler

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent
import com.shrekislove.ld46.ecs.components.rayhandler.RayHandlerBodyComponent

class RayHandlerUpdateBodyPositionsSystem(val PPM: Float) : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val b2dBodyMapper = ComponentMapper.getFor(Box2dBodyComponent::class.java)
    private val rhb2dBodyMapper = ComponentMapper.getFor(RayHandlerBodyComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(RayHandlerBodyComponent::class.java, Box2dBodyComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val bodyWorld = b2dBodyMapper[i].body
            val bodyLight = rhb2dBodyMapper[i].body

            bodyLight.setTransform(bodyWorld.position.cpy().scl(PPM), 0f)
        }
    }

}