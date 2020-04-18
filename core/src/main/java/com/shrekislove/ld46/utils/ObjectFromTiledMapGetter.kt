package com.shrekislove.ld46.utils

import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2

class ObjectFromTiledMapGetter {

    fun getPosition(map: TiledMap, objectName: String): Vector2 {
        for (i in map.layers[objectName].objects.getByType(RectangleMapObject::class.java)) {
            val rect = i.rectangle
            return Vector2(rect.getX(), rect.getY())
        }
        return Vector2()
    }

}