package com.shrekislove.ld46.ecs.components.box2d

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class Box2dTeleportComponent : Component {

    var isTeleport = false
    var newPosition = Vector2()

}