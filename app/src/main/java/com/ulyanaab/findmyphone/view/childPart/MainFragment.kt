package com.ulyanaab.findmyphone.view.childPart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.utilities.liveDataNeedToStop


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("LOL", "start service")
        startService()
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        with(requireView()) {
            findViewById<Button>(R.id.button_stop).setOnClickListener {
                stopService()
                serviceStopped()
            }

            findViewById<Button>(R.id.button_continue).setOnClickListener {
                startService()
                serviceWorking()
            }
        }
    }

    private fun serviceWorking() {
        with(requireView()) {
            findViewById<TextView>(R.id.text_label).text = getString(R.string.main_fragment_text)
            findViewById<Button>(R.id.button_stop).visibility = View.VISIBLE
            findViewById<Button>(R.id.button_continue).visibility = View.GONE
        }
    }

    private fun serviceStopped() {
        with(requireView()) {
            findViewById<TextView>(R.id.text_label).text = getString(R.string.main_fragment_text_stop)
            findViewById<Button>(R.id.button_stop).visibility = View.GONE
            findViewById<Button>(R.id.button_continue).visibility = View.VISIBLE
        }
    }

    private fun startService() {
        if (!LocationService.isServiceStarted) {
            liveDataNeedToStop.value = false
            ContextCompat.startForegroundService(
                requireContext(),
                Intent(requireContext(), LocationService::class.java)
            )
        }
        serviceWorking()
    }

    private fun stopService() {
        liveDataNeedToStop.value = true
        serviceStopped()
    }

}