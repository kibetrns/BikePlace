package me.ipsum_amet.bikeplace.data.service

import me.ipsum_amet.bikeplace.data.dto.request.BookingsInfoReq
import me.ipsum_amet.bikeplace.data.dto.request.BookingsReq
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes

interface BookingInfoService {
    suspend fun makeBookingWithBPAPI(bookingsReq: BookingsReq)

    suspend fun getAllBookingInfoOfUserAsFlow(bookingsInfoReq: BookingsInfoReq): List<BookingsInfoRes>
}