package com.alexbirichevskiy.appkodetask.repos.impls.retrofit_web

import com.alexbirichevskiy.appkodetask.domain.entities.UsersItemsEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface UsersItemsService {
    @Headers(
        "Content-Type: application/json",
        "Prefer: code=200, example=success"
    )
    @GET("mocks/kode-education/trainee-test/25143926/users")
    fun getListUsers(): Call<UsersItemsEntity>
}