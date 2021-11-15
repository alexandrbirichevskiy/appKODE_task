package com.alexbirichevskiy.appkodetask.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexbirichevskiy.appkodetask.Consts.ARG_TAG
import com.alexbirichevskiy.appkodetask.MyApplication
import com.alexbirichevskiy.appkodetask.databinding.FragmentListBinding
import com.alexbirichevskiy.appkodetask.repos.UsersItemsRepo

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var userItemsRepo: UsersItemsRepo
    private lateinit var adapter: UsersItemsAdapter

    private val app by lazy { activity?.application as MyApplication }

    private var department: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf {
            it.containsKey(ARG_TAG)?.apply {
                department = requireArguments().getString(ARG_TAG)
            }
        }

        adapter = UsersItemsAdapter(department, this)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        loadData()

        binding.swipeRefrashContainer.setOnRefreshListener {
            binding.swipeRefrashContainer.isRefreshing = true
            loadData()
            binding.swipeRefrashContainer.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun loadData() {
        userItemsRepo = app.usersItemsRepo
        userItemsRepo.getUsersItems({
            adapter.usersItemsList = it
            adapter.notifyDataSetChanged()
        }, { throwable ->
            Log.d("@@@", "Error! ${throwable.message}")
            Toast.makeText(context, "Error! ${throwable.message}", Toast.LENGTH_SHORT).show()
        })
    }
}