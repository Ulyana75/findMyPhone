package com.ulyanaab.findmyphone.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.ulyanaab.findmyphone.R


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()
        startService()
    }

    private fun startService() {
        if (!LocationService.isServiceStarted) {
            ContextCompat.startForegroundService(
                requireContext(),
                Intent(requireContext(), LocationService::class.java)
            )
        }
    }

}