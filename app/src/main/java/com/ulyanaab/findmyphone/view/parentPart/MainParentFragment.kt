package com.ulyanaab.findmyphone.view.parentPart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.utilities.replaceFragment
import com.ulyanaab.findmyphone.utilities.showToast


class MainParentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_parent, container, false)
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        with(requireView()) {
            findViewById<Button>(R.id.button_open_map).setOnClickListener {
                val text = findViewById<EditText>(R.id.id_edit_text).text.toString()
                if (text != "") {
                    replaceFragment(MapsFragment.newInstance(text), true)
                } else {
                    showToast("Вы не ввели токен")
                }
            }
        }
    }

}