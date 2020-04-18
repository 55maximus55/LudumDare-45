package com.shrekislove.ld46.utils

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold

class Box2dContactListener : ContactListener {

    override fun beginContact(contact: Contact?) {

    }

    override fun endContact(contact: Contact?) {

    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
        val bodyA = contact!!.fixtureA.body
        val bodyB = contact!!.fixtureB.body

        if ((bodyA.userData as Box2dBodyData).tag == "trigger" || (bodyB.userData as Box2dBodyData).tag == "trigger") {
            contact.isEnabled = false
        }
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

    }

}
