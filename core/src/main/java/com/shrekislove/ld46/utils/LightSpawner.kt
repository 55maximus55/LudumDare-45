package com.shrekislove.ld46.utils

import box2dLight.ConeLight
import box2dLight.PointLight
import box2dLight.RayHandler
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2

class LightSpawner {

    fun createLights(map: TiledMap, rayHandler: RayHandler, PPM: Float) {
        for (i in map.layers["light_point"].objects.getByType(RectangleMapObject::class.java)) {
            val rect = i.rectangle
            val pos = Vector2(rect.getX(), rect.getY())
            val dist = i.name.toFloat()

            PointLight(rayHandler, 100, Color.BLACK, dist * PPM, pos.x, pos.y)
        }
        for (i in map.layers["light_cone"].objects.getByType(RectangleMapObject::class.java)) {
            val rect = i.rectangle
            val pos = Vector2(rect.getX(), rect.getY())
            val dist = i.name.toFloat()

            ConeLight(rayHandler, 100, Color.BLACK, dist * PPM, pos.x, pos.y, -90f, 20f)
        }
    }

}