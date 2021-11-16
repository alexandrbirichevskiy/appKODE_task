package com.alexbirichevskiy.appkodetask.repos.impls

import android.os.Handler
import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity
import com.alexbirichevskiy.appkodetask.repos.UsersItemsRepo
import com.alexbirichevskiy.appkodetask.repos.impls.retrofit_web.WebUsersItemsRepoImpl
import com.alexbirichevskiy.appkodetask.repos.impls.room.UsersItemsDao
import com.alexbirichevskiy.appkodetask.repos.impls.room.RoomKodeUsersItemsRepoImpl
import retrofit2.Retrofit

class CombinedUsersItemsRepoImpl(
    retrofit: Retrofit,
    dao: UsersItemsDao,
    handler: Handler
) : UsersItemsRepo {
    private val webRepo = WebUsersItemsRepoImpl(retrofit)
    private val cacheRepo = RoomKodeUsersItemsRepoImpl(dao, handler)

    override fun getUsersItems(
        onSuccess: (List<UserItemEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {

        cacheRepo.getUsersItems({
            if (it.isNotEmpty()) {
                onSuccess(it)
            }
        }, onError)

        webRepo.getUsersItems({
            cacheRepo.saveCache(it)
            onSuccess(it)
        }, onError)
    }
}
