package com.shrekislove.ld46.entities

import box2dLight.PointLight
import box2dLight.RayHandler
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.shrekislove.ld46.ecs.components.SpeedComponent
import com.shrekislove.ld46.ecs.components.SpriteComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dFootersControlComponent
import com.shrekislove.ld46.ecs.components.rayhandler.RayHandlerBodyComponent
import com.shrekislove.ld46.ecs.components.rayhandler.RayHandlerLightComponent
import com.shrekislove.ld46.utils.Box2dBodyData
import ktx.box2d.body

class Car {

    fun create(positions: ArrayList<Vector2>, world: World, lightWorld: World, rayHandler: RayHandler, PPM: Float): Entity {
        val e = Entity()
        e.apply {
            add(Box2dFootersControlComponent(positions))
            add(SpriteComponent(Sprite(Texture("sprites/car.png"))))
            add(Box2dBodyComponent(world.body {
                type = BodyDef.BodyType.DynamicBody
                this.position.set(positions.first().cpy())
                circle(radius = 0.4f) {}
                userData = Box2dBodyData().apply {
                    collision = true
                    tag = "car"
                    entity = e
                }
            }))
            add(SpeedComponent(12f))

            add(RayHandlerBodyComponent(lightWorld.body {
                type = BodyDef.BodyType.DynamicBody
                this.position.set(positions.first().cpy().scl(PPM))
                circle(radius = 0.4f * PPM) {}
            }))
            add(RayHandlerLightComponent(PointLight(rayHandler, 100, Color.BLACK, 1f * PPM, 32f, 32f)))
        }
        return e
    }

}