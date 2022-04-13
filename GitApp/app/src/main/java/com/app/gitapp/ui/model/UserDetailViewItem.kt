package com.app.gitapp.ui.model

data class UserDetailModel(
    val hasError: Boolean?,
    var imageUrl: String? = null,
    var name: String? = null,
    var email: String? = null,
    var userDetailItemList: MutableList<UserDetailViewItem?>? = null
)

data class UserDetailViewItem(
    val iconId: Int? = null,
    val infoText: String? = null
)
