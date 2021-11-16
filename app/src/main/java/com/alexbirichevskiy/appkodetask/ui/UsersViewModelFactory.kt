package com.alexbirichevskiy.appkodetask.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexbirichevskiy.appkodetask.MyApplication
import com.alexbirichevskiy.appkodetask.repos.UsersItemsRepo

class UsersViewModelFactory(private val context: Context?):ViewModelProvider.Factory {
    private val app by lazy { context?.applicationContext as MyApplication }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsersViewModel(app.usersItemsRepo) as T
    }
}