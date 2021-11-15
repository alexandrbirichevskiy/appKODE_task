package com.alexbirichevskiy.appkodetask.ui

interface ActivityContract {
    interface View {
        fun showResult()

    }

    interface Presenter {
        fun attach(view: View)
    }
}