package com.alexbirichevskiy.appkodetask.ui

import android.view.View
import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity

interface RecyclerViewClickListener {
    fun onRecyclerViewClickListener(view: View, user: UserItemEntity)
}