package com.example.tbc_googlemaps_assign25.presentation.event

sealed class MapFragmentEvent {
    data object GetCurrentLocationPressed : MapFragmentEvent()
    data object SearchPressed : MapFragmentEvent()
    data object ResetError : MapFragmentEvent()
}