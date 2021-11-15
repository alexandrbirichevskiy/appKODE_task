package com.alexbirichevskiy.appkodetask.domain.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_item")
data class UserItemEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "avatarUrl") val avatarUrl: String?,
    @ColumnInfo(name = "firstName") val firstName: String?,
    @ColumnInfo(name = "lastName") val lastName: String?,
    @ColumnInfo(name = "userTag") val userTag: String?,
    @ColumnInfo(name = "department") val department: String?,
    @ColumnInfo(name = "position") val position: String?,
    @ColumnInfo(name = "birthday") val birthday: String?,
    @ColumnInfo(name = "phone") val phone: String?
) : Parcelable {
    val userTagLower: String?
        get() = userTag?.lowercase(Locale.getDefault())

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(avatarUrl)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(userTag)
        parcel.writeString(department)
        parcel.writeString(position)
        parcel.writeString(birthday)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserItemEntity> {
        override fun createFromParcel(parcel: Parcel): UserItemEntity {
            return UserItemEntity(parcel)
        }

        override fun newArray(size: Int): Array<UserItemEntity?> {
            return arrayOfNulls(size)
        }
    }
}


