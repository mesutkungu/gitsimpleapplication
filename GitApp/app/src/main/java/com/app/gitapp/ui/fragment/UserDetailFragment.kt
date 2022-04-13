package com.app.gitapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gitapp.R
import com.app.gitapp.databinding.FragmentUserDetailBinding
import com.app.gitapp.ui.adapter.UserDetailsAdapter
import com.app.gitapp.ui.viewmodel.UserDetailViewModel
import com.app.gitapp.util.ViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {
    private val viewModel: UserDetailViewModel by viewModels()
    private lateinit var binding: FragmentUserDetailBinding
    private val adapter = UserDetailsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        prepareUi()
        getUserDetail()
        return binding.root
    }

    private fun prepareUi() {
        with(binding) {
            header.tvTitle.text = getString(R.string.fragment_user_search)
            header.ivBack.setOnClickListener {
                back()
            }
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvUserDetails.layoutManager = layoutManager
            rvUserDetails.adapter = adapter
            rvUserDetails.setHasFixedSize(true)
        }
    }

    private fun getUserDetail() {
        showProgress()
        activity?.let { ViewUtil.hideKeyboard(it) }
        getUsername()?.let { viewModel.getUserDetail(it) }
        viewModel.userDetailResponse.observe(viewLifecycleOwner) { response ->
            if (response.hasError == false) {
                dismissProgress()
                with(binding) {
                    tvError.visibility = View.GONE
                    userProfileView.visibility = View.VISIBLE
                    userProfileView.setImage(response.imageUrl.orEmpty())
                    userProfileView.setName(response.name.orEmpty())
                    userProfileView.setEmail(response.email.orEmpty())
                    adapter.setUserDetailItemList(response.userDetailItemList)
                }
            } else {
                showError()
            }

        }
    }

    private fun showProgress() {
        with(binding) {
            pbLoading.visibility = View.VISIBLE
        }
    }

    private fun dismissProgress() {
        with(binding) {
            pbLoading.visibility = View.GONE
        }
    }

    private fun showError() {
        with(binding) {
            pbLoading.visibility = View.GONE
            tvError.visibility = View.VISIBLE
            tvError.text = getText(R.string.no_data_label)
            userProfileView.visibility = View.GONE
        }
    }

    private fun getUsername() = arguments?.let { UserDetailFragmentArgs.fromBundle(it).username }

    private fun back() {
        val direction =
            UserDetailFragmentDirections.actionBack()
        findNavController().navigate(direction)
    }
}