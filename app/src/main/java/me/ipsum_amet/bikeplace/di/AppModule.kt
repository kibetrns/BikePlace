package me.ipsum_amet.bikeplace.di

import me.ipsum_amet.bikeplace.data.db.remote.FayaBase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Binds
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
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import me.ipsum_amet.bikeplace.BuildConfig
import me.ipsum_amet.bikeplace.data.repo.BikePlaceRepository
import me.ipsum_amet.bikeplace.data.service.MpesaService
import me.ipsum_amet.bikeplace.data.service.MpesaServiceImp
import javax.inject.Singleton

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
    auth: FirebaseAuth, db: FirebaseFirestore, storage: FirebaseStorage
  ) = FayaBase(auth = auth, db = db, storage = storage)

  @Provides
  fun provideBikePlaceRepo(fayaBase: FayaBase) = BikePlaceRepository(fayaBase = fayaBase)

  @Provides
  fun provideHttpClient(): HttpClient = HttpClient(CIO) {
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
     json(Json{
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
  fun provideMpesaService(client: HttpClient): MpesaService = MpesaServiceImp(service = client)

}