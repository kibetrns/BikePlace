package me.ipsum_amet.bikeplace.data.db.remote

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import me.ipsum_amet.bikeplace.Util.BIKES
import me.ipsum_amet.bikeplace.Util.USERS
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.data.model.TYPE
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FayaBase @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage
) {
    fun getAllBikes(): List<Bike> {
        val listAll = mutableListOf<Bike>()

        db.collection(BIKES)
            .orderBy("postedAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents: QuerySnapshot ->
                for (document in documents) {
                    val bikeAll = document.toObject<Bike>()
                    listAll.add(bikeAll)
                }
                Log.d("fBGetAllBikes", listAll.toString())
            }
            .addOnFailureListener{ ex: Exception ->
                Log.w("main", "Error Fetching Bikes", ex)
            }
        return listAll
    }
    suspend fun getAllBikesAsFlow(): Flow<List<Bike>> {
        val listOfBikesAsFlow = mutableListOf<Bike>()
        return callbackFlow {
            val listener = db.collection(BIKES)
                .orderBy("modifiedAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { documents: QuerySnapshot ->
                    for (document in documents) {
                        val bikeAsFlow = document.toObject<Bike>()
                        listOfBikesAsFlow.add(bikeAsFlow)
                    }
                    trySend(listOfBikesAsFlow)
                    Log.d("fBGetAllBikesAsFlow", listOfBikesAsFlow.toString())
                }
                .addOnFailureListener{ ex: Exception ->
                    Log.w("main", "Error Fetching Bikes As Flow", ex)
                }
            awaitClose {
              /*
              Check this out later
               */
            }
        }
    }

    fun getBikeById(bikeId: String): Bike {
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

    suspend fun getBikeByIdAsFlow(bikeId: String): Flow<Bike> {
        return callbackFlow {
            db.collection(BIKES)
                .whereEqualTo("bikeId", bikeId)
                .get()
                .addOnSuccessListener { documents: QuerySnapshot ->
                    for ( document in documents ) {
                        trySend(document.toObject<Bike>())
                    }
                }
                .addOnFailureListener { ex: Exception ->
                    Log.w("main", "Error getting Bike by Name As Flow", ex)
                }
            awaitClose {

            }
        }
    }

    fun getBikesByName(query: String): List<Bike> {
        val listByName = mutableListOf<Bike>()

        db.collection(BIKES)
            .whereEqualTo("name", query)
            .get()
            .addOnSuccessListener { documents: QuerySnapshot ->
                for (document in documents) {
                    val bikeWithName = document.toObject<Bike>()
                    listByName.add(bikeWithName)
                }
                Log.d("fayaBaseBikeByName", listByName.toString())

            }
            .addOnFailureListener{ ex: Exception ->
                Log.w("main", "Error Fetching Bikes By Description", ex)
            }
        return listByName
    }
    
    suspend fun getBikesByNameAsFlow(query: String): Flow<List<Bike>> {
        val listOfBikeNamesAsFlow = mutableListOf<Bike>()

        return callbackFlow {
            db.collection(BIKES)
                .whereEqualTo("name", query)
                .get()
                .addOnSuccessListener { documents: QuerySnapshot ->
                    for ( document in documents ) {
                        val bikeNamesAsFLow = document.toObject<Bike>()
                        listOfBikeNamesAsFlow.add(bikeNamesAsFLow)
                    }
                    trySend(listOfBikeNamesAsFlow)
                    Log.d("fBGetAllBikeNameAsFlow", listOfBikeNamesAsFlow.toString())
                }
                .addOnFailureListener { ex: Exception ->
                    Log.w("main", "Error Fetching Bikes As Flow", ex)
                }
            awaitClose {  }
        }
    }

    suspend fun updateBikeAsFLow(bike: Bike): Flow<String> {
        return callbackFlow {
            bike.bikeId?.let {
                db.collection(BIKES)
                    .document(it)
                    .update(bike.toMap())
                    .addOnSuccessListener {
                        trySend("Bike Updated Successfully")
                    }
                    .addOnFailureListener {
                        trySend("Failure Updating Bike")
                    }
                awaitClose {  }
            }
        }
    }
     fun deleteBikeAsFlow(bikeId: String) {
         db.collection(BIKES).document(bikeId)
             .delete()
             .addOnSuccessListener {
                 Log.d("fBDeleteBikeAsFlow", "Bike Deletion Succeeded")
             }
             .addOnFailureListener {
                 Log.d("fBDeleteBikeAsFlow", "Bike Deletion Failed")
             }
     }


}