package com.app.gitapp.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.app.gitapp.R
import com.bumptech.glide.Glide


object ViewUtil {
    private fun ImageView.loadImage(uri: String?) {
        Glide.with(context)
            .load(uri)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(this)
    }

    fun loadImageView(view: ImageView, url: String?) {
        view.loadImage(url)
    }


    private fun ImageView.loadImage(drawableId: Int?) {
        Glide.with(context)
            .load(drawableId)
            .into(this)
    }

    fun loadImageView(view: ImageView, drawableId: Int?) {
        view.loadImage(drawableId)
    }

    fun hideKeyboard(activity: FragmentActivity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}