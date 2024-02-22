package com.example.tbc_googlemaps_assign25.domain.repository

import android.location.Location
import com.example.tbc_googlemaps_assign25.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface IUserLocationRepository {
    suspend fun getUserLocation(): Flow<Resource<Location?>>
}