package me.ipsum_amet.bikeplace.di

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
import io.realm.kotlin.Realm
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import me.ipsum_amet.bikeplace.Util.APP_ID
import me.ipsum_amet.bikeplace.Util.REALM_DB_NAME
import me.ipsum_amet.bikeplace.data.db.RealmDB
import me.ipsum_amet.bikeplace.data.model.User
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
}