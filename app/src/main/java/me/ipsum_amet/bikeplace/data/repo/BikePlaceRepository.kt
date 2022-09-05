package me.ipsum_amet.bikeplace.data.repo

import me.ipsum_amet.bikeplace.data.db.remote.FayaBase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import me.ipsum_amet.bikeplace.data.model.Bike
import javax.inject.Inject

@ViewModelScoped
class BikePlaceRepository @Inject constructor(
    val fayaBase: FayaBase
) {
    fun getAllBikes(): List<Bike> { return fayaBase.getAllBikes() }
    suspend fun getAllBikesAsFlow(): Flow<List<Bike>> { return fayaBase.getAllBikesAsFlow() }

    fun getBikeById(bikeId: String): Bike { return fayaBase.getBikeById(bikeId) }
    suspend fun getBikeByIdAsFlow(bikeId: String): Flow<Bike> { return fayaBase.getBikeByIdAsFlow(bikeId) }

    fun getBikesByName(query: String): List<Bike>  { return fayaBase.getBikesByName(query) }
    suspend fun getBikesNameAsFlow(query: String): Flow<List<Bike>> { return fayaBase.getBikesByNameAsFlow(query) }

}