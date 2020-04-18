package com.shrekislove.ld46.screens

import box2dLight.RayHandler
import box2dLight.RayHandler.setGammaCorrection
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.shrekislove.ld46.ecs.systems.*
import com.shrekislove.ld46.ecs.systems.box2d.Box2dTopdownPlayerMovementSystem
import com.shrekislove.ld46.ecs.systems.box2d.Box2dTopdownUpdateSpritePositionsSystem
import com.shrekislove.ld46.ecs.systems.box2d.Box2dWorldStepSystem
import com.shrekislove.ld46.ecs.systems.rayhandler.RayHandlerPlayerFlashLightSystem
import com.shrekislove.ld46.ecs.systems.rayhandler.RayHandlerUpdateBodyPositionsSystem
import com.shrekislove.ld46.ecs.systems.rayhandler.RayHandlerUpdateLightPositionsSystem
import com.shrekislove.ld46.entities.Player
import com.shrekislove.ld46.utils.Box2dContactListener
import com.shrekislove.ld46.utils.Box2dWallsFromTiledMapCreator
import com.shrekislove.ld46.utils.ObjectFromTiledMapGetter

class GameScreen : LibScreen() {

    val camera = OrthographicCamera().apply {
        setToOrtho(false)

        position.x = 0f
        position.y = 0f
    }
    val fov = 256f

    val map = TmxMapLoader().load("maps/home.tmx")
    val PPM = 32f

    val world = World(Vector2(), true).apply {
        setContactListener(Box2dContactListener())
    }
    val lightWorld = World(Vector2(), true)
    val rayHandler = RayHandler(lightWorld).apply {
        setAmbientLight(0.2f)
        setBlur(true)
        setBlurNum(1)
        setCulling(true)
        setGammaCorrection(true)
    }

    init {
        ecsEngine.apply {
            addSystem(Box2dTopdownPlayerMovementSystem())
            addSystem(Box2dWorldStepSystem(world, 10, 10))

            addSystem(RayHandlerUpdateBodyPositionsSystem(PPM))
            addSystem(RayHandlerUpdateLightPositionsSystem())
            addSystem(RayHandlerPlayerFlashLightSystem())

            addSystem(Box2dTopdownUpdateSpritePositionsSystem(world, PPM))
            addSystem(UpdateCameraPositionSystem(camera, PPM))
            addSystem(RenderSystem(camera, map, world, lightWorld, rayHandler, PPM))
        }
    }

    override fun show() {
        ecsEngine.apply {
            val playerStartPos = ObjectFromTiledMapGetter().getPosition(map, "player")
            addEntity(Player().create(playerStartPos.cpy().scl(1f / PPM), world, lightWorld, rayHandler, PPM))
            camera.position.apply {
                x = playerStartPos.x
                y = playerStartPos.y
            }
        }
        Box2dWallsFromTiledMapCreator().apply {
            createWalls(world, PPM, map, "walls")
            createWalls(lightWorld, 1f, map, "lightwalls")
            createTriggers(world, PPM, map)
        }
    }

    override fun hide() {
    }

    override fun resize(width: Int, height: Int) {
        camera.apply {
            viewportWidth = fov * Gdx.graphics.width.toFloat() / Gdx.graphics.height.toFloat()
            viewportHeight = fov
            update()
        }
        rayHandler.resizeFBO(width / 4, height / 4)
    }

}