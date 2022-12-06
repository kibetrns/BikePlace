package me.ipsum_amet.bikeplace.data.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import ipsum_amet.me.data.remote.dtos.requests.mpesa.EditReturnStatusBookingInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes
import me.ipsum_amet.bikeplace.util.BPAPIEndpoints
import javax.inject.Inject
import javax.inject.Named

class BookingsInfoServiceImpl@Inject constructor(
    @Named("default") private val defaultBPService: HttpClient
) : BookingsInfoService {
    override suspend fun getAllBookingsInfoAsFlow(): Flow<List<BookingsInfoRes>> = flow {
        emit( defaultBPService.get(BPAPIEndpoints.AllBookingsInfo.url) {
        }.body() )
    }

    override suspend fun updateReturnStatusOfBookingInfo(editReturnStatusBookingInfo: EditReturnStatusBookingInfo) {
        defaultBPService.put(BPAPIEndpoints.UpdateReturnStatusOfBookingInfo.url) {
            setBody(body = editReturnStatusBookingInfo)
        }
    }

    override suspend fun getBookingsInfoByReceiptId(receiptId: String): BookingsInfoRes? {

        return defaultBPService.get(BPAPIEndpoints.BookingInfoByReceiptId.url) {
            url {
                parameters.append("receiptId", receiptId)
            }
        }.body()
    }

}