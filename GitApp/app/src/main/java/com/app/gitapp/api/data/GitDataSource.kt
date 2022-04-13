package com.app.gitapp.api.data

import com.app.gitapp.api.service.ApiService
import javax.inject.Inject

class GitDataSource @Inject constructor(private val service: ApiService) {
    suspend fun searchUsers(query: String) = service.searchUsers(query)

    suspend fun getUserDetail(username: String) = service.getUserDetail(username)
}