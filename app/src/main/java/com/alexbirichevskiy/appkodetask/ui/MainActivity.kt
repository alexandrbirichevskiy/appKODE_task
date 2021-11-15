package com.alexbirichevskiy.appkodetask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexbirichevskiy.appkodetask.Consts.tabName
import com.alexbirichevskiy.appkodetask.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), ActivityContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: ActivityContract.Presenter
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ActivityPresenter()
        presenter.attach(this)

        adapter = RecyclerViewAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabName[position]
        }.attach()
    }

    override fun showResult() {
        TODO("Not yet implemented")
    }
}