package com.shrekislove.ld46.ecs.systems

import box2dLight.RayHandler
import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.shrekislove.ld46.Main
import com.shrekislove.ld46.ecs.components.SpriteComponent
import ktx.app.use

class RenderSystem(val camera: OrthographicCamera, val map: TiledMap, val world: World, val lightWorld: World, val rayHandler: RayHandler, val PPM: Float) : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val spriteMapper = ComponentMapper.getFor(SpriteComponent::class.java)

    val mapRenderer = OrthogonalTiledMapRenderer(map)
    val box2DDebugRenderer = Box2DDebugRenderer()
    val batch = Main.context.inject<Batch>()

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(SpriteComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        mapRenderer.apply {
            setView(camera)
            render()
        }
        // draw sprites
        apply {
            batch.apply {
                projectionMatrix = camera.combined
                use {
                    for (i in entities) {
                        val sprite = spriteMapper[i].sprite
                        sprite.draw(this)
                    }
                }
            }
        }
        apply {
            rayHandler.apply {
                setCombinedMatrix(camera)
                updateAndRender()
            }
        }
        // box2d debug
        apply {
            camera.apply {
                zoom /= PPM
                position.x /= PPM
                position.y /= PPM
                update()
                box2DDebugRenderer.render(world, camera.combined)
                zoom *= PPM
                position.x *= PPM
                position.y *= PPM
                update()
            }
        }
        // box2d light debug
        apply {
            camera.apply {
                update()
                box2DDebugRenderer.render(lightWorld, camera.combined)
            }
        }
    }

}