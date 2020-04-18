package com.shrekislove.ld46.ecs.systems.box2d

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dTopdownPlayerControllerComponent
import com.shrekislove.ld46.ecs.components.SpeedComponent

class Box2dTopdownPlayerMovementSystem : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val pcMapper = ComponentMapper.getFor(Box2dTopdownPlayerControllerComponent::class.java)
    private val b2dBodyMapper = ComponentMapper.getFor(Box2dBodyComponent::class.java)
    private val speedMapper = ComponentMapper.getFor(SpeedComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(Box2dTopdownPlayerControllerComponent::class.java, Box2dBodyComponent::class.java, SpeedComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val body = b2dBodyMapper[i].body
            val speed = speedMapper[i].speed

            val control = Vector2()
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                control.y += 1f
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                control.x -= 1f
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                control.y -= 1f
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                control.x += 1f
            }
            control.clamp(0f, 1f).scl(speed)

            body.linearVelocity = control
        }
    }

}