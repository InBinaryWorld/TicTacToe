package com.example.tictactoe


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {


    private var fields1 = ArrayList<Int>()
    private var fields2 = ArrayList<Int>()
    private var intBtn = ArrayMap<Int, Button>()
    private var btnInt = ArrayMap<Button, Int>()

    private var enabledPlayer = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intBtn[1] = btn1
        intBtn[2] = btn2
        intBtn[3] = btn3
        intBtn[4] = btn4
        intBtn[5] = btn5
        intBtn[6] = btn6
        intBtn[7] = btn7
        intBtn[8] = btn8
        intBtn[9] = btn9
        intBtn[10] = btn10
        intBtn[11] = btn11
        intBtn[12] = btn12
        intBtn[13] = btn13
        intBtn[14] = btn14
        intBtn[15] = btn15
        intBtn[16] = btn16
        intBtn[17] = btn17
        intBtn[18] = btn18
        intBtn[19] = btn19
        intBtn[20] = btn20
        intBtn[21] = btn21
        intBtn[22] = btn22
        intBtn[23] = btn23
        intBtn[24] = btn24
        intBtn[25] = btn25

        intBtn.forEach { t, u -> btnInt[u] = t }
    }

    fun onAction(view: View) {
        val btn = view as Button
        makeMove(btn)
    }

    private fun makeMove(btn: Button) {
        if (enabledPlayer == 1) {
            btn.text = "X"
            textView2.text = getString(R.string.player_2)
            btn.isEnabled = false
            fields1.add(btnInt[btn]!!)
            if (checkEnd()) return
            enabledPlayer = 2
            startBot()
        } else {
            btn.text = "O"
            textView2.text = resources.getString(R.string.player_1)
            btn.isEnabled = false
            fields2.add(btnInt[btn]!!)
            if (checkEnd()) return
            enabledPlayer = 1
        }
    }

    private fun disableBtn() {
        for (btn in btnInt.keys) {
            btn.isEnabled = false
        }
    }

    private fun checkEnd(): Boolean {
        if (inLine()) {
            if (enabledPlayer == 1) {
                textView2.text = resources.getString(R.string.pl1win)
            } else {
                textView2.text = getString(R.string.pl2win)
            }
            disableBtn()
            return true
        } else if (fields1.size + fields2.size == 25) {
            textView2.text = getString(R.string.draw)
            disableBtn()
            return true
        }
        return false
    }

    private fun inLine(): Boolean {
        val fields = if (enabledPlayer == 1) {
            fields1
        } else {
            fields2
        }


        var count = 0
        for (i in 1..25) {
            if (fields.contains(i))
                count++
            if (count == 5)
                return true
            if (i % 5 == 0)
                count = 0
        }

        for (i in 1..5) {
            for (j in 0..4) {
                if (fields.contains(i + j * 5))
                    count++
                if (count == 5)
                    return true
                if (j == 4)
                    count = 0
            }
        }

        for (i in 0..4) {
            if (fields.contains(1 + i * 6))
                count++
            if (count == 5)
                return true
        }
        count = 0
        for (i in 0..4) {
            if (fields.contains(5 + i * 4))
                count++
            if (count == 5)
                return true
        }
        return false
    }

    fun newGame(view: View) {
        fields2.clear()
        fields1.clear()

        for (btn in btnInt.keys) {
            btn.isEnabled = true
            btn.text = ""
        }
        enabledPlayer = 1
        textView2.text = getText(R.string.player_1)
    }

    private fun startBot() {
        val empty = ArrayList<Int>()
        val sort = ArrayList<Int>()
        var flag = false

        for (i in 0..24) {
            sort.add(i, 0)
            empty.add(i, 0)
        }

        //Bot algorithm ,written for fast
        for (id in 1..25) {
            if (fields1.contains(id) || fields2.contains(id)) {
                empty[id - 1] = -20
            }
        }
        var x: Int
        var y: Int
        for (i in fields1) {
            x = (i - 1) % 5
            y = (i - 1) / 5
            for (j in y * 5 until (y + 1) * 5 - 1) {
                if (fields2.contains(j + 1))
                    flag = true
            }
            if (!flag)
                for (j in y * 5 until (y + 1) * 5 - 1)
                    empty[j] += 1

            flag = false
            for (j in 0..4)
                if (fields2.contains(j * 5 + x + 1))
                    flag = true
            if (!flag)
                for (j in 0..4)
                    empty[j * 5 + x] += 1

            flag = false
            if (x == y) {
                for (j in 0..4) {
                    if (fields2.contains(j * 6 + 1))
                        flag = true
                }
                if (!flag)
                    for (j in 0..4)
                        empty[j * 6] += 1
            }


            flag = false
            if (x + y == 4) {
                for (j in 0..4) {
                    if (fields2.contains(5 + j * 4))
                        flag = true
                }
                if (!flag)
                    for (j in 0..4)
                        empty[4 + j * 4] += 1
            }
        }

        for (i in 0..24) {
            if (empty[i] == empty.max())
                sort[i] = 100
        }

        flag = false
        for (i in fields2) {
            x = (i - 1) % 5
            y = (i - 1) / 5
            for (j in y * 5 until (y + 1) * 5 - 1) {
                sort[j] += 1
                if (fields1.contains(j + 1))
                    flag = true
            }
            if (!flag)
                for (j in y * 5 until (y + 1) * 5 - 1)
                    sort[j] += 20



            flag = false
            for (j in 0..4) {
                sort[j * 5 + x] += 1
                if (fields1.contains(j * 5 + x + 1))
                    flag = true
            }
            if (!flag)
                for (j in 0..4)
                    sort[j * 5 + x] += 20


            flag = false
            if (x == y) {
                for (j in 0..4) {
                    sort[j * 6] += 1
                    if (fields1.contains(j * 6 + 1))
                        flag = true
                }
                if (!flag)
                    for (j in 0..4)
                        sort[j * 6] += 20
            }

            flag = false
            if (x + y == 4) {
                for (j in 0..4) {
                    sort[4 + j * 4] += 1
                    if (fields1.contains(5 + j * 4))
                        flag = true
                }
                if (!flag)
                    for (j in 0..4)
                        sort[4 + j * 4] += 20
            }
        }

        val id = sort.indexOf(sort.max()) + 1
        makeMove(intBtn[id]!!)

    }
}
