package com.example.tbc_googlemaps_assign25.presentation.screen.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_googlemaps_assign25.data.common.Resource
import com.example.tbc_googlemaps_assign25.domain.use_case.GetUserLocationUseCase
import com.example.tbc_googlemaps_assign25.presentation.event.MapFragmentEvent
import com.example.tbc_googlemaps_assign25.presentation.state.map.MapFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapFragmentViewModel @Inject constructor(private val userLocation: GetUserLocationUseCase) : ViewModel() {

    private val _mapState = MutableStateFlow(MapFragmentState())
    val mapState: SharedFlow<MapFragmentState> = _mapState.asStateFlow()

    fun onEvent(event: MapFragmentEvent){
        when(event){
            is MapFragmentEvent.GetCurrentLocationPressed -> {getLocation()}
            is MapFragmentEvent.SearchPressed -> {}
            is MapFragmentEvent.ResetError -> {setError(null)}
        }
    }

    private fun setError(error: String?){
        viewModelScope.launch {
            _mapState.update { currentState -> currentState.copy(error = error) }
        }
    }

    private fun getLocation(){
        viewModelScope.launch {
            userLocation().collect{result ->
                when(result){
                    is Resource.Success -> {_mapState.update { currentState -> currentState.copy(location = result.data) }}
                    is Resource.Error -> {setError(result.error)}
                    is Resource.Loading -> {_mapState.update { currentState -> currentState.copy(loading = result.loading) }}
                }
            }
        }
    }
}