package com.example.itmo_2022.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.itmo_2022.R

class Lab1DetailActivity : AppCompatActivity() {

    private val dataNatural = "DATA_NAT"
    private val dataFibonacci = "DATA_FIB"
    private val dataFibonacciTemp = "DATA_FIB0"
    private val dataCollatz = "DATA_COL"

    private var natural = 0
    private var fibonacci = 0
    private var fibonacciTemp0 = 0
    private var fibonacciTemp1 = 0
    private var collatz = 23

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab1_detail)

        savedInstanceState?.let {
            natural = it.getInt(dataNatural)
            fibonacci = it.getInt(dataFibonacci)
            fibonacciTemp0 = it.getInt(dataFibonacciTemp)
            collatz = it.getInt(dataCollatz)
        }

        val lab1DetailLabelNat = findViewById<TextView>(R.id.lab1DetailLabelNat)
        lab1DetailLabelNat.text = natural.toString()
        val lab1DetailBtnNat = findViewById<Button>(R.id.lab1DetailBtnNat)
        lab1DetailBtnNat.setOnClickListener {
            natural++
            lab1DetailLabelNat.text = natural.toString()
        }

        val lab1DetailLabelFib = findViewById<TextView>(R.id.lab1DetailLabelFib)
        lab1DetailLabelFib.text = fibonacci.toString()
        val lab1DetailBtnFib = findViewById<Button>(R.id.lab1DetailBtnFib)
        lab1DetailBtnFib.setOnClickListener {
            if (fibonacci != 0) {
                fibonacciTemp1 = fibonacci
                fibonacci += fibonacciTemp0
                fibonacciTemp0 = fibonacciTemp1
            } else {
                fibonacci = 1
            }
            lab1DetailLabelFib.text = fibonacci.toString()
        }

        val lab1DetailLabelCol = findViewById<TextView>(R.id.lab1DetailLabelCol)
        lab1DetailLabelCol.text = collatz.toString()
        val lab1DetailBtnCol = findViewById<Button>(R.id.lab1DetailBtnCol)
        lab1DetailBtnCol.setOnClickListener {
            if ((collatz % 2) == 0) {
                collatz /= 2
            } else {
                collatz = collatz * 3 + 1
            }
            lab1DetailLabelCol.text = collatz.toString()
        }

        val lab1DetailTitle = findViewById<TextView>(R.id.lab1DetailTitle)
        val lab1DetailDescription = findViewById<TextView>(R.id.lab1DetailDescription)
        val lab1DetailImage = findViewById<ImageView>(R.id.lab1DetailImage)
        val lab1DetailIcon = findViewById<ImageView>(R.id.lab1DetailIcon)

        lab1DetailTitle.text = intent.getSerializableExtra("title").toString()
        lab1DetailDescription.text = intent.getSerializableExtra("description").toString()
        lab1DetailImage.setImageResource(intent.getSerializableExtra("pics") as Int)
        lab1DetailIcon.setImageResource(intent.getSerializableExtra("icon") as Int)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(dataNatural, natural)
        outState.putInt(dataFibonacci, fibonacci)
        outState.putInt(dataFibonacciTemp, fibonacciTemp0)
        outState.putInt(dataCollatz, collatz)
        super.onSaveInstanceState(outState)
    }
}