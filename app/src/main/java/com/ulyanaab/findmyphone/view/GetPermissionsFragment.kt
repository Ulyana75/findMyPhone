package com.ulyanaab.findmyphone.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.utilities.REQUEST_LOCATION_CODE
import com.ulyanaab.findmyphone.utilities.replaceFragment


class GetPermissionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        checkPermissions()
        return inflater.inflate(R.layout.fragment_get_permissions, container, false)
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            replaceFragment(MainFragment())
        }
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), REQUEST_LOCATION_CODE
        )
    }

    private fun initViews() {
        with(requireView()) {

            findViewById<Button>(R.id.button_give_permission).setOnClickListener {
                requestLocationPermissions()
            }

            findViewById<Button>(R.id.button_go_to_settings).setOnClickListener {
                startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
            }
        }
    }

}