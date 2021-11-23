package com.alexbirichevskiy.appkodetask.ui.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alexbirichevskiy.appkodetask.Consts.PROFILE_FRAGMENT_TAG
import com.alexbirichevskiy.appkodetask.Consts.USER_TAG
import com.alexbirichevskiy.appkodetask.R
import com.alexbirichevskiy.appkodetask.databinding.UserItemBinding
import com.alexbirichevskiy.appkodetask.domain.entities.UserItemEntity
import com.alexbirichevskiy.appkodetask.ui.fragments.ProfileFragment
import com.alexbirichevskiy.appkodetask.ui.fragments.UsersFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

class RecyclerViewAdapter(
    private val users: MutableList<UserItemEntity>,
    private val depart: String?,
    val fragmentT: UsersFragment
) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>(), Filterable {

    private var usersAll = users.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        return RecyclerViewViewHolder(parent, fragmentT)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bind(filterByDepartData(depart)[position])
    }

    override fun getItemCount(): Int {
        return filterByDepartData(depart).size
    }

    private fun filterByDepartData(depart: String?): MutableList<UserItemEntity> {
        return if (depart == "all") {
            users.toMutableList()
        } else {
            val list = emptyList<UserItemEntity>().toMutableList()
            for (i in users) {
                if (i.department == depart) {
                    list.add(i)
                }
            }
            list
        }
    }

    val filterData: Filter = (object : Filter() {
        private var filterResults = FilterResults()

        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = emptyList<UserItemEntity>().toMutableList()
            if (p0.toString().isEmpty()) {
                filteredList.addAll(usersAll)
                Log.d("@@@", true.toString())
            } else {
                for (users in usersAll) {
                    if (users.firstName.toString().lowercase(Locale.getDefault())
                            .contains(p0.toString().lowercase(Locale.getDefault()))
                        || users.lastName.toString().lowercase(Locale.getDefault())
                            .contains(p0.toString().lowercase(Locale.getDefault()))
                        || users.userTag.toString().lowercase(Locale.getDefault())
                            .contains(p0.toString().lowercase(Locale.getDefault()))
                    ) {
                        filteredList.add(users)
                    }
                }
            }

            filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            users.clear()
            if (filterResults.values != null) {
                users.addAll(filterResults.values as Collection<UserItemEntity>)
                notifyDataSetChanged()
            }
        }
    })


    override fun getFilter(): Filter {
        return filterData
    }

    inner class RecyclerViewViewHolder(parent: ViewGroup, val fragmentT: UsersFragment) :
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
                    ?.replace(R.id.activity_layout, fragment)?.addToBackStack(PROFILE_FRAGMENT_TAG)
                    ?.commit()
            }
        }
    }
}
