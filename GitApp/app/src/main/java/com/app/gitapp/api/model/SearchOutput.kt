package com.app.gitapp.api.model

data class SearchOutput(
    val total_count: Int? = null,
    val incomplete_results: Boolean? = false,
    val items:List<UserItem>? = null
)
