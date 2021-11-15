package com.alexbirichevskiy.appkodetask.repos

import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity


interface UsersItemsRepo {
    fun getUsersItems(
        onSuccess: (List<UserItemEntity>) -> Unit,
        onError: (Throwable) -> Unit
    )
}