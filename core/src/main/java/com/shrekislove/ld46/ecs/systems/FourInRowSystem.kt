package com.shrekislove.ld46.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils

class FourInRowSystem : EntitySystem() {

    val shapeRenderer = ShapeRenderer()
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
        shapeRenderer.apply {
            begin(ShapeRenderer.ShapeType.Filled)
            for (i in 0 until 7) {
                for (j in 0 until 6) {
                    color = when (board[i][j]) {
                        1 -> Color.YELLOW
                        2 -> Color.RED
                        else -> Color.BLACK
                    }
                    rect(i * 20f, j * 20f, 20f, 20f)
                }
            }
            end()
            begin(ShapeRenderer.ShapeType.Line)
            color = Color.BLUE
            for (i in 0 until 7) {
                for (j in 0 until 6) {
                    rect(i * 20f, j * 20f, 20f, 20f)
                }
            }
            end()
        }
        apply {
            if (Gdx.input.justTouched() && turn || !turn) {
                var stolb = 0
                if (Gdx.input.justTouched() && turn) {
                    stolb = Gdx.input.x / 20
                } else {
                    val a = ArrayList<Int>()
                    for (i in 0 until 7) {
                        if (board[i][5] == 0)
                            a.add(i)
                    }
                    stolb = a[MathUtils.random(0, a.size - 1)]
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