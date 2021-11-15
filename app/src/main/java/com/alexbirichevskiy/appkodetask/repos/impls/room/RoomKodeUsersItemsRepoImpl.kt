package com.alexbirichevskiy.appkodetask.repos.impls.room

import android.os.Handler
import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity
import com.alexbirichevskiy.appkodetask.repos.UsersItemsRepo

class RoomKodeUsersItemsRepoImpl(
    private val dao: UsersItemsDao,
    private val handler: Handler
) : UsersItemsRepo {

    fun saveCache(cache: List<UserItemEntity>) {
        Thread {
            dao.deleteAll()
            dao.insertAll(*cache.toTypedArray())
        }.start()
    }

    override fun getUsersItems(
        onSuccess: (List<UserItemEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Thread {
            val usersRepo = dao.getAll()
            handler.post { onSuccess(usersRepo) }
        }
    }
}