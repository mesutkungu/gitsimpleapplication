package com.app.gitapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gitapp.api.model.NetworkResponse
import com.app.gitapp.api.model.SearchOutput
import com.app.gitapp.api.repository.GitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val repository: GitRepository
) : ViewModel() {
    private val _gitResponse by lazy { MutableLiveData<NetworkResponse<SearchOutput>>() }

    val gitResponse: LiveData<NetworkResponse<SearchOutput>>
        get() = _gitResponse

    fun searchUser(query: String) = viewModelScope.launch {
        repository.searchUsers(query).collect { values ->
            _gitResponse.value = values
        }
    }
}