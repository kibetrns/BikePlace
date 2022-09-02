package me.ipsum_amet.bikeplace.data.model

data class BikeDen(
    var bikeDenId: String? = null,
    var name: String? = null,
    var location: String? = null,
    var allBikes: List<Bike>? = null
) {
    fun toMap() = mapOf(
        "bikeDenId" to bikeDenId,
        "name" to name,
        "location" to location,
        "allBikes" to allBikes
    )


}