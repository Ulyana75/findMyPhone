package com.ulyanaab.findmyphone.view.childPart

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.utilities.*


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
        startService()
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    @SuppressLint("SetTextI18n")
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

            findViewById<TextView>(R.id.token_text).text = token

            findViewById<ImageView>(R.id.copy_image).setOnClickListener {
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("copied token", token)
                clipboard.setPrimaryClip(clip)
                showToast("Скопировано в буфер обмена")
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
            findViewById<TextView>(R.id.text_label).text =
                getString(R.string.main_fragment_text_stop)
            findViewById<Button>(R.id.button_stop).visibility = View.GONE
            findViewById<Button>(R.id.button_continue).visibility = View.VISIBLE
        }
    }

    private fun startService() {
        if (!LocationService.isServiceStarted) {
            Log.d("LOL", "start service")
            val intent = Intent(requireContext(), LocationService::class.java)
            intent.action = ACTION_START_SERVICE
            ContextCompat.startForegroundService(
                requireContext(),
                intent
            )
        }
        serviceWorking()
    }

    private fun stopService() {
        val intent = Intent(requireContext(), LocationService::class.java)
        intent.action = ACTION_STOP_SERVICE
        ContextCompat.startForegroundService(
            requireContext(),
            intent
        )
        serviceStopped()
    }

}