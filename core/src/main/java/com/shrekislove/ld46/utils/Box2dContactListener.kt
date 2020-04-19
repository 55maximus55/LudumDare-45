package com.shrekislove.ld46.utils

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.kotcrab.vis.ui.widget.VisLabel
import com.shrekislove.ld46.*
import com.shrekislove.ld46.ecs.components.box2d.Box2dTeleportComponent
import com.shrekislove.ld46.screens.BomjFightScreen
import ktx.math.div

class Box2dContactListener(val map: TiledMap, val PPM: Float, val task: VisLabel) : ContactListener {

    override fun beginContact(contact: Contact?) {
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
                            if (triggerName == "teleport1" && !shelfChecked) {
                                isTeleport = false
                            }
                            newPosition = ObjectFromTiledMapGetter().getPosition(map, triggerName).div(PPM)
                        }
                    }
                    triggerName.contains("wall_hidden") -> {
                        map.layers[triggerName].opacity = 0.3f
                    }
                    triggerName.contains("check_fridge") -> {
                        if (!fridgeChecked) {
                            task.setText("Fridge is empty, go to check shelf to find some money")
                            fridgeChecked = true
                        }
                    }
                    triggerName.contains("check_shelf") -> {
                        if (fridgeChecked) {
                            task.setText("Shelf is empty, you haven't get payment for 3 days\n" +
                                    "You have no money\n" +
                                    "Try to get some food")
                            shelfChecked = true
                            money = 0
                            hungry = 100
                        }
                    }
                    triggerName.contains("pyat_steal") -> {
                        if (!pyat_robbed) {
                            task.setText("You stole the bread, you are dangerous criminal\n" +
                                    "You are wanted")
                            pyat_robbed = true
                            pyat_pizdec = true
                        }
                    }
                }
            }
        }
    }

    override fun endContact(contact: Contact?) {
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
                    triggerName.contains("wall_hidden") -> {
                        map.layers[triggerName].opacity = 1f
                    }
                }
            }
        }
    }

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
                    triggerName.contains("bomj_fight") -> {
                        bomj = true
                    }
                }
            }
        }
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}

}
