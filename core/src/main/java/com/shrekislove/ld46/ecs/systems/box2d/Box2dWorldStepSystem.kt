package com.shrekislove.ld46.ecs.systems.box2d

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.physics.box2d.World

class Box2dWorldStepSystem(val world: World, val box2dVelocityIterations: Int, val box2dPositionsIterations: Int) : EntitySystem() {

    override fun update(deltaTime: Float) {
        world.step(deltaTime, box2dVelocityIterations, box2dPositionsIterations)
    }

}