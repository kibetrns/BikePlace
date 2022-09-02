package me.ipsum_amet.bikeplace.data.db.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import me.ipsum_amet.bikeplace.Util.BIKES
import me.ipsum_amet.bikeplace.Util.USERS
import me.ipsum_amet.bikeplace.data.model.Bike
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FayaBase @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage
) {
    fun getAllBikes(): List<Bike> {
        val list = mutableListOf<Bike>()

        db.collection(BIKES)
            .orderBy("postedAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents: QuerySnapshot ->
                for (document in documents) {
                    val bike = document.toObject<Bike>()
                    list.add(bike)
                }
            }
            .addOnFailureListener{ ex: Exception ->
                Log.w("main", "Error Fetching Bikes", ex)
            }
        return list
    }

    fun getBikeById(bikeId: String) : Bike {
        var result = Bike()

        db.collection(BIKES).document(bikeId).get()
            .addOnSuccessListener {
                val bike = it.toObject<Bike>()

                if (bike != null) {
                    result  = bike

                    Log.d("fayabaseBikeId", bike.toString())
                }


            }
            .addOnFailureListener { ex: Exception ->
                Log.w("main", "Error Getting Bike By Id", ex)
            }
        return result

    }
}