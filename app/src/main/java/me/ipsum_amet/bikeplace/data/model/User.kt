package me.ipsum_amet.bikeplace.data.model

import com.google.firebase.firestore.GeoPoint

data class User(
    var userId: String? = null,
    var fullName: String? = null,
    var phoneNumber: String? = null,
    var imageUrl: String? = null,
    var dropOffLocation: BikeDropOffAddress? =null,
    var orderedBikes: List<Bike>? = null,
    var currentUserLocation: GeoPoint? = null
) {
    fun toMap() = mapOf(
        "userId" to userId,
        "fullName" to fullName,
        "phoneNumber" to phoneNumber,
        "imageUrl" to imageUrl,
        "bikeDropOffAddress" to dropOffLocation,
        "orderBikes" to orderedBikes,
        "dropOffLocation" to dropOffLocation,
        "currentUserLocation" to currentUserLocation
    )
}

