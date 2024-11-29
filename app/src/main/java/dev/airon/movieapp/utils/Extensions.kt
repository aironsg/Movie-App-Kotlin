package dev.airon.movieapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import dev.airon.movieapp.R


fun Fragment.initToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean = true) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
