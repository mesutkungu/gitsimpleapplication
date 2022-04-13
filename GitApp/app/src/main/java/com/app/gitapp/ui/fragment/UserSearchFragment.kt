package com.app.gitapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gitapp.R
import com.app.gitapp.api.model.NetworkResponse
import com.app.gitapp.databinding.FragmentUserSearchBinding
import com.app.gitapp.ui.adapter.UserViewAdapter
import com.app.gitapp.ui.viewmodel.UserSearchViewModel
import com.app.gitapp.util.ViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchFragment : Fragment() {
    private val viewModel: UserSearchViewModel by viewModels()
    private lateinit var binding: FragmentUserSearchBinding
    private val adapter = UserViewAdapter() { openUserDetail(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserSearchBinding.inflate(inflater, container, false)
        prepareUi()
        return binding.root
    }

    private fun prepareUi() {
        with(binding) {
            header.tvTitle.text = getString(R.string.fragment_user_search)
            header.ivBack.visibility = View.INVISIBLE
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvUsers.layoutManager = layoutManager
            rvUsers.adapter = adapter
            rvUsers.setHasFixedSize(true)
            rvUsers.addItemDecoration(
                DividerItemDecoration(
                    context,
                    layoutManager.orientation
                )
            )
            btnSearch.text = getText(R.string.search_label)
            btnSearch.setOnClickListener {
                searchUsers()
            }
        }
    }

    private fun searchUsers() {
        showProgress()
        activity?.let { ViewUtil.hideKeyboard(it) }
        viewModel.searchUser(binding.svUserSearch.query.toString())
        viewModel.gifResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    dismissProgress()
                    if (response.data?.items?.size == 0) {
                        showError()
                    } else {
                        binding.tvNoData.visibility = View.GONE
                        adapter.setUserItemList(response.data?.items)
                    }
                }
                is NetworkResponse.Error -> {
                    showError()
                }
            }
        }
    }

    private fun showProgress() {
        with(binding) {
            tvNoData.visibility = View.GONE
            pbLoading.visibility = View.VISIBLE
        }
    }

    private fun dismissProgress() {
        with(binding) {
            rvUsers.visibility = View.VISIBLE
            pbLoading.visibility = View.GONE
        }
    }

    private fun showError() {
        with(binding) {
            pbLoading.visibility = View.GONE
            tvNoData.visibility = View.VISIBLE
            tvNoData.text = getText(R.string.no_data_label)
            rvUsers.visibility = View.GONE
        }
    }

    private fun openUserDetail(userName: String) {
        val direction =
            UserSearchFragmentDirections.actionViewPagerFragmentToUserDetailFragment(userName)
        findNavController().navigate(direction)
    }
}