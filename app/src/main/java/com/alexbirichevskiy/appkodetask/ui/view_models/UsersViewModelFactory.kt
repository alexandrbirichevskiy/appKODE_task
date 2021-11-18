package com.alexbirichevskiy.appkodetask.ui.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexbirichevskiy.appkodetask.MyApplication
import com.alexbirichevskiy.appkodetask.ui.MainActivity

class UsersViewModelFactory(private val context: Context?):ViewModelProvider.Factory {
    private val app by lazy { context?.applicationContext as MyApplication }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsersViewModel(app.usersItemsRepo) as T
    }
}