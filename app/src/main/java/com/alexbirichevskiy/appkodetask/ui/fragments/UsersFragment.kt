package com.alexbirichevskiy.appkodetask.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexbirichevskiy.appkodetask.Consts.ARG_TAG
import com.alexbirichevskiy.appkodetask.MyApplication
import com.alexbirichevskiy.appkodetask.databinding.FragmentUsersBinding
import com.alexbirichevskiy.appkodetask.ui.MainActivity
import com.alexbirichevskiy.appkodetask.ui.view_models.UsersViewModel
import com.alexbirichevskiy.appkodetask.ui.view_models.UsersViewModelFactory
import com.alexbirichevskiy.appkodetask.ui.adapters.RecyclerViewAdapter

class UsersFragment : Fragment() {
    private val usersViewModel: UsersViewModel by activityViewModels()

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private var department: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf {
            it.containsKey(ARG_TAG)?.apply {
                department = requireArguments().getString(ARG_TAG)
            }
        }

        binding.swipeRefrashContainer.setOnRefreshListener {
            binding.swipeRefrashContainer.isRefreshing = true
            onRefresh()
            binding.swipeRefrashContainer.isRefreshing = false
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        usersViewModel.getUsers()

        usersViewModel.searchText.observe(viewLifecycleOwner, { msg ->
            usersViewModel.users.observe(viewLifecycleOwner, Observer { users ->
                binding.recyclerView.also { recyclerView ->
                    val adapter = RecyclerViewAdapter(users.toMutableList(), department, this)
                    adapter.filter.filter(msg)
                    recyclerView.adapter = adapter
                }
            })
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onRefresh() {
        usersViewModel.getUsers()
        usersViewModel.users.observe(viewLifecycleOwner, Observer { users ->
            binding.recyclerView.also { recyclerView ->
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.setHasFixedSize(true)
                val adapter = RecyclerViewAdapter(users.toMutableList(), department, this)
                recyclerView.adapter = adapter
            }
        })
    }
}