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
import com.ulyanaab.findmyphone.utilities.replaceFragment
import java.util.*

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

        requestPoints()

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

    override fun onStart() {
        super.onStart()
        controller.pointsLiveData.observe(this) {
            showData(it)
        }
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

    private fun showData(
        data: List<PhoneMetrics>,
    ) {
        map.clear()

        var latLng = LatLng(0.0, 0.0)
        data.forEach {
            if (it.latitude != null && it.longitude != null) {
                latLng = LatLng(it.latitude, it.longitude)
                map.addMarker(
                    MarkerOptions().position(latLng)
                        .title(it.date.toString())
                        .snippet(getTitle(it))
                )
            }
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL))
    }

    private fun requestPoints(
        timeBegin: Date = Date(System.currentTimeMillis() - DAY),
        timeEnd: Date = Date(System.currentTimeMillis())
    ) {
        if(childToken != null) {
            controller.getData(childToken!!, timeBegin, timeEnd, this::noSuchToken)
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

    private fun noSuchToken() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.wrong_token_title))
            .setPositiveButton("Ок") { dialog, _ ->
                dialog.cancel()
                replaceFragment(MainParentFragment())
            }
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.day -> requestPoints()
            R.id.week -> requestPoints(
                Date(System.currentTimeMillis() - DAY * 7),
                Date(System.currentTimeMillis())
            )
            R.id.month -> requestPoints(
                Date(System.currentTimeMillis() - DAY * 30),
                Date(System.currentTimeMillis())
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