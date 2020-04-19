package com.shrekislove.ld46.ecs.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import com.shrekislove.ld46.ecs.components.PlayerAnimationTimer
import com.shrekislove.ld46.ecs.components.SpriteComponent
import com.shrekislove.ld46.ecs.components.SukaComponent
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent

class PizdimVorogivSystem(val PPM: Float) : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private val sukaMapper = ComponentMapper.getFor(SukaComponent::class.java)
    private val spriteMapper = ComponentMapper.getFor(SpriteComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(SukaComponent::class.java, SpriteComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val sprite = spriteMapper[i].sprite
            val suka = sukaMapper[i]

            if (suka.n > 0) {
                suka.timer -= deltaTime
                if (suka.timer < 0) {
                    suka.timer = 0.3f
                    suka.pos.add(Vector2(2f, 0f).setAngle(suka.angle))
                    suka.n--
                }

                sprite.apply {
                    setPosition(suka.pos.x * PPM - sprite.width / 2, suka.pos.y * PPM - sprite.height / 2)
                    val s = when(suka.timer) {
                        in 0.2f..0.3f -> 0
                        in 0.1f..0.2f -> 1
                        in 0.0f..0.1f -> 2
                        else -> 0
                    }
                    setRegion(s * 32, 0, 32, 32)
                    setSize(32f, 32f)
                }

                if (suka.n <= 0) {
                    sprite.apply {
                        setPosition(-100f, -100f)
                    }
                }
            }
        }
    }

}