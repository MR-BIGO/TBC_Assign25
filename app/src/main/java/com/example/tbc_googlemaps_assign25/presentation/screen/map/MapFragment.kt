package com.example.tbc_googlemaps_assign25.presentation.screen.map

import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbc_googlemaps_assign25.R
import com.example.tbc_googlemaps_assign25.databinding.FragmentMapBinding
import com.example.tbc_googlemaps_assign25.presentation.event.MapFragmentEvent
import com.example.tbc_googlemaps_assign25.presentation.screen.base.BaseFragment
import com.example.tbc_googlemaps_assign25.presentation.state.map.MapFragmentState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback {
    private var googleMap: GoogleMap? = null
    private val viewModel: MapFragmentViewModel by viewModels()

    override fun setUp() {

        setUpMap()
        bindObservers()
        listeners()
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        googleMap?.let {
            it.uiSettings.isCompassEnabled = true
            it.uiSettings.isZoomControlsEnabled = true
        }
    }

    private fun setUpMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    private fun listeners() = with(binding) {
        btnCurrentLocation.setOnClickListener {
            viewModel.onEvent(MapFragmentEvent.GetCurrentLocationPressed)
        }

        btnSearch.setOnClickListener {

        }
    }

    private fun handleState(state: MapFragmentState) {
        binding.progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE

        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(MapFragmentEvent.ResetError)
        }

        state.location?.let {
            d("checking location on emulator", it.toString())
            googleMap!!.apply {
                addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)))
                moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(it.latitude, it.longitude),
                        18f
                    )
                )
            }
        }
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mapState.collect {
                    handleState(it)
                }
            }
        }

    }
}