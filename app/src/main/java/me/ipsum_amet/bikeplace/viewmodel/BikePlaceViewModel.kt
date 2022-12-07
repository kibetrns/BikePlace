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
import ipsum_amet.me.data.remote.dtos.requests.mpesa.EditReturnStatusBookingInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes
import me.ipsum_amet.bikeplace.data.model.*
import me.ipsum_amet.bikeplace.util.*
import javax.inject.Inject
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

    val user = mutableStateOf<User?>(null)

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
    var bikePrice = mutableStateOf("0.00")
    var isBooked = mutableStateOf(false)
    var bikeGear = mutableStateOf(Gears.ABOVE_TWENTY)
    var bikeGroupSetMaterial = mutableStateOf(GroupSetMaterial.TITANIUM)
    var bikeHandleBars = mutableStateOf(HandleBars.NARROW)
    var bikeSuspension = mutableStateOf(Suspension.HARD_TAIL)

    var imageData = mutableStateOf<Uri?>(null)


    var leaseActivationTitle = mutableStateOf("Start Range")
    var leaseActivationDateInput = mutableStateOf(getDate())
    var leaseActivationTimeInput = mutableStateOf(getTime())
    var leaseExpiryTitle = mutableStateOf("End Range")
    var leaseExpiryDateInput = mutableStateOf(getDate())
    var leaseExpiryTimeInput = mutableStateOf(getTime())

    var lineGraphHeaderTitle = mutableStateOf(Pair<Any, Any>("", 1))


    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")


    val getCategoryState: MutableState<GetCategoryState> =
        mutableStateOf(GetCategoryState.TRIGGEREDNOT)


    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    private val _allBikes = MutableStateFlow<RequestState<List<Bike>>>(RequestState.Idle)
    val allBikes: StateFlow<RequestState<List<Bike>>> = _allBikes

    private val _searchedBikes = MutableStateFlow<RequestState<List<Bike>>>(RequestState.Idle)
    val searchedBikes: StateFlow<RequestState<List<Bike>>> = _searchedBikes

    private val _selectedBike: MutableStateFlow<Bike?> = MutableStateFlow(null)
    val selectedBike: StateFlow<Bike?> = _selectedBike

    private val _topBikeChoices: MutableStateFlow<RequestState<List<Bike>>> =
        MutableStateFlow(RequestState.Idle)
    val topBikeChoices: StateFlow<RequestState<List<Bike>>> = _topBikeChoices

    private val _allBikeCategories: MutableStateFlow<RequestState<List<Bike>>> =
        MutableStateFlow(RequestState.Idle)
    val allBikeCategories: StateFlow<RequestState<List<Bike>>> = _allBikeCategories

    private val _allBikesByCategory: MutableStateFlow<RequestState<List<Bike>>> =
        MutableStateFlow(RequestState.Idle)
    val allBikesByCategory: StateFlow<RequestState<List<Bike>>> = _allBikesByCategory

    val alreadyLoggedIn = mutableStateOf(false)

    var hoursToLease = mutableStateOf("3")


    private val _allBookingsInfo: MutableStateFlow<RequestState<List<BookingsInfoRes>>> =
        MutableStateFlow(RequestState.Idle)
    val allBookingsInfoRes: StateFlow<RequestState<List<BookingsInfoRes>>> = _allBookingsInfo

    private val _leasedBookingsInfo: MutableStateFlow<RequestState<List<BookingsInfoRes>>> =
        MutableStateFlow(RequestState.Idle)
    val leasedBookingsInfo: StateFlow<RequestState<List<BookingsInfoRes>>> = _leasedBookingsInfo

    private val _returnedBookingsInfo: MutableStateFlow<RequestState<List<BookingsInfoRes>>> =
        MutableStateFlow(RequestState.Idle)
    val returnedBookingsInfo: StateFlow<RequestState<List<BookingsInfoRes>>> = _returnedBookingsInfo


    val searchReceiptNumber: MutableState<String> = mutableStateOf("")

    private val _searchedBookingInfo: MutableStateFlow<RequestState<BookingsInfoRes?>> =
        MutableStateFlow(RequestState.Idle)
    val searchedBookingInfo: StateFlow<RequestState<BookingsInfoRes?>> = _searchedBookingInfo

    val homeAdminSearchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)

    val selectedBookingInfoItem: MutableState<BookingInfo?> = mutableStateOf(null)

    val bookingInfoReturnStatus: MutableState<BookingInfo?> = mutableStateOf(null)

    private val _fetchedBookingInfoResToBeUsedForStats: MutableStateFlow<List<BookingsInfoRes>?> =
        MutableStateFlow(null)
    val fetchedBookingInfoResToBeUsedForStats = _fetchedBookingInfoResToBeUsedForStats


    private var _totalAccumulatedAmount = MutableStateFlow(0.0)
    val totalAccumulatedAmount = _totalAccumulatedAmount

    private var _totalAccumulatedAmountBMX = MutableStateFlow(0.0)
    val totalAccumulatedAmountBMX = _totalAccumulatedAmountBMX

    private var _totalAccumulatedAmountCRUISER = MutableStateFlow(0.0)
    val totalAccumulatedAmountCRUISER = _totalAccumulatedAmountCRUISER

    private var _totalAccumulatedAmountCYCLOCROSSBIKE = MutableStateFlow(0.0)
    val totalAccumulatedAmountCYCLOCROSSBIKE = _totalAccumulatedAmountCYCLOCROSSBIKE

    private var _totalAccumulatedAmountELECTRICBIKE = MutableStateFlow(0.0)
    val totalAccumulatedAmountELECTRICBIKE = _totalAccumulatedAmountELECTRICBIKE

    private var _totalAccumulatedAmountFOLDINGBIKE = MutableStateFlow(0.0)
    val totalAccumulatedAmountFOLDINGBIKE = _totalAccumulatedAmountFOLDINGBIKE

    private var _totalAccumulatedAmountHYBRIDBIKE = MutableStateFlow(0.0)
    val totalAccumulatedAmountHYBRIDBIKE = _totalAccumulatedAmountHYBRIDBIKE

    private var _totalAccumulatedAmountMOUNTAINBIKE = MutableStateFlow(0.0)
    val totalAccumulatedAmountMOUNTAINBIKE = _totalAccumulatedAmountMOUNTAINBIKE

    private var _totalAccumulatedAmountRECUMBENTBIKE = MutableStateFlow(0.0)
    val totalAccumulatedAmountRECUMBENTBIKE = _totalAccumulatedAmountRECUMBENTBIKE

    private var _totalAccumulatedAmountROADRIDE = MutableStateFlow(0.0)
    val totalAccumulatedAmountROADRIDE = _totalAccumulatedAmountROADRIDE

    private var _totalAccumulatedAmountTOURINGBIKE = MutableStateFlow(0.0)
    val totalAccumulatedAmountTOURINGBIKE = _totalAccumulatedAmountTOURINGBIKE

    private var _totalAccumulatedAmountTRACKBIKE = MutableStateFlow(0.0)
    val totalAccumulatedAmountTRACKBIKE = _totalAccumulatedAmountTRACKBIKE


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
            try {
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
            } catch (ex: Exception) {
                ex.message?.let { handleException(ex = ex, customMessage = it) }
            }
            inProgress.value = false
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
            phoneNumber = phoneNumber.value
        )
        try {
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
        } catch (ex: Exception) {
            ex.message?.let { handleException(ex = ex, customMessage = it) }
            inProgress.value = false
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
            phoneNumber = phoneNumber.value
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
        try {
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
        } catch (ex: Exception) {
            ex.message?.let { handleException(ex = ex, customMessage = it) }
        }
        inProgress.value = false
    }

    fun getSelectedBike(bikeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBikeByIdAsFlow(bikeId)
                .onStart {
                    Log.d("getSelectedBikeVM", "Started Collecting Selected Bike As Flow")
                    Log.d("getSelectedBikeVM", _selectedBike.value.toString())
                }
                .catch { ex ->
                    Log.w("getSelectedBikeVM", "Exception Caught: ${ex.message}")
                }
                .onCompletion { cause: Throwable? ->
                    if (cause != null)
                        Log.d(
                            "getSelectedBikeVM",
                            """Flow completed with message "${cause.message}" """
                        )
                    else
                        Log.d("getSelectedBikeVM", _selectedBike.value.toString())
                }
                .collect() {
                    _selectedBike.value = it
                    bike.value = it
                    Log.d("getSelectedBikeVM", "Flow Completed Successfully")
                    Log.d("getSelectedBikeVM", _selectedBike.value.toString())
                }

        }
    }

    /*
    fun getSelectedBike(bikeId: String) {
            try {
                _selectedBike.value = repository.getBikeById(bikeId)
                Log.d("bikeIdResult", _selectedBike.value.toString())
            } catch (ex: Exception) {
                Log.w("main", "Error While Getting Selected Bike", ex)
            }
    }

     */

    fun loginUser(emailAddress: String, password: String) {
        inProgress.value = true
        try {
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
        } catch (ex: Exception) {
            ex.message?.let { handleException(ex = ex, customMessage = it) }
        }
        inProgress.value = false
    }

    private fun uploadImage(uri: Uri, onSuccess: (Uri) -> Unit) {
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

    fun updateBikeImage(uri: Uri) {
        try {
            uploadImage(uri) {
                updateOrAddBike(
                    bikeId = _selectedBike.value?.bikeId,
                    isBooked = _selectedBike.value?.isBooked,
                    imageUrl = it.toString(),

                    )
            }
        } catch (ex: Exception) {
            Log.w("main", "Error Updating Bike Details")
        }
    }

    private fun refreshBikes() {


    }

    fun calculateTotalCheckoutPrice(): Double {
        val result = mutableStateOf(0.0)
        try {
            result.value = _selectedBike.value?.price!! * hoursToLease.value.toDouble()
        } catch (ex: Exception) {
            Log.w("main", "${ex.message}")
        }
        return result.value
    }

    fun getAllBikes() {
        _allBikes.value = RequestState.Loading
        Log.d("getAllBikesVM", _allBikes.value.toString())
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllBikesAsFlow()
                .onStart {
                    Log.d("getAllBikesVM", "Started Collecting All Bikes As Flow")
                    Log.d("getAllBikesVM", _allBikes.value.toString())
                }
                .onEach {
                    Log.d("getAllBikesVM", it.toString())
                    Log.d("getAllBikesVM", _allBikes.value.toString())
                }
                .catch { ex ->
                    Log.d("getAllBikesVM", "Exception Caught: ${ex.message}")
                }
                .onCompletion { cause: Throwable? ->
                    if (cause != null)
                        Log.d(
                            "getAllBikesVM",
                            """Flow completed with message "${cause.message}" """
                        )
                    else
                        Log.d("getAllBikesVM", _allBikes.value.toString())
                }
                .collect() {
                    _allBikes.value = RequestState.Success(it)
                    Log.d("getAllBikesVM", "Flow Completed Successfully")
                    Log.d("getAllBikesVM", _allBikes.value.toString())
                }
        }

    }

    fun calculateTotalAmountMadeByBikeCategory() {
        try {

            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    /*
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.distinctBy { it.bikeType }
                    }

                         */
                    .collect { bookingsInfoResList ->
                        _totalAccumulatedAmount.value = bookingsInfoResList.sumOf {
                            it.amount
                        }
                        Log.d("calulateAmountByCat_VM", _totalAccumulatedAmount.value.toString())
                    }
            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByCat_VM", it) }
        }
    }

    fun calculateTotalAmountMadeForBMXCategory() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    .mapNotNull { bookingsInfoResList ->
                        bookingsInfoResList.filter {
                            it.bikeType == TYPE.BMX
                        }
                    }
                    .catch { ex ->
                        Log.e("calAmountByMade_BMX_VM", "Exception Caught: ${ex.message}")
                    }
                    .collect() { bookingsInfoResList ->
                        _totalAccumulatedAmountBMX.value = bookingsInfoResList.sumOf {
                            it.amount
                        }
                    }
                Log.d("calAmountByMade_BMX_VM", _totalAccumulatedAmountBMX.value.toString())

            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByBMX_VM", it) }
        }
    }

    fun calculateTotalAmountMadeForCRUISERCategory() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.distinctBy { it.bikeType }
                    }
                    .catch { ex ->
                        Log.e("calAmountByMade_CRUISER_VM", "Exception Caught: ${ex.message}")
                    }
                    .collect() { bookingsInfoResList ->
                        _totalAccumulatedAmountCRUISER.value = bookingsInfoResList.sumOf {
                            it.amount
                        }
                    }
                Log.d("calAmountByMade_CRUISER_VM", _totalAccumulatedAmountCRUISER.value.toString())

            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByCrui_VM", it) }
        }
    }

    fun calculateTotalAmountMadeForCYCLOCROSS_BIKECategory() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    /*
                    .mapNotNull { bookingsInfoResList ->
                        bookingsInfoResList.filter {
                            it.bikeType == TYPE.CYCLOCROSS_BIKE
                        }
                    }

                         */
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.distinctBy { it.bikeType }
                    }
                    .catch { ex ->
                        Log.e(
                            "calAmountByMade_CYCLOCROSS_BIKE_VM",
                            "Exception Caught: ${ex.message}"
                        )
                    }
                    .collect() { bookingsInfoResList ->
                        _totalAccumulatedAmountCYCLOCROSSBIKE.value = bookingsInfoResList.sumOf {
                            it.amount
                        }
                    }
                Log.d(
                    "calAmountByMade_CYCLOCROSS_BIKE_VM",
                    _totalAccumulatedAmountCYCLOCROSSBIKE.value.toString()
                )

            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByCyc_VM", it) }
        }
    }

    fun calculateTotalAmountMadeForELECTRIC_BIKECategory() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.distinctBy { it.bikeType }
                    }
                    .catch { ex ->
                        Log.e(
                            "calAmountByMade_CYCLOCROSS_ELECTRIC_BIKE_VM",
                            "Exception Caught: ${ex.message}"
                        )
                    }
                    .collect() { bookingsInfoResList ->
                        _totalAccumulatedAmountELECTRICBIKE.value = bookingsInfoResList.sumOf {
                            it.amount
                        }
                    }
                Log.d(
                    "calAmountByMade_CYCLOCROSS_ELECTRIC_BIKE_VM",
                    _totalAccumulatedAmountELECTRICBIKE.value.toString()
                )

            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByElec_VM", it) }
        }
    }

    fun calculateTotalAmountMadeForFOLDING_BIKECategory() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.distinctBy { it.bikeType }
                    }
                    .catch { ex ->
                        Log.e("calAmountByMade_FOLDING_BIKE_VM", "Exception Caught: ${ex.message}")
                    }
                    .collect() { bookingsInfoResList ->
                        _totalAccumulatedAmountFOLDINGBIKE.value = bookingsInfoResList.sumOf {
                            it.amount
                        }
                    }
                Log.d(
                    "calAmountByMade_FOLDING_BIKE_VM",
                    _totalAccumulatedAmountFOLDINGBIKE.value.toString()
                )

            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByFOL_VM", it) }
        }
    }

    fun calculateTotalAmountMadeForHYBRID_BIKECategory() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.distinctBy { it.bikeType }
                    }
                    .catch { ex ->
                        Log.e("calAmountByMade_HYBRID_BIKE_VM", "Exception Caught: ${ex.message}")
                    }
                    .collect() { bookingsInfoResList ->
                        _totalAccumulatedAmountHYBRIDBIKE.value = bookingsInfoResList.sumOf {
                            it.amount
                        }
                    }
                Log.d(
                    "calAmountByMade_HYBRID_BIKE_VM",
                    _totalAccumulatedAmountHYBRIDBIKE.value.toString()
                )

            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByHyB_VM", it) }
        }
    }


    fun calculateTotalAmountMadeForMOUNTAIN_BIKECategory() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.distinctBy { it.bikeType == TYPE.MOUNTAIN_BIKE }
                        /*
                        bikes.takeWhile {
                            it.bikeType == TYPE.MOUNTAIN_BIKE
                        }
                         */
                    }
                    .catch { ex ->
                        Log.e("calAmountByMade_MOUNTAIN_BIKE_VM", "Exception Caught: ${ex.message}")
                    }
                    /*
                    .filter {
                        it.keys == setOf(TYPE.MOUNTAIN_BIKE)
                    }
                    .collect() {
                        _totalAccumulatedAmountMOUNTAINBIKE.value = it.values.sumOf {
                            it.sumOf {
                                it.amount
                            }
                        }

                         */
                    .collect() {
                        _totalAccumulatedAmountMOUNTAINBIKE.value =  it.sumOf {
                            it.amount
                        }
                    }
            }
            Log.d(
                "calAmountByMade_MOUNTAIN_BIKE_VM",
                _totalAccumulatedAmountMOUNTAINBIKE.value.toString()
            )
    } catch (ex: Exception) {
        ex.localizedMessage?.toString()?.let { Log.e("calcAByMou_VM", it) }
    }
}


    fun calculateTotalAmountMadeForEachBikeType() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.groupBy { it.bikeType }
                    }
                    .catch {  ex ->
                        Log.e("calAmountByMade_ForEachBikeTypes_VM", "Exception Caught: ${ex.message}")
                    }
                    .collect() { bookingsInfoResList ->
                        bookingsInfoResList.mapNotNull {
                            when(it.key) {
                                TYPE.TRACK_BIKE ->  _totalAccumulatedAmountMOUNTAINBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                                TYPE.BMX -> _totalAccumulatedAmountBMX.value = it.value.sumOf {
                                    it.amount
                                }
                                TYPE.CRUISER -> _totalAccumulatedAmountCRUISER.value = it.value.sumOf {
                                    it.amount
                                }
                                TYPE.CYCLOCROSS_BIKE -> _totalAccumulatedAmountCYCLOCROSSBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                                TYPE.ELECTRIC_BIKE -> _totalAccumulatedAmountELECTRICBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                                TYPE.FOLDING_BIKE -> _totalAccumulatedAmountFOLDINGBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                                TYPE.HYBRID_BIKE -> _totalAccumulatedAmountHYBRIDBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                                TYPE.MOUNTAIN_BIKE -> _totalAccumulatedAmountMOUNTAINBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                                TYPE.RECUMBENT_BIKE -> _totalAccumulatedAmountRECUMBENTBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                                TYPE.ROAD_RIDE -> _totalAccumulatedAmountROADRIDE.value = it.value.sumOf {
                                    it.amount
                                }
                                TYPE.TOURING_BIKE -> _totalAccumulatedAmountTOURINGBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                            }
                            /*
                            if (it.key == TYPE.MOUNTAIN_BIKE) {
                                _totalAccumulatedAmountMOUNTAINBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                            } else if(it.key == TYPE.BMX) {
                                _totalAccumulatedAmountBMX.value = it.value.sumOf {
                                    it.amount
                                }
                            } else if (it.key == TYPE.CRUISER) {
                                _totalAccumulatedAmountCRUISER.value = it.value.sumOf {
                                    it.amount
                                }
                            } else if (it.key == TYPE.CYCLOCROSS_BIKE) {
                                _totalAccumulatedAmountCYCLOCROSSBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                            } else if (it.key == TYPE.ELECTRIC_BIKE) {
                                _totalAccumulatedAmountELECTRICBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                            } else if (it.key == TYPE.FOLDING_BIKE) {
                                _totalAccumulatedAmountFOLDINGBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                            } else if (it.key == TYPE.HYBRID_BIKE) {
                                _totalAccumulatedAmountHYBRIDBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                            } else if (it.key == TYPE.RECUMBENT_BIKE) {
                                _totalAccumulatedAmountRECUMBENTBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                            } else if (it.key == TYPE.ROAD_RIDE) {
                                _totalAccumulatedAmountROADRIDE.value = it.value.sumOf {
                                    it.amount
                                }
                            } else if (it.key == TYPE.TOURING_BIKE) {
                                _totalAccumulatedAmountTOURINGBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                            } else if (it.key == TYPE.TRACK_BIKE) {
                                _totalAccumulatedAmountTRACKBIKE.value = it.value.sumOf {
                                    it.amount
                                }
                            }
                            */


                        }
                    }
            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByMou_VM", it) }
        }

    }

    fun calculateTotalAmountPayedForAllBikesCOmbined() {
      _totalAccumulatedAmount.value =   _totalAccumulatedAmountBMX.value + totalAccumulatedAmountCRUISER.value +
              totalAccumulatedAmountCYCLOCROSSBIKE.value + totalAccumulatedAmountELECTRICBIKE.value +
              totalAccumulatedAmountFOLDINGBIKE.value + totalAccumulatedAmountHYBRIDBIKE.value +
              totalAccumulatedAmountMOUNTAINBIKE.value + totalAccumulatedAmountRECUMBENTBIKE.value +
              totalAccumulatedAmountROADRIDE.value + totalAccumulatedAmountTOURINGBIKE.value +
              totalAccumulatedAmountTRACKBIKE.value

    }

    fun calculateTotalAmountMadeForRECUMBENT_BIKECategory() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.distinctBy { it.bikeType }
                    }
                    .catch {  ex ->
                        Log.e("calAmountByMade_RECUMBENT_BIKE_VM", "Exception Caught: ${ex.message}")
                    }
                    .collect() { bookingsInfoResList ->
                        _totalAccumulatedAmountRECUMBENTBIKE.value = bookingsInfoResList.sumOf {
                            it.amount
                        }
                    }
                Log.d(
                    "calAmountByMade_RECUMBENT_BIKE_VM",
                    _totalAccumulatedAmountRECUMBENTBIKE.value.toString()
                )

            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByRec_VM", it) }
        }
    }

    fun calculateTotalAmountMadeForROAD_RIDECategory() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.distinctBy { it.bikeType }
                    }
                    .catch {  ex ->
                        Log.e("calAmountByMade_ROAD_RIDE_VM", "Exception Caught: ${ex.message}")
                    }
                    .collect() { bookingsInfoResList ->
                        _totalAccumulatedAmountROADRIDE.value = bookingsInfoResList.sumOf {
                            it.amount
                        }
                    }
                Log.d(
                    "calAmountByMade_ROAD_RIDE_VM",
                    _totalAccumulatedAmountROADRIDE.value.toString()
                )

            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByRoa_VM", it) }

        }
    }

    fun calculateTotalAmountMadeForTOURING_BIKECategory() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.distinctBy { it.bikeType }
                    }
                    .catch {  ex ->
                        Log.e("calAmountByMade_TOURING_BIKE_VM", "Exception Caught: ${ex.message}")
                    }
                    .collect() { bookingsInfoResList ->
                        _totalAccumulatedAmountTOURINGBIKE.value = bookingsInfoResList.sumOf {
                            it.amount
                        }
                    }
                Log.d(
                    "calAmountByMade_TOURING_BIKE_VM",
                    _totalAccumulatedAmountTOURINGBIKE.value.toString()
                )

            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByTou_VM", it) }

        }
    }

    fun calculateTotalAmountMadeForTRACK_BIKECategory() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllBookingsInfoAsFlow()
                    .mapNotNull { bikes: List<BookingsInfoRes> ->
                        bikes.distinctBy { it.bikeType }
                    }
                    .catch {  ex ->
                        Log.e("calAmountByMade_TRACK_BIKE_VM", "Exception Caught: ${ex.message}")
                    }
                    .collect() { bookingsInfoResList ->
                        _totalAccumulatedAmountTRACKBIKE.value = bookingsInfoResList.sumOf {
                            it.amount
                        }
                    }
                Log.d(
                    "calAmountByMade_TRACK_BIKE_VM",
                    _totalAccumulatedAmountTRACKBIKE.value.toString()
                )

            }
        } catch (ex: Exception) {
            ex.localizedMessage?.toString()?.let { Log.e("calcAByTra_VM", it) }

        }
    }

    fun getAllCalculatedAmountsByCategoryInVM() {
        try {
            calculateTotalAmountMadeByBikeCategory()
            calculateTotalAmountMadeForBMXCategory()
            calculateTotalAmountMadeForCRUISERCategory()
            calculateTotalAmountMadeForCYCLOCROSS_BIKECategory()
            calculateTotalAmountMadeForELECTRIC_BIKECategory()
            calculateTotalAmountMadeForROAD_RIDECategory()
            calculateTotalAmountMadeForMOUNTAIN_BIKECategory()
            calculateTotalAmountMadeForRECUMBENT_BIKECategory()
            calculateTotalAmountMadeForHYBRID_BIKECategory()
            calculateTotalAmountMadeForFOLDING_BIKECategory()
            calculateTotalAmountMadeForTOURING_BIKECategory()
            calculateTotalAmountMadeForTRACK_BIKECategory()
        } catch (ex: Exception) {
            ex.localizedMessage?.let { Log.e("getAllCalcAmoByCat_VM", it) }
        }
    }



    fun getTopChoiceBikes() {
        _topBikeChoices.value = RequestState.Loading
        Log.d("getTopChoiceBikesVM", _topBikeChoices.value.toString())
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllBikesAsFlow()
                .onStart {
                    Log.d("getTopChoiceBikesVM", "Started Collecting TopChoice Bikes As Flow")
                    Log.d("getTopChoiceBikesVM", _topBikeChoices.value.toString())
                }
                .map { bikes: List<Bike> ->
                    bikes.filter {
                        it.condition == CONDITION.EXCELLENT || it.condition == CONDITION.GOOD
                    }
                }
                .take(10)
                .catch { ex ->
                    Log.w("getTopChoiceBikesVM", "Exception Caught: ${ex.message}")
                }
                .onCompletion { cause: Throwable? ->
                    if (cause != null )
                        Log.d("getTopChoiceBikesVM", """Flow completed with message "${cause.message}" """)
                    else
                        Log.d("getTopChoiceBikesVM", _topBikeChoices.value.toString())
                }
                .collect {
                    _topBikeChoices.value = RequestState.Success(it)
                    Log.d("getTopChoiceBikesVM", "Flow Completed Successfully")
                    Log.d("getTopChoiceBikesVM", _topBikeChoices.value.toString())
                }
        }
    }

    fun getAllBikeCategories() {
        _allBikeCategories.value = RequestState.Loading
        Log.d("getAllBikeCategoriesVM", _allBikeCategories.value.toString())
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllBikesAsFlow()
                .onStart {
                    Log.d("getCategoriesOfBikesVM", "Started Collecting  All Bike Categories As Flow")
                    Log.d("getCategoriesOfBikesVM", _allBikeCategories.value.toString())
                }
                .map { bikes: List<Bike> ->
                    bikes.distinctBy { it.type }
                }
                .catch { ex ->
                    Log.w("getCategoriesOfBikesVM", "Exception Caught: ${ex.message}")
                }
                .onCompletion { cause: Throwable? ->
                    if (cause != null )
                        Log.d("getCategoriesOfBikesVM", """Flow completed with message "${cause.message}" """)
                    else
                        Log.d("getCategoriesOfBikesVM", _allBikeCategories.value.toString())
                }
                .collect() {
                    _allBikeCategories.value = RequestState.Success(it)
                    Log.d("getCategoriesOfBikesVM", "Flow Completed Successfully")
                    Log.d("getCategoriesOfBikesVM", _allBikeCategories.value.toString())
                }
        }
    }
    fun getAllBikesByCategory() {
        _allBikesByCategory.value = RequestState.Loading
        Log.d("getAllBikesByCategoryVM", _allBikesByCategory.value.toString())
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllBikesAsFlow()
                .onStart {
                    Log.d(
                        "getAllBikesByCategoryVM",
                        "Started Collecting  All Bikes By Category As Flow"
                    )
                    Log.d("getAllBikesByCategoryVM", _allBikesByCategory.value.toString())
                }
                .map { bikes: List<Bike> ->
                    bikes.filter{ bike ->
                        bike.type == _selectedBike.value?.type
                    }

                }
                .catch { ex ->
                    Log.w("getAllBikesByCategoryVM", "Exception Caught: ${ex.message}")
                }
                .catch { cause: Throwable? ->
                    if (cause != null)
                        Log.d(
                            "getAllBikesByCategoryVM",
                            """Flow completed with message "${cause.message}" """
                        )
                    else
                        Log.d("getAllBikesByCategoryVM", _allBikesByCategory.value.toString())
                }
                .onCompletion {
                    Log.d("getAllBikesByCategoryVM", "Flow Completed Successfully")
                }
                .collect() {
                    _allBikesByCategory.value = RequestState.Success(it)
                    Log.d("getAllBikesByCategoryVM", _allBikesByCategory.value.toString())
                    Log.d("getCategoryStateVM", getCategoryState.value.toString())
                    Log.d("actionState", action.value.toString())
                }
        }
    }


