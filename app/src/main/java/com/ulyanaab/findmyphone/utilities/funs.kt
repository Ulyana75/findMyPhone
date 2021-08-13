package com.ulyanaab.findmyphone.utilities

import androidx.fragment.app.Fragment
import com.ulyanaab.findmyphone.R

fun replaceFragment(fragment: Fragment) {
    APP_ACTIVITY.supportFragmentManager.beginTransaction()
        .replace(R.id.data_container, fragment).commit()
}