package com.ulyanaab.findmyphone.view.parentPart

import android.app.AlertDialog
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ulyanaab.findmyphone.R
import com.ulyanaab.findmyphone.controllers.MapController
import com.ulyanaab.findmyphone.model.objects.PhoneMetrics
import com.ulyanaab.findmyphone.utilities.KEY_TO_SEND_TOKEN

class MapsFragment : Fragment() {

    private val controller = MapController()

    private val ZOOM_LEVEL = 50f
    private val DAY = 86_400_000L

    private lateinit var map: GoogleMap
    private var childToken: String? = null

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        googleMap.setOnMarkerClickListener {
            showDialog(it)
            false
        }

        showData()

    }

    private fun getTitle(phoneMetrics: PhoneMetrics): String {
        return "latitude = ${phoneMetrics.latitude}\n" +
                "longitude = ${phoneMetrics.longitude}\n" +
                "cell id = ${phoneMetrics.cellId}\n" +
                "lac = ${phoneMetrics.lac}\n" +
                "rsrp = ${phoneMetrics.rsrp}\n" +
                "rsrq = ${phoneMetrics.rsrq}\n" +
                "sinr = ${phoneMetrics.sinr}"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        childToken = arguments?.getString(KEY_TO_SEND_TOKEN)
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun showData(
        timeBegin: Long = System.currentTimeMillis() - DAY,
        timeEnd: Long = System.currentTimeMillis()
    ) {
        map.clear()

        val data = getPoints(timeBegin, timeEnd)
        data.forEach {
            if (it.latitude != null && it.longitude != null) {
                val latLng = LatLng(it.latitude, it.longitude)
                map.addMarker(
                    MarkerOptions().position(latLng)
                        .title("marker")
                        .snippet(getTitle(it))
                )
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL))
            }
        }
    }

    private fun getPoints(
        timeBegin: Long,
        timeEnd: Long
    ): List<PhoneMetrics> {
        return if(childToken != null) {
            controller.getData(childToken!!, timeBegin, timeEnd)
        } else {
            listOf()
        }
    }

    private fun showDialog(marker: Marker) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(marker.title)
            .setMessage(marker.snippet)
            .setPositiveButton("Ок") { dialog, _ ->
                dialog.cancel()
            }
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.day -> showData()
            R.id.week -> showData(
                System.currentTimeMillis() - DAY * 7,
                System.currentTimeMillis()
            )
            R.id.month -> showData(
                System.currentTimeMillis() - DAY * 30,
                System.currentTimeMillis()
            )
        }
        return true
    }

    companion object {
        fun newInstance(token: String) =
            MapsFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_TO_SEND_TOKEN, token)
                }
            }
    }
}