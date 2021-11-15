package com.alexbirichevskiy.appkodetask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexbirichevskiy.appkodetask.Consts.USER_TAG
import com.alexbirichevskiy.appkodetask.R
import com.alexbirichevskiy.appkodetask.databinding.UserItemBinding
import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UsersItemsAdapter(depart: String?, val fragmentT: ListFragment) :
    RecyclerView.Adapter<UsersItemsAdapter.UsersItemsViewHolder>() {
    var usersItemsList = emptyList<UserItemEntity>()
    val departStr = depart

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersItemsViewHolder {
        return UsersItemsViewHolder(parent, fragmentT)
    }

    override fun onBindViewHolder(holder: UsersItemsViewHolder, position: Int) {
        holder.bind(sortData(departStr)[position])
    }

    override fun getItemCount(): Int {
        return sortData(departStr).size
    }

    fun sortData(depart: String?): MutableList<UserItemEntity> {
        return if (depart == "all") {
            usersItemsList.toMutableList()
        } else {
            val list = emptyList<UserItemEntity>().toMutableList()
            for (i in usersItemsList) {
                if (i.department == depart) {
                    list.add(i)
                }
            }
            list
        }
    }

    inner class UsersItemsViewHolder(parent: ViewGroup, val fragmentT: ListFragment) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        ) {
        private val binding: UserItemBinding = UserItemBinding.bind(itemView)

        fun bind(UserItemEntity: UserItemEntity) {
            binding.fullNameTextView.text =
                "${UserItemEntity.firstName} ${UserItemEntity.lastName}"
            binding.departmentTextView.text = UserItemEntity.position
            binding.userTagTextView.text = UserItemEntity.userTagLower
            Glide.with(binding.root).load(UserItemEntity.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.avatarImageView)

            val fragment = ProfileFragment()

            fragment.arguments = Bundle().apply {
                putParcelable(USER_TAG, UserItemEntity)
            }

            itemView.setOnClickListener {
                fragmentT.activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.activity_layout, fragment)?.addToBackStack("lol")?.commit()
            }
        }
    }
}
