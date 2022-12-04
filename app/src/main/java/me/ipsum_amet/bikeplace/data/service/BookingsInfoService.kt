package me.ipsum_amet.bikeplace.data.service

import kotlinx.coroutines.flow.Flow
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes

interface BookingsInfoService {
    suspend fun getAllBookingsInfoAsFlow(): Flow<List<BookingsInfoRes>>

    suspend fun getBookingsInfoByReceiptId(receiptId: String): BookingsInfoRes?
}

