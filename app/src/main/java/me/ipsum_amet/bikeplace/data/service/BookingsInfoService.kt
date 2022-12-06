package me.ipsum_amet.bikeplace.data.service

import ipsum_amet.me.data.remote.dtos.requests.mpesa.EditReturnStatusBookingInfo
import kotlinx.coroutines.flow.Flow
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes

interface BookingsInfoService {
    suspend fun getAllBookingsInfoAsFlow(): Flow<List<BookingsInfoRes>>

    suspend fun getBookingsInfoByReceiptId(receiptId: String): BookingsInfoRes?

    suspend fun updateReturnStatusOfBookingInfo(editReturnStatusBookingInfo: EditReturnStatusBookingInfo)

}

