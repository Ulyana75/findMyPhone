package com.ulyanaab.findmyphone.utilities

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ulyanaab.findmyphone.R

fun replaceFragment(fragment: Fragment) {
    APP_ACTIVITY.supportFragmentManager.beginTransaction()
        .replace(R.id.data_container, fragment).commit()
}

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}