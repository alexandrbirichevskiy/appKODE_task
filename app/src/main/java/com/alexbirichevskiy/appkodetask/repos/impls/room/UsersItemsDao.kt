package com.alexbirichevskiy.appkodetask.repos.impls.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity

@Dao
interface UsersItemsDao {
    @Query("SELECT * FROM user_item")
    fun getAll(): List<UserItemEntity>

    @Query("DELETE FROM user_item")
    fun deleteAll()

    @Insert
    fun insertAll(vararg users: UserItemEntity)

    @Delete
    fun delete(user: UserItemEntity)
}