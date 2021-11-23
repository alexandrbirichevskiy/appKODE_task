package com.alexbirichevskiy.appkodetask.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexbirichevskiy.appkodetask.Consts.ARG_TAG
import com.alexbirichevskiy.appkodetask.databinding.FragmentUsersBinding
import com.alexbirichevskiy.appkodetask.ui.view_models.UsersViewModel
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

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)

        val loadData = loadData()

        if (loadData) {
            loadSearchText()
        }

        binding.swipeRefrashContainer.setOnRefreshListener {
            binding.swipeRefrashContainer.isRefreshing = true
            usersViewModel.getUsers()
            loadData()
            binding.swipeRefrashContainer.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData(): Boolean {
        val adapter =
            RecyclerViewAdapter(usersViewModel.users.value!!.toMutableList(), department, this)
        binding.recyclerView.adapter = adapter
        return true
    }

    private fun loadSearchText() {
        usersViewModel.searchText.observe(viewLifecycleOwner, { msg ->
            val myAdapter = binding.recyclerView.adapter as RecyclerViewAdapter
            myAdapter.filter.filter(msg)
        })
    }
}