/*
   fun getAllBikes() {
       _allBikes.value = RequestState.Loading
       Log.d("getAllBikesVM", _allBikes.value.toString())
       try {
           viewModelScope.launch(Dispatchers.IO) {
               _allBikes.value = RequestState.Success(repository.getAllBikes())
               Log.d("getAllBikesVM", _allBikes.value.toString())
           }
       } catch (ex: Exception) {
           _allBikes.value = RequestState.Error(ex)
       }
   }

 */

    fun searchDB(query: String){
    _searchedBikes.value = RequestState.Loading
    Log.d("searchDBVM", _searchedBikes.value.toString())
    viewModelScope.launch(Dispatchers.IO) {
        repository.getBikesNameAsFlow(query)
            .onStart {
                Log.d("searchDBVM", _searchedBikes.value.toString())
                Log.d("searchDBVM", _searchedBikes.value.toString())
            }
            .onEach {
                Log.d("searchDBVM", it.toString())
                Log.d("searchDBVM", _searchedBikes.value.toString())
            }
            .catch { ex ->
                Log.d("searchDBVM", "Exception Caught: ${ex.message}")
            }
            .onCompletion { cause: Throwable? ->
                if (cause != null)
                    Log.d("searchDBVM", """Flow completed with message "${cause.message}" """)
                else
                    Log.d("searchDBVM", _searchedBikes.value.toString())
            }
            .collect() {
                _searchedBikes.value = RequestState.Success(it)
                Log.d("searchDBVM", "Flow Completed Successfully")
                Log.d("searchDBVM", _searchedBikes.value.toString())
            }
    }
}

