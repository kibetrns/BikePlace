package me.ipsum_amet.bikeplace.data.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.reflect.*
import me.ipsum_amet.bikeplace.data.dto.request.BookingsInfoReq
import me.ipsum_amet.bikeplace.data.dto.request.BookingsReq
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes
import me.ipsum_amet.bikeplace.util.BPAPIEndpoints
import javax.inject.Inject
import javax.inject.Named

class BookingInfoServiceImpl @Inject constructor(
    @Named("default") private val defaultBPService: HttpClient
) : BookingInfoService {

    override suspend fun makeBookingWithBPAPI(bookingsReq: BookingsReq) {
        defaultBPService.post(BPAPIEndpoints.MpesaExpress.url) {
            setBody(body = bookingsReq)
        }
    }

    override suspend fun getAllBookingInfoOfUserAsFlow(bookingsInfoReq: BookingsInfoReq): List<BookingsInfoRes> {
        return defaultBPService.get(BPAPIEndpoints.UserBookingInfoByUserId.url) {
            //setBody(body = bookingsInfoReq)
            url {
                parameters.append("userId", bookingsInfoReq.userId)
            }
        }.body()
    }
}


