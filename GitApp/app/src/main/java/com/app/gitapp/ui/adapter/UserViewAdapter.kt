package com.app.gitapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.gitapp.api.model.UserItem
import com.app.gitapp.databinding.UserViewItemBinding
import com.app.gitapp.ui.adapter.UserViewHolder.Companion.USER_ID_COMPARATOR
import com.app.gitapp.util.ViewUtil

class UserViewAdapter(private val onClick: (String) -> Unit) :
    PagingDataAdapter<UserItem, UserViewHolder>(
        USER_ID_COMPARATOR
    ) {
    private var userItemList = mutableListOf<UserItem?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        UserViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        userItemList[position]?.let { holder.bind(it, onClick) }
    }

    override fun getItemCount() = userItemList.size

    fun setUserItemList(userItemList: List<UserItem?>?) {
        userItemList?.let {
            this.userItemList = it.toMutableList()
            notifyDataSetChanged()
        }
    }
}

class UserViewHolder(private val binding: UserViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(userItem: UserItem, onClick: (String) -> Unit) {
        with(binding) {
            ViewUtil.loadImageView(ivUser, userItem.avatar_url)
            tvUserName.text = userItem.login
            clUserItem.setOnClickListener {
                userItem.login?.let { it1 -> onClick(it1) }
            }
        }
    }

    companion object {
        val USER_ID_COMPARATOR = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}