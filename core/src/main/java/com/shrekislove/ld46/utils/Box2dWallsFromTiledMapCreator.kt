package com.shrekislove.ld46.utils

import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.box2d.body

class Box2dWallsFromTiledMapCreator {

    fun createWalls(world: World, PPM: Float, map: TiledMap) {
        for (i in map.layers["walls"].objects.getByType(RectangleMapObject::class.java)) {
            val rect = i.rectangle

            world.body {
                type = BodyDef.BodyType.StaticBody
                position.set(
                        (rect.getX() + rect.getWidth() / 2) / PPM,
                        (rect.getY() + rect.getHeight() / 2) / PPM
                )
                box(width = rect.getWidth() / PPM, height = rect.getHeight() / PPM) {

                }
                userData = "wall"
            }
        }
    }

}