package com.alexbirichevskiy.appkodetask.ui

class ActivityPresenter : ActivityContract.Presenter {
    private var view: ActivityContract.View? = null

    override fun attach(view: ActivityContract.View) {
            this.view = view
    }
}