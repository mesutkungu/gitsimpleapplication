package com.app.gitapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.gitapp.databinding.UserDetailInfoLayoutBinding
import com.app.gitapp.ui.model.UserDetailViewItem
import com.app.gitapp.util.ViewUtil

class UserDetailsAdapter : RecyclerView.Adapter<UserDetailsViewHolder>() {
    private var userDetailItemList = mutableListOf<UserDetailViewItem?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserDetailsViewHolder(
        UserDetailInfoLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: UserDetailsViewHolder, position: Int) {
        userDetailItemList[position]?.let { holder.bind(it) }
    }

    override fun getItemCount() = userDetailItemList.size

    fun setUserDetailItemList(userDetailItemList: List<UserDetailViewItem?>?) {
        userDetailItemList?.let {
            this.userDetailItemList = it.toMutableList()
            notifyDataSetChanged()
        }
    }

}

class UserDetailsViewHolder(private val binding: UserDetailInfoLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(userDetailItem: UserDetailViewItem) {
        with(binding) {
            ViewUtil.loadImageView(ivUserInfoIcon, userDetailItem.iconId)
            tvUserInfoText.text = userDetailItem.infoText?:"No Information"
        }
    }
}