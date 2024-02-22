package com.example.tbc_googlemaps_assign25.di

import android.app.Application
import com.example.tbc_googlemaps_assign25.data.repository.UserLocationRepositoryImpl
import com.example.tbc_googlemaps_assign25.domain.repository.IUserLocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserLocationRepository(
        locationClient: FusedLocationProviderClient,
        context: Application
    ): IUserLocationRepository {
        return UserLocationRepositoryImpl(locationClient, context)
    }
}