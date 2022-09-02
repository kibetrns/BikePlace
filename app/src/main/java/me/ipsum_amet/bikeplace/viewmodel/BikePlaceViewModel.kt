package me.ipsum_amet.bikeplace.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.ipsum_amet.bikeplace.Util.*
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.data.model.CONDITION
import me.ipsum_amet.bikeplace.data.model.TYPE
import javax.inject.Inject
import me.ipsum_amet.bikeplace.data.model.User
import me.ipsum_amet.bikeplace.data.repo.BikePlaceRepository
import java.util.*

@HiltViewModel
class BikePlaceViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage,
    val repository: BikePlaceRepository
) : ViewModel() {


    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val isAdmin = mutableStateOf(false)
    val refreshPostProgress = mutableStateOf(false)

    private val user = mutableStateOf<User?>(null)

    val popUpNotification = mutableStateOf<Event<String>?>(null)

    var password: MutableState<String> = mutableStateOf("")
    var confirmedPassword: MutableState<String> = mutableStateOf("")

    var emailAddress: MutableState<String> = mutableStateOf("")
    var fullName: MutableState<String> = mutableStateOf("")
    var imageUrl = mutableStateOf("")
    var location = mutableStateOf("")
    var phoneNumber: MutableState<String> = mutableStateOf("")


    private val bike = mutableStateOf<Bike?>(null)

    var bikeName = mutableStateOf("")
    var bikeType = mutableStateOf(TYPE.BMX)
    var bikeCondition = mutableStateOf(CONDITION.AVERAGE)
    var bikeDescription = mutableStateOf("")
    var bikeImageUrl = mutableStateOf("")
    var bikePrice = mutableStateOf("")
    var isBooked = mutableStateOf(false)

    var imageData = mutableStateOf<Uri?>(null)


    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    private val _allBikes = MutableStateFlow<RequestState<List<Bike>>>(RequestState.Idle)
    val allBikes: StateFlow<RequestState<List<Bike>>> = _allBikes

    private val _searchedBikes = MutableStateFlow<RequestState<List<Bike>>>(RequestState.Idle)
    val searchedBikes: StateFlow<RequestState<List<Bike>>> = _searchedBikes

    private val _selectedBike: MutableStateFlow<Bike?> = MutableStateFlow(null)
    val selectedBike: StateFlow<Bike?> = _selectedBike

    val alreadyLoggedIn = mutableStateOf(false)


    init {
        auth.signOut()
        val currentUser = auth.currentUser
        signedIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(uid = it)
        }
    }


    fun registerUser() {
        viewModelScope.launch(Dispatchers.IO) {
            inProgress.value = true
            db.collection(USERS).whereEqualTo("fullName", fullName.value).get()
                .addOnSuccessListener { documents: QuerySnapshot ->
                    if (documents.size() > 0) {
                        handleException(customMessage = "Name already in use")
                        inProgress.value = false
                    } else {
                        auth.createUserWithEmailAndPassword(emailAddress.value, password.value)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    signedIn.value = true
                                    createInitialUserProfile()
                                } else {
                                    handleException(
                                        ex = task.exception,
                                        customMessage = "Registration Failed"
                                    )
                                }
                                inProgress.value = false
                            }
                            .addOnFailureListener { ex: Exception ->
                                ex.message?.let { errorMessage: String ->
                                    handleException(ex = ex, customMessage = errorMessage)
                                }
                            }
                    }
                }
                .addOnFailureListener { ex: Exception ->
                    ex.message?.let { errorMessage: String ->
                        handleException(ex = ex, customMessage = errorMessage)
                    }
                }
        }
    }

     fun handleException(ex: Exception? = null, customMessage: String = "") {
        ex?.printStackTrace()
        val errorMsg = ex?.localizedMessage ?: ""
        val msg = if (customMessage.isEmpty()) errorMsg else "$customMessage $errorMsg"
        popUpNotification.value = Event(msg)
    }

    private fun createInitialUserProfile() {

        val uid = auth.currentUser?.uid
        val user = User(
            userId = uid,
            fullName = fullName.value,
            phoneNumber = phoneNumber.value.toInt()
        )

        inProgress.value = true
        uid?.let {
            db.collection(USERS).document(uid).set(user)
                .addOnCompleteListener {
                    inProgress.value = false
                }
                .addOnSuccessListener {
                    handleException(customMessage = "Account Creation Successful")
                    inProgress.value = false
                }
                .addOnFailureListener { ex: Exception ->
                    handleException(ex = ex, customMessage = "Account Creation Failed")
                    inProgress.value = false
                }
        }
        inProgress.value = false
    }


    private fun UpdateUserProfile() {

        val uid = auth.currentUser?.uid
        val user = User(
            userId = uid,
            fullName = fullName.value,
            imageUrl = imageUrl.value,
            location = location.value,
            phoneNumber = phoneNumber.value.toInt()
        )
        uid?.let { uId ->
            inProgress.value = true
            db.collection(USERS).document(uId).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        it.reference.update(user.toMap())
                            .addOnSuccessListener {
                                this@BikePlaceViewModel.user.value = user
                                inProgress.value = false
                            }
                            .addOnFailureListener { ex: Exception ->
                                handleException(
                                    ex = ex,
                                    customMessage = "Unable to update users profile"
                                )
                                inProgress.value = false
                            }
                    }
                }
                .addOnFailureListener { ex ->
                    handleException(ex, "Unable to update users profile")
                    inProgress.value = false
                }
        }
    }

    /*   private fun createOrUpdateProfile(
           fullName: String? = null,
           phoneNumber: String? = null,
           imageUrl: String? = null,
           location: String? =null,
           orderedBikes: List<Bike>? = null
       ) {
           viewModelScope.launch(Dispatchers.IO) {
               val uid = auth.currentUser?.uid
               val user = User(
                   userId = uid,
                   fullName = fullName ?: user.value?.fullName,
                   phoneNumber = phoneNumber ?: user.value?.phoneNumber,
                   imageUrl = imageUrl ?: user.value?.imageUrl,
                   location = location ?: user.value?.location,
                   orderedBikes = orderedBikes ?: user.value?.orderedBikes
               )

               uid?.let { uid ->
                   inProgress.value = true
                   db.collection(USERS).document(uid).get()
                       .addOnSuccessListener {
                           if (it.exists()) {
                               it.reference.update(user.toMap())
                                   .addOnSuccessListener {
                                       this@BikePlaceViewModel.user.value = user
                                       inProgress.value = false
                                   }
                                   .addOnFailureListener {
                                       handleException(
                                           ex = it,
                                           customMessage = "Unable to update user "
                                       )
                                       inProgress.value = false
                                   }
                           } else {
                               db.collection(USERS).document(uid).set(user)
                               getUserData(uid)
                               inProgress.value = false
                           }
                       }
                       .addOnFailureListener { ex ->
                           handleException(ex = ex, customMessage = "Unable to create user")
                           inProgress.value = false
                       }
               }
           }
       }

     */

    private fun getUserData(uid: String) {
        inProgress.value = true
        db.collection(USERS).document(uid).get()
            .addOnSuccessListener {
                val user1 = it.toObject<User>()
                user.value = user1
                inProgress.value = false
                popUpNotification.value = Event("User Data Retrieved Successfully")

            }
            .addOnFailureListener { ex: Exception ->
                handleException(ex = ex, "Unable to retrieve user data")
                inProgress.value = false
            }
        inProgress.value = false
    }

    fun getSelectedBike(bikeId: String) {
            try {
                _selectedBike.value = repository.getBikeById(bikeId)
                Log.d("bikeIdResult", _selectedBike.value.toString())
            } catch (ex: Exception) {
                Log.w("main", "Error While Getting Selected Bike", ex)
            }
    }

    fun loginUser(emailAddress: String, password: String) {
        inProgress.value = true
        auth.signInWithEmailAndPassword(emailAddress, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signedIn.value = true
                    inProgress.value = false
                    auth.currentUser?.uid?.let { uid ->
                        handleException(customMessage = "Successfully logged In")
                        getUserData(uid = uid)

                    }
                } else {
                    handleException(ex = task.exception, customMessage = "Login Failed")
                    inProgress.value = false
                }
            }
            .addOnFailureListener { ex: Exception ->
                handleException(ex = ex, customMessage = "Login Failed")
                inProgress.value = false
            }

    }

    private fun uploadImage(uri: Uri, onSuccess: (Uri) -> Unit)   {
        inProgress.value = true
        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageRef.child("images/$uuid")
        val uploadTask = imageRef.putFile(uri)
        uploadTask
            .addOnSuccessListener {
                val result = it.metadata?.reference?.downloadUrl
                result?.addOnSuccessListener(onSuccess)
            }
            .addOnFailureListener { ex: Exception ->
                handleException(ex = ex)
                inProgress.value = false
            }
    }

    fun uploadBikeImage(uri: Uri) {
        uploadImage(uri) {
            addBike(imageUri = it)
        }
    }

    private fun refreshBikes() {



    }

   fun getAllBikes() {
       _allBikes.value = RequestState.Loading
       try {
           viewModelScope.launch(Dispatchers.IO) {
               _allBikes.value = RequestState.Success(repository.getAllBikes())
           }
       } catch (ex: Exception) {
           _allBikes.value = RequestState.Error(ex)
       }
   }



    fun searchDB(searchQuery: String) {

    }

    fun updateOrAddBike(
        bikeId: String? = null,
        booked: Boolean? = null,
        condition: CONDITION? = null,
        description: String? = null,
        imageUrl: String? = null,
        name: String? = null,
        price: Double? = null,
        type: TYPE? = null,
        userId: String? = null
    ) {
        val uId = auth.currentUser?.uid

        val bike = Bike(
            bikeId = bikeId,
            isBooked = booked ?: bike.value?.isBooked,
            condition = condition ?: bikeCondition.value,
            description = description ?: bikeDescription.value,
            imageUrl = imageUrl ?: imageData.value.toString(),
            name = name ?: bikeName.value,
            price = price ?: bikePrice.value.toDouble(),
            type = type ?: bikeType.value,
            userId = uId
        )
        inProgress.value = true

        bikeId?.let { bikId ->
            inProgress.value = true
            db.collection(BIKES).document(bikeId).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        it.reference.update(bike.toMap())
                            .addOnSuccessListener {
                                this@BikePlaceViewModel.bike.value = bike
                                inProgress.value = false
                            }
                            .addOnFailureListener {
                                handleException(
                                    ex = it,
                                    customMessage = "Unable to update user "
                                )
                                inProgress.value = false
                            }
                    } else {
                        db.collection(BIKES).document(UUID.randomUUID().toString()).set(bike)
                        inProgress.value = false
                    }
                }
                .addOnFailureListener { ex ->
                    handleException(ex = ex, customMessage = "Unable to create user")
                    inProgress.value = false
                }
        }
    }

    fun addBike(imageUri: Uri? = null) {



        viewModelScope.launch(Dispatchers.IO) {
            val bikeId = UUID.randomUUID().toString()
            val userId = auth.currentUser?.uid

            val bike = Bike(
                bikeId = bikeId,
                userId = userId,
                isBooked = isBooked.value,
                name = bikeName.value,
                price = bikePrice.value.toDouble(),
                imageUrl = imageUri.toString(),
                description = bikeDescription.value,
                postedAt = Timestamp.now(),
                condition = bikeCondition.value,
                type = bikeType.value
            )


            inProgress.value = true
            db.collection(BIKES).document(bikeId).set(bike)
                .addOnCompleteListener {
                    inProgress.value = false
                }
                .addOnSuccessListener {
                    handleException(customMessage = "Bike details added successfully")
                }
                .addOnFailureListener { ex: Exception ->
                    handleException(
                        ex = ex,
                        customMessage = "Unable to add bike details"
                    )
                }
        }
    }

    fun handleDatabaseAction(action: Action) {

        when (action) {
            Action.ADD -> {
                //addBike(imageUri = imageData.value)
                imageData.value?.let { uploadBikeImage(uri = it) }
            }
            Action.UPDATE -> {

            }
            Action.DELETE -> {

            }
            Action.DELETE_ALL -> {

            }
            Action.UNDO -> {

            }

            else -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }


    suspend fun updateBikeFields(selectedBike: Bike?) {

        if ( selectedBike == null) {
            bikeName.value = ""
            bikeType.value = TYPE.BMX
            bikeCondition.value = CONDITION.AVERAGE
            bikeDescription.value = ""
            bikeImageUrl.value = ""
            bikePrice.value = ""
        } else {
            bikeName.value = selectedBike.name.toString()
            bikeType.value = selectedBike.type!!
            bikeCondition.value = selectedBike.condition!!
            bikeDescription.value = selectedBike.description.toString()
            bikeImageUrl.value = selectedBike.imageUrl.toString()
            bikePrice.value = selectedBike.price.toString()
        }
    }

    fun validateBikeContentFields(): Boolean {

        return bikeName.value.isNotEmpty() && bikePrice.value.isNotEmpty() && bikeDescription.value.isNotEmpty()
    }

    fun validateRegistrationFields(): Boolean {

        return fullName.value.isNotEmpty() && emailAddress.value.isNotEmpty() && phoneNumber.value.toString()
            .isNotEmpty() && password.value.isNotEmpty() && confirmedPassword.value.isNotEmpty()
    }

    fun validateSamePassword(): Boolean {

        return confirmedPassword.value == password.value
    }

    fun validateSignInFields(): Boolean {
        return emailAddress.value.isNotEmpty() && password.value.isNotEmpty()
    }




}


