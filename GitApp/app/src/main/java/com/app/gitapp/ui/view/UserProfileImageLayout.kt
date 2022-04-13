package com.app.gitapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.gitapp.databinding.UserProfileImageLayoutBinding
import com.app.gitapp.util.ViewUtil

class UserProfileImageLayout(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {
    val binding = UserProfileImageLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun setImage(url: String) {
        ViewUtil.loadImageView(binding.ivUser, url)
    }

    fun setName(username: String) {
        binding.tvUserName.text = username
    }

    fun setEmail(email: String) {
        binding.tvUserEmail.text = email
    }
}