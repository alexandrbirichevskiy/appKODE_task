package com.alexbirichevskiy.appkodetask.repos.impls.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity

@Database(
    version = 1,
    entities = [UserItemEntity::class]
)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun kodeUsersItemsDao(): UsersItemsDao

}
