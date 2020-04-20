package com.shrekislove.ld46.ecs.systems.rayhandler

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.shrekislove.ld46.ecs.components.box2d.Box2dTopdownPlayerControllerComponent
import com.shrekislove.ld46.ecs.components.rayhandler.RayHandlerBodyComponent
import com.shrekislove.ld46.ecs.components.rayhandler.RayHandlerFlashLightComponent

class RayHandlerPlayerFlashLightSystem : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val pcMapper = ComponentMapper.getFor(Box2dTopdownPlayerControllerComponent::class.java)
    private val rhb2dBodyMapper = ComponentMapper.getFor(RayHandlerBodyComponent::class.java)
    private val rhb2dFlashLightMapper = ComponentMapper.getFor(RayHandlerFlashLightComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(Box2dTopdownPlayerControllerComponent::class.java, RayHandlerBodyComponent::class.java, RayHandlerFlashLightComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val body = rhb2dBodyMapper[i].body
            val light = rhb2dFlashLightMapper[i].light

            light.setPosition(body.position.x, body.position.y)
            val a = Vector2(Gdx.graphics.width.toFloat() / 2, Gdx.graphics.height.toFloat() / 2)
            val b = Vector2(Gdx.input.x.toFloat(), (Gdx.graphics.height - Gdx.input.y).toFloat())
            val dir = b.sub(a).angle()
            light.setDirection(dir)
        }
    }

}