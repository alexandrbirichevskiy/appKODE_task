package com.alexbirichevskiy.appkodetask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexbirichevskiy.appkodetask.Consts.ARG_TAG
import com.alexbirichevskiy.appkodetask.Consts.tabValue

class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return tabValue.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = UsersFragment()
        fragment.arguments = Bundle().apply {
            putString(ARG_TAG, tabValue[position])
        }
        return fragment
    }
}