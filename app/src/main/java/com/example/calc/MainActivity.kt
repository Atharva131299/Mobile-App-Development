package com.example.calc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var currentNumber = ""
    private var operator = ""
    private var firstNumber = ""
    private var secondNumber = ""
    private var isNewOp = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.tvDisplay)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnDot, R.id.btnClear, R.id.btnSign, R.id.btnPercent,
            R.id.btnDivide, R.id.btnMultiply, R.id.btnMinus, R.id.btnPlus, R.id.btnEqual
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener {
                onButtonClick(it as Button)
            }
        }
    }

    private fun onButtonClick(button: Button) {
        when (button.id) {
            R.id.btnClear -> clear()
            R.id.btnSign -> toggleSign()
            R.id.btnPercent -> percentage()
            R.id.btnDivide, R.id.btnMultiply, R.id.btnMinus, R.id.btnPlus -> operator(button.text.toString())
            R.id.btnEqual -> calculate()
            else -> number(button.text.toString())
        }
    }

    private fun clear() {
        currentNumber = ""
        operator = ""
        firstNumber = ""
        secondNumber = ""
        isNewOp = true
        display.text = "0"
    }

    private fun toggleSign() {
        currentNumber = if (currentNumber.startsWith("-")) {
            currentNumber.substring(1)
        } else {
            "-$currentNumber"
        }
        display.text = currentNumber
    }

    private fun percentage() {
        if (currentNumber.isNotEmpty()) {
            currentNumber = (currentNumber.toDouble() / 100).toString()
            display.text = currentNumber
        }
    }

    private fun operator(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber
            currentNumber = ""
            operator = op
            isNewOp = true
        }
    }

    private fun calculate() {
        if (firstNumber.isNotEmpty() && currentNumber.isNotEmpty()) {
            secondNumber = currentNumber
            val result = when (operator) {
                "+" -> firstNumber.toDouble() + secondNumber.toDouble()
                "-" -> firstNumber.toDouble() - secondNumber.toDouble()
                "X" -> firstNumber.toDouble() * secondNumber.toDouble()
                "/" -> firstNumber.toDouble() / secondNumber.toDouble()
                else -> 0.0
            }
            display.text = result.toString()
            currentNumber = result.toString()
            firstNumber = ""
            secondNumber = ""
            operator = ""
            isNewOp = true
        }
    }

    private fun number(num: String) {
        if (isNewOp) {
            currentNumber = ""
            isNewOp = false
        }
        currentNumber += num
        display.text = currentNumber
    }
}
