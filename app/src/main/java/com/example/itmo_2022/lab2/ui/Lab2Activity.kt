package com.example.itmo_2022.lab2.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.itmo_2022.R
import com.example.itmo_2022.lab2.MVPContract
import com.example.itmo_2022.lab2.presenter.Lab2Presenter

class Lab2Activity : AppCompatActivity(), MVPContract.IView {

    private lateinit var textViewThread1: TextView
    private lateinit var textViewThread2: TextView
    private lateinit var buttonRun: Button
    private lateinit var buttonStop: Button
    private lateinit var buttonReset: Button
    private lateinit var buttonSpeedUp1: Button
    private lateinit var buttonSpeedDown1: Button
    private lateinit var buttonSpeedUp2: Button
    private lateinit var buttonSpeedDown2: Button
    private lateinit var progressBar1: ProgressBar
    private lateinit var progressBar2: ProgressBar

    private val presenter: MVPContract.IPresenter = Lab2Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab2)

        initViews()
        resetNumbers()

        buttonRun.setOnClickListener { presenter.startThreads() }
        buttonStop.setOnClickListener { presenter.stopThreads() }
        buttonReset.setOnClickListener { presenter.resetThreads() }
        buttonSpeedUp1.setOnClickListener { presenter.speedUp1() }
        buttonSpeedDown1.setOnClickListener { presenter.speedDown1() }
        buttonSpeedUp2.setOnClickListener { presenter.speedUp2() }
        buttonSpeedDown2.setOnClickListener { presenter.speedDown2() }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        presenter.stopThreads()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.stopThreads()
    }

    private fun initViews() {
        textViewThread1 = findViewById(R.id.textView_lab2_thread1)
        textViewThread2 = findViewById(R.id.textView_lab2_thread2)
        buttonRun = findViewById(R.id.button_lab2_run)
        buttonStop = findViewById(R.id.button_lab2_stop)
        buttonReset = findViewById(R.id.button_lab2_reset)
        buttonSpeedUp1 = findViewById(R.id.btn_lab2_speedUp1)
        buttonSpeedDown1 = findViewById(R.id.btn_lab2_speedDown1)
        buttonSpeedUp2 = findViewById(R.id.btn_lab2_speedUp2)
        buttonSpeedDown2 = findViewById(R.id.btn_lab2_speedDown2)
        progressBar1 = findViewById(R.id.lab2_Bar1)
        progressBar2 = findViewById(R.id.lab2_Bar2)
    }

    override fun showToast(str: String) {
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    override fun resetNumbers() {
        textViewThread1.text = "0"
        textViewThread2.text = "0"
    }

    override fun resetProgressBars() {
        progressBar1.visibility = View.VISIBLE
        progressBar2.visibility = View.VISIBLE
        progressBar1.progress = 0
        progressBar2.progress = 0
    }

    override fun updateNumber1(intThread1: Int) {
        runOnUiThread {
            textViewThread1.text = intThread1.toString()
        }
    }

    override fun setProgressBarProgress1(progress: Int) {
        progressBar1.progress = progress
    }

    override fun setProgressBarVisibility1(visibility: Int) {
        progressBar1.visibility = visibility
    }

    override fun updateNumber2(intThread2: Int) {
        runOnUiThread {
            textViewThread2.text = intThread2.toString()
        }
    }

    override fun setProgressBarProgress2(progress: Int) {
        progressBar2.progress = progress
    }

    override fun setProgressBarVisibility2(visibility: Int) {
        progressBar2.visibility = visibility
    }
}