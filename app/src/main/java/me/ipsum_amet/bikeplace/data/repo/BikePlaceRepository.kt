package me.ipsum_amet.bikeplace.data.repo

import android.util.Log
import me.ipsum_amet.bikeplace.data.db.remote.FayaBase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import me.ipsum_amet.bikeplace.data.dto.request.BookingsInfoReq
import me.ipsum_amet.bikeplace.data.dto.request.BookingsReq
import me.ipsum_amet.bikeplace.data.dto.response.toBookingInfo
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.data.model.BookingInfo
import me.ipsum_amet.bikeplace.data.service.BookingInfoService
import me.ipsum_amet.bikeplace.data.service.MpesaService
import javax.inject.Inject

@ViewModelScoped
class BikePlaceRepository @Inject constructor(
    val fayaBase: FayaBase,
    val mpesaService: MpesaService,
    val bookingInfoService: BookingInfoService
) {
    fun getAllBikes(): List<Bike> { return fayaBase.getAllBikes() }
    suspend fun getAllBikesAsFlow(): Flow<List<Bike>> { return fayaBase.getAllBikesAsFlow() }

    fun getBikeById(bikeId: String): Bike { return fayaBase.getBikeById(bikeId) }
    suspend fun getBikeByIdAsFlow(bikeId: String): Flow<Bike> { return fayaBase.getBikeByIdAsFlow(bikeId) }

    fun getBikesByName(query: String): List<Bike>  { return fayaBase.getBikesByName(query) }
    suspend fun getBikesNameAsFlow(query: String): Flow<List<Bike>> { return fayaBase.getBikesByNameAsFlow(query) }

    suspend fun updateBikeAsFlow(bike: Bike): Flow<String> { return fayaBase.updateBikeAsFLow(bike) }

    suspend fun deleteBikeAsFLow(bikeId: String) { fayaBase.deleteBikeAsFlow(bikeId) }



    suspend fun getAllBikeInfoByUserIdAsFlow(bookingsInfoReq: BookingsInfoReq): List<BookingInfo>{
        return try {
            bookingInfoService.getAllBookingInfoOfUserAsFlow(bookingsInfoReq = bookingsInfoReq).map { it.toBookingInfo() }
        } catch (ex: Exception) {
            ex.localizedMessage?.let { Log.e("getAllBInByUIdAsFloRepo", it) }
            emptyList()
        }
    }

    suspend fun makeBookingWithBPAPI(bookingsReq: BookingsReq) {
        try {
            bookingInfoService.makeBookingWithBPAPI(bookingsReq)
        } catch (ex: Exception) {
            Log.e("makeBookingWitBPAPIRepo", ex.stackTrace.toString())
        }
    }

}

/*
   viewModelScope.launch(Dispatchers.IO) {


            Log.d("getAllBokinInfoOfUserVM", bookingInfoReq.toString())

            user.value?.userId?.let { userId ->
                BookingsInfoReq(
                    userId = userId,
                    mpesaReceiptNumber = ""
                )
            }?.let { bookingInfoReq: BookingsInfoReq ->
                Log.d("getAllBokinInfoOfUserVM", bookingInfoReq.toString())

                RequestState.Success(repository.getAllBikeInfoByUserIdAsFlow(bookingsInfoReq = bookingInfoReq))

                Log.d("getAllBokinInfoOfUserVM", repository.getAllBikeInfoByUserIdAsFlow(bookingsInfoReq = bookingInfoReq).toString())

                    .onStart {
                        Log.d(
                            "getAllBokinInfoOfUserVM",
                            "Started Collecting  All BOOKING INFO As Flow"
                        )
                        Log.d("getAllBokinInfoOfUserVM", _bookingsInfo.value.toString())
                    }
                    .onEach {
                        Log.d("getAllBokinInfoOfUserVM", _bookingsInfo.value.toString())
                    }

                    .catch { ex ->
                        Log.d("getAllBokinInfoOfUserVM", "Exception Caught: ${ex.message}")
                    }



                    .onCompletion { cause: Throwable? ->
                        Log.d(
                            "getAllBokinInfoOfUserVM",
                            """Flow completed with message "${cause?.message}" """
                        )

                        Log.d("getAllBokinInfoOfUserVM", _bookingsInfo.value.toString())
                    }

                    .collect() { bookingsInfoRes: List<BookingsInfoRes> ->

                        _bookingsInfo.value = RequestState.Success(bookingsInfoRes)
                        Log.d("getAllBokinInfoOfUserVM", "Flow Completed Successfully")
                        Log.d("getAllBokinInfoOfUserVM", _searchedBikes.value.toString())
                    }

            }
*/







/*



            user.value?.userId?.let { userId ->
                BookingsInfoReq(
                    userId = userId,
                    mpesaReceiptNumber = ""
                )
            }?.let { bookingInfoReq: BookingsInfoReq ->
                Log.d("getAllBokinInfoOfUserVM", bookingInfoReq.toString())

                RequestState.Success(repository.getAllBikeInfoByUserIdAsFlow(bookingsInfoReq = bookingInfoReq))

                Log.d("getAllBokinInfoOfUserVM",
                    repository.getAllBikeInfoByUserIdAsFlow(bookingsInfoReq = bookingInfoReq)
                        .toString()
                )

        }
*/