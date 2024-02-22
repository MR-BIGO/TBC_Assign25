package com.example.tbc_googlemaps_assign25.domain.use_case

import android.location.Location
import com.example.tbc_googlemaps_assign25.data.common.Resource
import com.example.tbc_googlemaps_assign25.domain.repository.IUserLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(private val repository: IUserLocationRepository){

    suspend operator fun invoke(): Flow<Resource<Location?>>{
        return repository.getUserLocation()
    }
}