package com.ulyanaab.findmyphone.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.utilities.PARENT_TOKEN
import com.ulyanaab.findmyphone.utilities.TOKEN_KEY
import com.ulyanaab.findmyphone.utilities.TOKEN_PREFERENCE
import com.ulyanaab.findmyphone.utilities.replaceFragment
import com.ulyanaab.findmyphone.view.childPart.StartChildFragment
import com.ulyanaab.findmyphone.view.parentPart.MainParentFragment


class ChooseAccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_account, container, false)
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        with(requireView()) {
            findViewById<Button>(R.id.button_child).setOnClickListener {
                replaceFragment(StartChildFragment())
            }

            findViewById<Button>(R.id.button_parent).setOnClickListener {
                addParentToken()
                replaceFragment(MainParentFragment())
            }
        }
    }

    private fun addParentToken() {
        val sPref = requireContext().getSharedPreferences(TOKEN_PREFERENCE, AppCompatActivity.MODE_PRIVATE)
        sPref.edit().putString(TOKEN_KEY, PARENT_TOKEN).apply()
    }

}