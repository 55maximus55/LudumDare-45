package com.shrekislove.ld46.screens

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.shrekislove.ld46.Main
import com.shrekislove.ld46.ecs.components.Box2dBodyComponent
import com.shrekislove.ld46.ecs.components.Box2dTopdownPlayerControllerComponent
import com.shrekislove.ld46.ecs.components.SpeedComponent
import com.shrekislove.ld46.ecs.components.SpriteComponent
import com.shrekislove.ld46.ecs.systems.Box2dTopdownPlayerMovementSystem
import com.shrekislove.ld46.ecs.systems.Box2dTopdownUpdateSpritePositionsSystem
import com.shrekislove.ld46.ecs.systems.Box2dWorldStepSystem
import com.shrekislove.ld46.ecs.systems.RenderSystem
import com.shrekislove.ld46.utils.Box2dWallsFromTiledMapCreator
import ktx.app.use
import ktx.box2d.body

class GameScreen : LibScreen() {

    val camera = OrthographicCamera().apply {
        setToOrtho(false)

        position.x = 0f
        position.y = 0f
    }
    val fov = 256f

    val map = TmxMapLoader().load("maps/town.tmx")
    val PPM = 32f

    val world = World(Vector2(), true)

    init {
        ecsEngine.apply {
            addSystem(Box2dTopdownPlayerMovementSystem())
            addSystem(Box2dWorldStepSystem(world, 10, 10))
            addSystem(Box2dTopdownUpdateSpritePositionsSystem(world, PPM))
            addSystem(RenderSystem(camera, map, world, PPM))
        }
    }

    override fun show() {
        ecsEngine.apply {
            // player entity
            addEntity(Entity().apply {
                add(SpriteComponent(Sprite(Texture("sprites/player.png"))))
                add(Box2dBodyComponent(world.body {
                    type = BodyDef.BodyType.DynamicBody
                    circle(radius = 0.4f) {}
                }))
                add(Box2dTopdownPlayerControllerComponent())
                add(SpeedComponent(4f))
            })
        }
        Box2dWallsFromTiledMapCreator().createWalls(world, PPM, map)
    }

    override fun resize(width: Int, height: Int) {
        camera.apply {
            viewportWidth = fov * Gdx.graphics.width.toFloat() / Gdx.graphics.height.toFloat()
            viewportHeight = fov
            update()
        }
    }

}