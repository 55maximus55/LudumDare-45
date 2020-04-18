package com.shrekislove.ld46.ecs.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent
import com.shrekislove.ld46.ecs.components.CameraTargetComponent

class UpdateCameraPositionSystem(val camera: OrthographicCamera, val PPM: Float) : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val ctMapper = ComponentMapper.getFor(CameraTargetComponent::class.java)
    private val b2dBodyMapper = ComponentMapper.getFor(Box2dBodyComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(CameraTargetComponent::class.java, Box2dBodyComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val targetPosition = b2dBodyMapper[i].body.position.cpy().scl(PPM)
            val camPos = Vector2(camera.position.x, camera.position.y)
            val camMove = targetPosition.sub(camPos).scl(0.1f)
            camera.position.x += camMove.x
            camera.position.y += camMove.y
            camera.update()
        }
    }
}