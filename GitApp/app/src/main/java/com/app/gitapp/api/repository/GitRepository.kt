package com.app.gitapp.api.repository

import com.app.gitapp.api.data.GitDataSource
import com.app.gitapp.api.model.NetworkResponse
import com.app.gitapp.api.model.SearchOutput
import com.app.gitapp.api.model.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GitRepository @Inject constructor(private val gitDataSource: GitDataSource): BaseApiResponse() {
    fun searchUsers(query: String): Flow<NetworkResponse<SearchOutput>> {
        return flow {
            emit(safeApiCall { gitDataSource.searchUsers(query) })
        }.flowOn(Dispatchers.IO)
    }

    fun getUserDetail(username: String): Flow<NetworkResponse<UserDetail>> {
        return flow {
            emit(safeApiCall { gitDataSource.getUserDetail(username) })
        }.flowOn(Dispatchers.IO)
    }
}