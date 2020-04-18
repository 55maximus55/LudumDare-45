package com.shrekislove.ld46.utils

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.shrekislove.ld46.ecs.components.box2d.Box2dTeleportComponent
import ktx.math.div

class Box2dContactListener(val map: TiledMap, val PPM: Float) : ContactListener {

    override fun beginContact(contact: Contact?) {}

    override fun endContact(contact: Contact?) {}

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
        val bodyA = contact!!.fixtureA.body
        val bodyB = contact!!.fixtureB.body

        if (!(bodyA.userData as Box2dBodyData).collision || !(bodyB.userData as Box2dBodyData).collision) {
            contact.isEnabled = false
        }

        if ((bodyA.userData as Box2dBodyData).tag == "trigger" || (bodyB.userData as Box2dBodyData).tag == "trigger") {
            if ((bodyA.userData as Box2dBodyData).tag == "player" || (bodyB.userData as Box2dBodyData).tag == "player") {
                val triggerName: String
                val entityPlayer: Entity = if ((bodyA.userData as Box2dBodyData).tag == "player") {
                    triggerName = (bodyB.userData as Box2dBodyData).data as String
                    (bodyA.userData as Box2dBodyData).entity
                } else {
                    triggerName = (bodyA.userData as Box2dBodyData).data as String
                    (bodyB.userData as Box2dBodyData).entity
                }

                when {
                    triggerName.contains("teleport") -> {
                        entityPlayer.getComponent(Box2dTeleportComponent::class.java).apply {
                            isTeleport = true
                            newPosition = ObjectFromTiledMapGetter().getPosition(map, triggerName).div(PPM)
                        }
                    }
                }
            }
        }
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}

}
