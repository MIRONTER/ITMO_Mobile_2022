package com.example.itmo_2022.lab2.presenter

import android.view.View
import com.example.itmo_2022.lab2.MVPContract
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

class Lab2Presenter(private val view: MVPContract.IView): MVPContract.IPresenter {

    private var intThread1 = 0
    private var intThread2 = 0
    private var runThreads = AtomicBoolean(false)
    @Volatile
    private var sleep1 = 600L
    @Volatile
    private var sleep2 = 400L
    private val step = 50

    override fun startThreads() {
        if (!runThreads.get()) {
            runThreads.set(true)
            view.resetProgressBars()
            thread {
                do {
                    var progress = 0
                    intThread1++
                    view.updateNumber1(intThread1)
                    for (i in 1..step) {
                        progress += 100 / step
                        view.setProgressBarProgress1(progress)
                        Thread.sleep(sleep1 / step)
                    }
                } while (runThreads.get())
                view.setProgressBarVisibility1(View.INVISIBLE)
            }
            thread {
                do {
                    var progress = 0
                    intThread2++
                    view.updateNumber2(intThread2)
                    for (i in 1..step) {
                        progress += 100 / step
                        view.setProgressBarProgress2(progress)
                        Thread.sleep(sleep2 / step)
                    }
                } while (runThreads.get())
                view.setProgressBarVisibility2(View.INVISIBLE)
            }

        }
    }

    override fun stopThreads() {
        runThreads.set(false)
    }

    override fun resetThreads() {
        stopThreads()
        intThread1 = 0
        intThread2 = 0
        view.resetNumbers()
    }

    override fun speedUp1() {
        if (sleep1 > 100) { sleep1 -= 100 }
        view.showToast(sleep1.toString())
    }

    override fun speedDown1() {
        sleep1 += 100
        view.showToast(sleep1.toString())
    }

    override fun speedUp2() {
        if (sleep2 > 100) { sleep2 -= 100 }
        view.showToast(sleep2.toString())
    }

    override fun speedDown2() {
        sleep2 += 100
        view.showToast(sleep2.toString())
    }

}