/*
    fun searchDB(searchQuery: String) {
        _searchedBikes.value = RequestState.Loading
        Log.d("searchedBikesVM", _searchedBikes.value.toString())
        try {
            viewModelScope.launch(Dispatchers.IO) {

                _searchedBikes.value = RequestState.Success(repository.getBikesByName(searchQuery))
                Log.d("searchedBikesVM", _searchedBikes.value.toString())
            }
        } catch (ex: Exception) {
            _searchedBikes.value = RequestState.Error(ex)
            Log.d("searchedBikesVM", _searchedBikes.value.toString())
        }

    }
    */

    fun getAllBookingsInfo() {
        _allBookingsInfo.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {

            repository.getAllBookingsInfoAsFlow()
                .catch { ex ->
                    Log.d("getAllBksInfoVM", "Exception Caught: ${ex.message}")
                    _allBookingsInfo.value = RequestState.Error(ex)
                }
                .collect {
                    _allBookingsInfo.value = RequestState.Success(it.asReversed())
                }
            /*
            val result = repository.getAllBookingsInfoAsFlow().map { it.toBookingInfo() }

            Log.d("getAllBookingsInfoVM", result.toString())

            _allBookingsInfo.value = RequestState.Success(result)

             */
        }
    }

    fun  getAllLeasedBookings() {
        _leasedBookingsInfo.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllBookingsInfoAsFlow()
                .onStart {
                    Log.d("getAllLeasBksInfoVM", "started .....")
                }
                .catch {
                    Log.e("getAllLeasBksInfoVM", it.toString())
                    _leasedBookingsInfo.value = RequestState.Error(it)
                }
                .map { bookingsInfo: List<BookingsInfoRes> ->
                    bookingsInfo.filter {
                        it.bikeReturnStatus == ReturnStatus.LEASED
                    }
                }
                .collect {
                    Log.d("getAllLeasBksInfoVM", it.toString())
                    _leasedBookingsInfo.value = RequestState.Success(it.asReversed())
                }
        }
    }


    fun getAllReturnedBookings() {
        _returnedBookingsInfo.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllBookingsInfoAsFlow()
                .catch {
                    Log.e("getAllRetBksInfoVM", it.toString())
                    _returnedBookingsInfo.value = RequestState.Error(it)
                }
                .map { bookingsInfo: List<BookingsInfoRes> ->
                    bookingsInfo.filter {
                        it.bikeReturnStatus == ReturnStatus.RETURNED
                    }
                }
                .collect {
                    Log.d("getAllRetBksInfoVM", it.toString())
                    _returnedBookingsInfo.value = RequestState.Success(it.asReversed())
                }
        }
    }

    fun updateReturnStatusOfBookingInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val editReturnStatusBookingInfo = selectedBookingInfoItem.value?.let {
                bookingInfoReturnStatus.value?.let { it1 ->
                    EditReturnStatusBookingInfo(
                        bookingId = it.bookingId,
                        bikeReturnStatus = it1.bikeReturnStatus
                    )
                }
            }
            if (editReturnStatusBookingInfo != null) {
                repository.updateReturnStatusOfBookingInfo(editReturnStatusBookingInfo)
            }
        }
    }

    fun getBookingInfoByMpesaReceiptNumber(mpesaReceiptNumber: String) {

        _searchedBookingInfo.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getBookingsInfoByReceiptId(mpesaReceiptNumber)

                Log.d("getBkInByMpeRecNoVM", result.toString())
                Log.d("getBkInByMpeRcNo_MPN_VM", mpesaReceiptNumber)

                if (result != null) {
                    _searchedBookingInfo.value = RequestState.Success(result)
                } else {
                    _searchedBikes.value = RequestState.Error(Throwable(message = "Unable to Fetch Bookings Info with provided receipt id"))
                }
            } catch (ex: Exception) {
                ex.localizedMessage?.let { Log.e("getBkInByMpeRecNo", it) }
                _searchedBookingInfo.value = RequestState.Error(ex)
            }
        }
    }

    fun updateOrAddBike(
        bikeId: String? = null,
        isBooked: Boolean? = null,
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
            isBooked = isBooked ?: bike.value?.isBooked,
            condition = condition ?: bikeCondition.value,
            description = description ?: bikeDescription.value,
            imageUrl = imageUrl ?: imageData.value.toString(),
            modifiedAt = Timestamp.now(),
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
                modifiedAt = Timestamp.now(),
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

    fun deleteBike() {
        viewModelScope.launch(Dispatchers.IO) {
            if( _selectedBike.value != null ) {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.deleteBikeAsFLow(_selectedBike.value?.bikeId!!)
                    Log.d("deleteBikeVM" ,"In deleted Bike VM")
                }
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
                imageData.value?.let { updateBikeImage(it) }
            }
            Action.DELETE -> {
                deleteBike()
            }
            Action.DELETE_ALL -> {

            }
            Action.UNDO -> {

            }
            Action.GET_ALL_BIKES_BY_TOP_CHOICE -> {
                getTopChoiceBikes()
            }
            Action.GET_ALL_BIKES_BY_CATEGORY -> {
                getAllBikesByCategory()
            }
            Action.GET_CATEGORIES_OF_BIKE -> {
                getAllBikeCategories()
            }
            Action.GET_TOP_CHOICE_BIKES -> {

            }

            else -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }
    fun updateSelectedBikeState() {
        _selectedBike.value = null
    }


    fun updateBikeFields(selectedBike: Bike?) {
        if ( selectedBike != null ) {
            bikeName.value = selectedBike.name.toString()
            bikeType.value = selectedBike.type!!
            bikeCondition.value = selectedBike.condition!!
            bikeDescription.value = selectedBike.description.toString()
            bikeImageUrl.value = selectedBike.imageUrl.toString()
            bikePrice.value = selectedBike.price.toString()
        } else {
            bikeName.value = ""
            bikeType.value = TYPE.BMX
            bikeCondition.value = CONDITION.AVERAGE
            bikeDescription.value = ""
            bikeImageUrl.value = ""
            bikePrice.value = ""
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


