package com.app.gitapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gitapp.R
import com.app.gitapp.api.model.NetworkResponse
import com.app.gitapp.api.repository.GitRepository
import com.app.gitapp.ui.model.UserDetailModel
import com.app.gitapp.ui.model.UserDetailViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: GitRepository
) : ViewModel() {
    private val _userDetail by lazy { MutableLiveData<UserDetailModel>() }

    val userDetailResponse: LiveData<UserDetailModel>
        get() = _userDetail

    fun getUserDetail(username: String) = viewModelScope.launch {
        repository.getUserDetail(username).collect {
            when (it) {
                is NetworkResponse.Success -> {
                    val userDetailModel = UserDetailModel(false)
                    userDetailModel.imageUrl = it.data?.avatar_url.orEmpty()
                    userDetailModel.name = it.data?.login.orEmpty()
                    userDetailModel.email = it.data?.email.orEmpty()
                    val userDetailItemList = mutableListOf<UserDetailViewItem?>()
                    userDetailItemList.add(UserDetailViewItem(R.drawable.company_icon,it.data?.company))
                    userDetailItemList.add(UserDetailViewItem(R.drawable.location_icon,it.data?.location))
                    userDetailItemList.add(UserDetailViewItem(R.drawable.twitter_icon,it.data?.twitter_username))
                    userDetailModel.userDetailItemList = userDetailItemList
                    _userDetail.postValue(userDetailModel)
                }
                is NetworkResponse.Error -> {
                    _userDetail.postValue(UserDetailModel(true))
                }
            }
        }
    }

}