package com.alexbirichevskiy.appkodetask.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexbirichevskiy.appkodetask.Consts.ARG_TAG
import com.alexbirichevskiy.appkodetask.databinding.FragmentUsersBinding
import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity
import com.alexbirichevskiy.appkodetask.ui.RecyclerViewClickListener
import com.alexbirichevskiy.appkodetask.ui.view_models.UsersViewModel
import com.alexbirichevskiy.appkodetask.ui.view_models.UsersViewModelFactory
import com.alexbirichevskiy.appkodetask.ui.adapters.RecyclerViewAdapter

class UsersFragment : Fragment(), RecyclerViewClickListener {
    private lateinit var usersViewModel: UsersViewModel

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        usersViewModel =
            ViewModelProvider(this, UsersViewModelFactory(context))[UsersViewModel::class.java]

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onRefresh(){
        usersViewModel.getUsers()
        usersViewModel.users.observe(viewLifecycleOwner, Observer { users ->
            binding.recyclerView.also {
                it.layoutManager = LinearLayoutManager(context)
                it.setHasFixedSize(true)
                it.adapter = RecyclerViewAdapter(users, department, this)
            }
        })
    }

    override fun onRecyclerViewClickListener(view: View, user: UserItemEntity) {
//            todo
    }
}