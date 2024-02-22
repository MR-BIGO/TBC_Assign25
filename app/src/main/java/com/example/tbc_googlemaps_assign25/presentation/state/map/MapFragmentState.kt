package com.example.tbc_googlemaps_assign25.presentation.state.map

import android.location.Location

data class MapFragmentState (
    val location: Location? = null,
    val loading: Boolean = false,
    val error: String? = null
)