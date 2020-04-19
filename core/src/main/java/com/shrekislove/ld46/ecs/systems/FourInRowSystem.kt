package com.shrekislove.ld46.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.shrekislove.ld46.Main
import ktx.app.use

class FourInRowSystem : EntitySystem() {

    val camera = OrthographicCamera()
    val texture = Texture("sprites/bomj gang.png")

    val shapeRenderer = ShapeRenderer()
    val batch = Main.context.inject<Batch>()
    val board = ArrayList<ArrayList<Int>>().apply {
        for (i in 0 until 7) {
            add(ArrayList())
            for (j in 0 until 6) {
                this[i].add(0)
            }
        }
    }
    var turn = true

    var win = false
    var lose = false

    override fun update(deltaTime: Float) {
        if (win) {
            Gdx.app.log("4Row", "Win")
        }
        if (lose) {
            Gdx.app.log("4Row", "Lose")
        }
        // render
        val fov = if (Gdx.graphics.width.toFloat() / Gdx.graphics.height.toFloat() > 240f / 96f)
            96f
        else
            240f / Gdx.graphics.width.toFloat() * Gdx.graphics.height.toFloat()
        var stolb = (Gdx.input.x - (Gdx.graphics.width / 2 - (Gdx.graphics.height / fov * 16 * 3.5f).toInt())) / (Gdx.graphics.height / fov * 16).toInt()
        if ((Gdx.input.x - (Gdx.graphics.width / 2 - (Gdx.graphics.height / fov * 16 * 4f).toInt())) < 0)
            stolb = 0
        if (stolb > 6)
            stolb = 6
        apply {
            camera.apply {
                viewportWidth = fov * Gdx.graphics.width.toFloat() / Gdx.graphics.height.toFloat()
                viewportHeight = fov
                update()
            }
            shapeRenderer.apply {
                projectionMatrix = camera.combined
                begin(ShapeRenderer.ShapeType.Filled)
                for (i in 0 until 7) {
                    for (j in 0 until 6) {
                        color = when (board[i][j]) {
                            1 -> Color.YELLOW
                            2 -> Color.RED
                            else -> Color.BLACK
                        }
                        rect(-56f + i * 16f, -48f + j * 16f, 16f, 16f)
                    }
                }
                end()
            }
            batch.apply {
                projectionMatrix = camera.combined
                use {
                    draw(texture, -texture.width.toFloat() / 2, -texture.height.toFloat() / 2)
                }
            }
            // TODO: Рендер
            shapeRenderer.apply {
                begin(ShapeRenderer.ShapeType.Line)
                apply { // TODO: remove this
                    color = Color.BLUE
                    rect(-56f + stolb * 16f, -48f, 16f, 16f * 6)
                }
                end()
            }
        }

        // control and update
        apply {
            if (Gdx.input.justTouched() && turn || !turn) {
                if (Gdx.input.justTouched() && turn) {
                } else {
                    val a = ArrayList<Int>()
                    for (i in 0 until 7) {
                        if (board[i][5] == 0)
                            a.add(i)
                    }
                    a[MathUtils.random(0, a.size - 1)]
                }
                var wst = false
                if (stolb < 7) {
                    for (j in 0 until 6) { // i'm an idiot
                        if (board[stolb][j] == 0) {
                            board[stolb][j] = if (turn) 1 else 2
                            turn = !turn
                            wst = true
                            break
                        }
                    }
                }
                if (wst) {
                    for (j in 0 until 6) {
                        var s = ""
                        for (i in 0 until 7) {
                            s += board[i][j].toString()
                        }
                        if (s.contains("1111")) {
                            win = true
                        } else if (s.contains("2222")) {
                            lose = true
                        }
                    }
                    for (i in 0 until 7) {
                        var s = ""
                        for (j in 0 until 6) {
                            s += board[i][j].toString()
                        }
                        if (s.contains("1111")) {
                            win = true
                        } else if (s.contains("2222")) {
                            lose = true
                        }
                    }
                    for (x in -5 until 6) {
                        var s = ""
                        for (i in 0 until 6) {
                            if (i + x in 0..6)
                                s += board[x + i][5 - i].toString()
                        }
                        if (s.contains("1111")) {
                            win = true
                        } else if (s.contains("2222")) {
                            lose = true
                        }
                    }
                }
            }
        }
    }

}