package com.shrekislove.ld46.ecs.components

import com.badlogic.ashley.core.Component

class PlayerAnimationTimer(val frameTime: Float) : Component {
    var timer = 0f
}