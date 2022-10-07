package me.ipsum_amet.bikeplace.data.model

import com.google.firebase.Timestamp

data class LeaseOrder(
    val leaseOrderId: String,
    val customer: String,
    val dateLeased: Timestamp,
    val leaseExpiry: Timestamp,
    val clientId: String,
    val pricing: Int
) {
    fun toMap() = mapOf(
        "leaseOrderId" to leaseOrderId,
        "customer" to customer,
        "dateLeased" to dateLeased,
        "leaseExpiry" to leaseExpiry,
        "clientId" to clientId,
        "pricing" to pricing
    )
}