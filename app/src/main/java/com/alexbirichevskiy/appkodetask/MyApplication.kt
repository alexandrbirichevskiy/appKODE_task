package com.alexbirichevskiy.appkodetask

import android.app.Application
import android.os.Handler
import androidx.room.Room
import com.alexbirichevskiy.appkodetask.repos.impls.CombinedUsersItemsRepoImpl
import com.alexbirichevskiy.appkodetask.repos.impls.room.UsersDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://stoplight.io/"

class MyApplication : Application() {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            UsersDatabase::class.java, "users-items-db"
        ).build()
    }

    val usersItemsRepo by lazy {
        CombinedUsersItemsRepoImpl(
            retrofit,
            db.kodeUsersItemsDao(),
            Handler(mainLooper)
        )
    }
}