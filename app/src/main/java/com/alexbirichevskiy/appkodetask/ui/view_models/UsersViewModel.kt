package com.alexbirichevskiy.appkodetask.ui.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity
import com.alexbirichevskiy.appkodetask.repos.UsersItemsRepo

class UsersViewModel(private val usersItems: UsersItemsRepo) : ViewModel() {
    val searchText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val _users = MutableLiveData<List<UserItemEntity>>()
    val users: LiveData<List<UserItemEntity>>
        get() = _users

    fun sortUsersBirth(){
        _users.value = users.value?.sortedBy {
            it.birthday
        }
    }

    fun sortUsersAlpha(){
        _users.value = users.value?.sortedBy {
            it.firstName
        }
    }

    fun getUsers() {
        usersItems.getUsersItems({
            _users.value = it
        }, { throwable ->
            Log.d("@@@", "Error! ${throwable.message}")
        })
    }
}