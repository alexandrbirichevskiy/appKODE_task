package com.alexbirichevskiy.appkodetask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import com.alexbirichevskiy.appkodetask.Consts.tabName
import com.alexbirichevskiy.appkodetask.R
import com.alexbirichevskiy.appkodetask.databinding.ActivityMainBinding
import com.alexbirichevskiy.appkodetask.ui.adapters.ViewPagerAdapter
import com.alexbirichevskiy.appkodetask.ui.view_models.UsersViewModel
import com.alexbirichevskiy.appkodetask.ui.view_models.UsersViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var usersVM: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pagerAdapter = ViewPagerAdapter(this)


        usersVM = ViewModelProvider(this, UsersViewModelFactory(this))[UsersViewModel::class.java]
        usersVM.getUsers()
        Log.d("@@@", "Create Activity")

        Thread{
            Thread.sleep(5_000)
            Log.d("@@@", "users " + usersVM.users.value.toString())
            runOnUiThread{
                binding.viewPager.adapter = pagerAdapter
                binding.viewPager.offscreenPageLimit = 1
                TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                    tab.text = tabName[position]
                }.attach()
            }
        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item = menu!!.findItem(R.id.search)
        val searchView = item.actionView as android.widget.SearchView
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                usersVM.searchText.value = newText
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}