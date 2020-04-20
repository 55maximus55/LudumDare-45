package com.shrekislove.ld46.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.physics.box2d.World
import com.shrekislove.ld46.pyat_pizdec
import com.shrekislove.ld46.utils.Box2dWallsFromTiledMapCreator

class PizdecSystem(val world: World, val PPM: Float, val map: TiledMap) : EntitySystem() {

    override fun update(deltaTime: Float) {
        if (pyat_pizdec) {
            Box2dWallsFromTiledMapCreator().createWalls(world, PPM, map, "walls_aaa")
            pyat_pizdec = false
        }
    }

}