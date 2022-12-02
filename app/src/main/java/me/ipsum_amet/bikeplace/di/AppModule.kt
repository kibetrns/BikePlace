package me.ipsum_amet.bikeplace.di

import me.ipsum_amet.bikeplace.data.db.remote.FayaBase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import me.ipsum_amet.bikeplace.BuildConfig
import me.ipsum_amet.bikeplace.data.repo.BikePlaceRepository
import me.ipsum_amet.bikeplace.data.service.BookingInfoService
import me.ipsum_amet.bikeplace.data.service.BookingInfoServiceImpl
import me.ipsum_amet.bikeplace.data.service.MpesaService
import me.ipsum_amet.bikeplace.data.service.MpesaServiceImp
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
  @Provides
  fun provideAuthentication(): FirebaseAuth = Firebase.auth

  @Provides
  fun provideFireStore(): FirebaseFirestore = Firebase.firestore

  @Provides
  fun provideStorage(): FirebaseStorage = Firebase.storage

  @Provides
  fun provideDBRemoteFayaBase(
    auth: FirebaseAuth,
    db: FirebaseFirestore,
    storage: FirebaseStorage
  ) = FayaBase(auth = auth, db = db, storage = storage)

  @Provides
  fun provideBikePlaceRepo(
    fayaBase: FayaBase,
    mpesaService: MpesaService,
    bookingInfoService: BookingInfoService
  ) = BikePlaceRepository(
    fayaBase = fayaBase,
    mpesaService = mpesaService,
    bookingInfoService = bookingInfoService
  )

  @Provides
  @Named("mpesa")
  fun provideMpesaAuthHttpClient(): HttpClient = HttpClient(CIO) {
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
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
      })
    }
    install(Auth) {
      basic {
        sendWithoutRequest {
          true
        }
        credentials {
          BasicAuthCredentials(
            username = BuildConfig.DARAJA_CONSUMER_KEY,
            password = BuildConfig.DARAJA_CONSUMER_SECRET_KEY,
          )
        }
      }
    }
  }

  @Provides
  @Named("default")
  fun provideDefaultHttpClient(): HttpClient = HttpClient(CIO) {

    install(Logging) {
      level = LogLevel.ALL
      logger = Logger.ANDROID
    }

    install(ContentNegotiation) {
      json(Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
      })
    }

    defaultRequest {
      headers {
        append(HttpHeaders.ContentType, "application/json")
      }
    }

  }

  @Provides
  fun provideMpesaService(
    @Named("mpesa") mpesaAuthService: HttpClient,
    @Named("default") defaultBPService: HttpClient,
  ): MpesaService = MpesaServiceImp(mpesaAuthService = mpesaAuthService,  defaultBPService = defaultBPService )

  @Provides
  fun provideBookingInfoService(
    @Named("default") defaultBPService: HttpClient
  ): BookingInfoService = BookingInfoServiceImpl(defaultBPService = defaultBPService)


}




