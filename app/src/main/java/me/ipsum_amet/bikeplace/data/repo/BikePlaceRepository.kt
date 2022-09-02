package me.ipsum_amet.bikeplace.data.repo

import android.util.Log
import me.ipsum_amet.bikeplace.data.db.remote.FayaBase
import dagger.hilt.android.scopes.ViewModelScoped
import me.ipsum_amet.bikeplace.data.model.Bike
import javax.inject.Inject

@ViewModelScoped
class BikePlaceRepository @Inject constructor(
    val fayaBase: FayaBase
) {

     fun getAllBikes(): List<Bike> { return fayaBase.getAllBikes() }

    fun getBikeById(bikeId: String): Bike { return fayaBase.getBikeById(bikeId) }


}