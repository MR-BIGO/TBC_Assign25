package com.example.tbc_googlemaps_assign25.data.repository

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.example.tbc_googlemaps_assign25.data.common.Resource
import com.example.tbc_googlemaps_assign25.domain.repository.IUserLocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserLocationRepositoryImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val context: Application
) : IUserLocationRepository {
    override suspend fun getUserLocation(): Flow<Resource<Location?>> =flow {
            emit(Resource.Loading(true))
            try {
                //check fine location permission
                val hasFineLocationPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                //check coarse location permission
                val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                //check if both are true
                if (hasFineLocationPermission && hasCoarseLocationPermission) {

                        //emit last location
                        val lastLocation = locationClient.lastLocation.await()
                        emit(Resource.Success(lastLocation))

                }else{
                    emit(Resource.Error("No Permissions!"))
                }
            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: "Unknown Error"))
            }
            emit(Resource.Loading(false))

    }.flowOn(Dispatchers.IO)
}