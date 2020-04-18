package com.shrekislove.ld46.ecs.systems.box2d

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.shrekislove.ld46.ecs.components.SpeedComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dFootersControlComponent

class Box2dFootersControlSystem : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val fMapper = ComponentMapper.getFor(Box2dFootersControlComponent::class.java)
    private val b2dBodyMapper = ComponentMapper.getFor(Box2dBodyComponent::class.java)
    private val speedMapper = ComponentMapper.getFor(SpeedComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(Box2dFootersControlComponent::class.java, Box2dBodyComponent::class.java, SpeedComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val positions = fMapper[i].positions
            val n = fMapper[i].i
            val body = b2dBodyMapper[i].body
            val speed = speedMapper[i].speed

            val currentPos = body.position
            val nextPos = positions[n].cpy()

            val ctrl = nextPos.sub(currentPos)
            if (ctrl.len() <= 0.25f) {
                fMapper[i].i++
                if (fMapper[i].i >= positions.size) {
                    fMapper[i].i = 0
                }
            }
            ctrl.setLength(speed)

            body.linearVelocity = ctrl
        }
    }

}