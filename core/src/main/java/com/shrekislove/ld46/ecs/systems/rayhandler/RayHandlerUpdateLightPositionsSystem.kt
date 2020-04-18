package com.shrekislove.ld46.ecs.systems.rayhandler

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent
import com.shrekislove.ld46.ecs.components.rayhandler.RayHandlerBodyComponent
import com.shrekislove.ld46.ecs.components.rayhandler.RayHandlerLightComponent

class RayHandlerUpdateLightPositionsSystem : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val rhb2dBodyMapper = ComponentMapper.getFor(RayHandlerBodyComponent::class.java)
    private val rhb2dLightMapper = ComponentMapper.getFor(RayHandlerLightComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(RayHandlerBodyComponent::class.java, RayHandlerLightComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val bodyLight = rhb2dBodyMapper[i].body
            val light = rhb2dLightMapper[i].light

            light.setPosition(bodyLight.position.x, bodyLight.position.y)
        }
    }

}