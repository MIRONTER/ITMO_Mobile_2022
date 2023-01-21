package com.example.itmo_2022.lab2

interface MVPContract {

    interface IView {
        fun showToast(str: String)
        fun resetNumbers()
        fun resetProgressBars()

        fun updateNumber1(intThread1: Int)
        fun setProgressBarProgress1(progress: Int)
        fun setProgressBarVisibility1(visibility: Int)
        fun updateNumber2(intThread2: Int)
        fun setProgressBarProgress2(progress: Int)
        fun setProgressBarVisibility2(visibility: Int)
    }

    interface IPresenter {
        fun startThreads()
        fun stopThreads()
        fun resetThreads()

        fun speedUp1()
        fun speedDown1()
        fun speedUp2()
        fun speedDown2()
    }
}