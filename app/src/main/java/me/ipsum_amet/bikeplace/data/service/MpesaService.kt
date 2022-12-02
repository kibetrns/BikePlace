package me.ipsum_amet.bikeplace.data.service

import android.util.Log
import io.ktor.client.plugins.auth.providers.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.ipsum_amet.bikeplace.data.dto.request.BookingsReq
import me.ipsum_amet.bikeplace.data.model.remote.AccessTokenResponse
import me.ipsum_amet.bikeplace.data.model.remote.STKPushRequest

interface MpesaService {
    suspend fun sendPush(sTKPushRequest: STKPushRequest)


    suspend fun getAccessToken(): AccessTokenResponse
    //fun loadTokens(): Job
}