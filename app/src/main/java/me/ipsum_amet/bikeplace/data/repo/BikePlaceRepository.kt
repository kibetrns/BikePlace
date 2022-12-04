package me.ipsum_amet.bikeplace.data.repo

import me.ipsum_amet.bikeplace.data.db.remote.FayaBase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.*
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.data.service.BookingsInfoService
import javax.inject.Inject

@ViewModelScoped
class BikePlaceRepository @Inject constructor(
    val fayaBase: FayaBase,
    val bookingsInfoService: BookingsInfoService
) {
    fun getAllBikes(): List<Bike> { return fayaBase.getAllBikes() }
    suspend fun getAllBikesAsFlow(): Flow<List<Bike>> { return fayaBase.getAllBikesAsFlow() }

    fun getBikeById(bikeId: String): Bike { return fayaBase.getBikeById(bikeId) }
    suspend fun getBikeByIdAsFlow(bikeId: String): Flow<Bike> { return fayaBase.getBikeByIdAsFlow(bikeId) }

    fun getBikesByName(query: String): List<Bike>  { return fayaBase.getBikesByName(query) }
    suspend fun getBikesNameAsFlow(query: String): Flow<List<Bike>> { return fayaBase.getBikesByNameAsFlow(query) }

    suspend fun updateBikeAsFlow(bike: Bike): Flow<String> { return fayaBase.updateBikeAsFLow(bike) }

    suspend fun deleteBikeAsFLow(bikeId: String) { fayaBase.deleteBikeAsFlow(bikeId) }


    suspend fun getAllBookingsInfoAsFlow(): Flow<List<BookingsInfoRes>> {
       return bookingsInfoService.getAllBookingsInfoAsFlow()
    }


    suspend fun getBookingsInfoByReceiptId(receiptId: String): BookingsInfoRes? {
        return bookingsInfoService.getBookingsInfoByReceiptId(receiptId = receiptId)
    }
}