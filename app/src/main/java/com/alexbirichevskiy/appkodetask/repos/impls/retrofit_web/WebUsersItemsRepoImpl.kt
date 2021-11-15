package com.alexbirichevskiy.appkodetask.repos.impls.retrofit_web

import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity
import com.alexbirichevskiy.appkodetask.domain.entities.UsersItemsEntity
import com.alexbirichevskiy.appkodetask.repos.UsersItemsRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class WebUsersItemsRepoImpl(private val retrofit: Retrofit) : UsersItemsRepo {
    private val service: UsersItemsService by lazy { retrofit.create(UsersItemsService::class.java) }

    override fun getUsersItems(
        onSuccess: (List<UserItemEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.getListUsers().enqueue(object : Callback<UsersItemsEntity> {
            override fun onResponse(
                call: Call<UsersItemsEntity>,
                response: Response<UsersItemsEntity>
            ) {
                if (response.isSuccessful) {
                    response.body()?.items?.let {
                        onSuccess(it)
                    }
                } else if (response.code() == 500) {
                    onError(Throwable("Какой-то сверхразум все сломал"))
                } else {
                    onError(Throwable("Api error ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<UsersItemsEntity>, t: Throwable) {
                onError(t)
            }

        })
    }
}