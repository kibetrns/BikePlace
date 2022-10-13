package me.ipsum_amet.bikeplace.data.service

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.ipsum_amet.bikeplace.data.model.remote.AccessTokenResponse
import me.ipsum_amet.bikeplace.data.model.remote.STKPushRequest
import javax.inject.Inject

class MpesaServiceImp @Inject constructor(
    private val service: HttpClient
) : MpesaService {

    private val isAuthTokesAvailable = true
    private val receivedAccessToken: String? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private val bearerTokenStorage = mutableListOf<BearerTokens>()
    private val accessTokens = mutableListOf<AccessTokenResponse>()

    lateinit var tokenInfo: String
    lateinit var reloadedTokenInfo: BearerTokens

    suspend fun loadTokenInfo() = scope.launch {
        tokenInfo = getAccessToken().accessToken
        bearerTokenStorage.add(BearerTokens(accessToken = tokenInfo, refreshToken = tokenInfo))
    }


/*
    override fun loadTokens() = scope.launch(Dispatchers.IO) {
        accessTokens.add(getAccessToken())
        bearerTokenStorage.add(BearerTokens(
            accessToken = accessTokens.last().accessToken,
            refreshToken = accessTokens.last().accessToken
        ))
        Log.d("mLoadTokens", bearerTokenStorage.size.toString())
        Log.d("mLoadTokens", bearerTokenStorage.last().accessToken)

    }

 */



    @OptIn(ExperimentalSerializationApi::class)
    val service2 = HttpClient(CIO) {

        engine {
            maxConnectionsCount = 1000
            endpoint {
                maxConnectionsPerRoute = 100
                pipelineMaxSize = 20
                keepAliveTime = 5000
                connectTimeout = 5000
                connectAttempts = 5
            }
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.ANDROID
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
                explicitNulls = false
            })
        }

        install(Auth) {
            bearer {
                loadTokens {
                    bearerTokenStorage.last()
                }
                refreshTokens {
                    bearerTokenStorage.last()
                }
            }
        }


       defaultRequest {
           headers {
               append(HttpHeaders.ContentType, "application/json")
               //append(HttpHeaders.Authorization, "Bearer $tokenInfo")
           }
       }
    }


    override suspend fun sendPush(sTKPushRequest: STKPushRequest) {
        loadTokenInfo().join()
         val x = service2.post("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest") {
            setBody(body = sTKPushRequest)
        }
        Log.d("sendPush",
            """
                ->>>>>>>> ${x.headers} 
               ->>>>>>>> ${x.status}
               ->>>>>>>>  ${x.call}
                ->>>>>>>> $x
                ->>>>>>>> $tokenInfo
            """.trimIndent()
        )
    }

    override suspend fun getAccessToken(): AccessTokenResponse {
        return service.get("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials") {
        }.body()
    }
}