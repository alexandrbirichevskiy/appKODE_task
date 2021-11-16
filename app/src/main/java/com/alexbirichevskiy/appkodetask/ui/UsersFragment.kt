package com.alexbirichevskiy.appkodetask.ui

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

class UsersFragment : Fragment() {

    private lateinit var usersViewModel: UsersViewModel
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

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
//            loadData(app.usersItemsRepo)
            binding.swipeRefrashContainer.isRefreshing = false
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        usersViewModel =
            ViewModelProvider(this, UsersViewModelFactory(context))[UsersViewModel::class.java]

        usersViewModel.getUsers()

        usersViewModel.users.observe(viewLifecycleOwner, Observer { users ->
            binding.recyclerView.also {
                it.layoutManager = LinearLayoutManager(context)
                it.setHasFixedSize(true)
                it.adapter = RecyclerViewAdapter(users, department, this)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}