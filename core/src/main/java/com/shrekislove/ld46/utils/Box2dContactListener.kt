package com.shrekislove.ld46.utils

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.shrekislove.ld46.ecs.components.box2d.Box2dBodyComponent

class Box2dContactListener : ContactListener {

    override fun beginContact(contact: Contact?) {

    }

    override fun endContact(contact: Contact?) {

    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
        val bodyA = contact!!.fixtureA.body
        val bodyB = contact!!.fixtureB.body

        if (!(bodyA.userData as Box2dBodyData).collision || !(bodyB.userData as Box2dBodyData).collision) {
            contact.isEnabled = false
        }
        if ((bodyA.userData as Box2dBodyData).tag == "trigger" || (bodyB.userData as Box2dBodyData).tag == "trigger") {
            if ((bodyA.userData as Box2dBodyData).tag == "player" || (bodyB.userData as Box2dBodyData).tag == "player") {
                val entityPlayer: Entity = if ((bodyA.userData as Box2dBodyData).tag == "player") {
                    (bodyA.userData as Box2dBodyData).entity
                } else {
                    (bodyB.userData as Box2dBodyData).entity
                }
                entityPlayer.getComponent(Box2dBodyComponent::class.java).body.setTransform(0f, 0f, 0f)
            }
        }
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

    }

}
