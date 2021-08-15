package com.ulyanaab.findmyphone.utilities

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ulyanaab.findmyphone.R

fun replaceFragment(fragment: Fragment, addToBackStack: Boolean=false) {
    if(addToBackStack) {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.data_container, fragment).addToBackStack(null).commit()
    } else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.data_container, fragment).commit()
    }
}

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}