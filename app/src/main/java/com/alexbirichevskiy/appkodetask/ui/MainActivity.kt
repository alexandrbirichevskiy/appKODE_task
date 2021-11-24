package com.alexbirichevskiy.appkodetask.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
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
        binding.shimmerFrameLayout.startShimmer()

        pagerAdapter = ViewPagerAdapter(this)

        usersVM = ViewModelProvider(this, UsersViewModelFactory(this))[UsersViewModel::class.java]
        usersVM.getUsers()

        usersVM.users.observe(this) {
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.removeAllViews()
            binding.viewPager.adapter = pagerAdapter
            binding.viewPager.offscreenPageLimit = 1
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = tabName[position]
            }.attach()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item = menu!!.findItem(R.id.search_item_menu)

        val sortingView = menu!!.findItem(R.id.sort_item_menu)

        sortingView.setOnMenuItemClickListener {
            showBottomMenu()
        }


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

    private fun showBottomMenu(): Boolean {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet)
        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes!!.windowAnimations = R.style.bottom_sheet_animation
        dialog.window?.setGravity(Gravity.BOTTOM)

        val sortAlphaRadioButton = dialog.findViewById<RadioButton>(R.id.sort_alpha_radio_button)
        val sortBirthRadioButton = dialog.findViewById<RadioButton>(R.id.sort_birth_radio_button)

        sortAlphaRadioButton.setOnClickListener {
            usersVM.sortUsersAlpha()
        }

        sortBirthRadioButton.setOnClickListener {
            usersVM.sortUsersBirth()
        }
        
        return true
    }
}