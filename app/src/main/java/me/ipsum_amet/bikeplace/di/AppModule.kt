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
import me.ipsum_amet.bikeplace.data.repo.BikePlaceRepository

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


}