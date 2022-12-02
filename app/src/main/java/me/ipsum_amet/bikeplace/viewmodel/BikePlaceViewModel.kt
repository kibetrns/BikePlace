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
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.ipsum_amet.bikeplace.data.dto.request.BookingsInfoReq
import me.ipsum_amet.bikeplace.data.dto.request.BookingsReq
import me.ipsum_amet.bikeplace.util.*
import me.ipsum_amet.bikeplace.data.model.*
import me.ipsum_amet.bikeplace.data.model.remote.STKPushRequest
import javax.inject.Inject
import me.ipsum_amet.bikeplace.data.repo.BikePlaceRepository
import me.ipsum_amet.bikeplace.data.service.MpesaService
import java.util.*

@HiltViewModel
class BikePlaceViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val repository: BikePlaceRepository,
   private val mpesaService: MpesaService,
) : ViewModel() {


    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val isAdmin = mutableStateOf(false)
    val refreshPostProgress = mutableStateOf(false)

    val user = mutableStateOf<User?>(null)

    val popUpNotification = mutableStateOf<Event<String>?>(null)

    var password: MutableState<String> = mutableStateOf("")
    var confirmedPassword: MutableState<String> = mutableStateOf("")

    var emailAddress = mutableStateOf("")
    var fullName  = mutableStateOf("")
    var imageUrl = mutableStateOf("")



    var title = mutableStateOf<String?>("")
    var area = mutableStateOf<String?>("")
    var streetName = mutableStateOf<String?>("")
    var moreInfo = mutableStateOf<String?>("")
    var geoPoint = mutableStateOf<GeoPoint?>(null)

    var dropOffLocation  = BikeDropOffAddress(
        title = title.value,
        area = area.value,
        streetName = streetName.value,
        moreInfo = moreInfo.value
    )

    var bikeDropOffAddress  = mutableStateOf(user.value?.dropOffLocation)


    var phoneNumber = mutableStateOf("")
    var currentLocation = mutableStateOf<GeoPoint?>(null)
    var orderBikes = mutableStateOf<List<Bike>?>(null)


    private val bike = mutableStateOf<Bike?>(null)

    var bikeName = mutableStateOf("")
    var bikeType = mutableStateOf(TYPE.BMX)
    var bikeCondition = mutableStateOf(CONDITION.AVERAGE)
    var bikeDescription = mutableStateOf("")
    var bikeImageUrl = mutableStateOf("")
    var bikePrice = mutableStateOf("0")
    var isBooked = mutableStateOf(false)

    var imageData = mutableStateOf<Uri?>(null)


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

    private val _selectedBike: MutableStateFlow<Bike?> = MutableStateFlow(Bike(
        bikeId = null,
        userId = null,
        name = null,
        price = 1.0,
        imageUrl = null,
        description = null,
        isBooked = null,
        postedAt = null,
        modifiedAt = null,
        condition = null,
        type = null,
        leaseStatus = null
    ))
    val selectedBike: StateFlow<Bike?> = _selectedBike

    private val _topBikeChoices: MutableStateFlow<RequestState<List<Bike>>> = MutableStateFlow(RequestState.Idle)
    val topBikeChoices: StateFlow<RequestState<List<Bike>>> = _topBikeChoices

    private val _allBikeCategories: MutableStateFlow<RequestState<List<Bike>>> = MutableStateFlow(RequestState.Idle)
    val allBikeCategories: StateFlow<RequestState<List<Bike>>> = _allBikeCategories

    private val _allBikesByCategory: MutableStateFlow<RequestState<List<Bike>>> = MutableStateFlow(RequestState.Idle)
    val allBikesByCategory: StateFlow<RequestState<List<Bike>>> = _allBikesByCategory

    val alreadyLoggedIn = mutableStateOf(false)

    var hoursToLease = mutableStateOf("3")

    var leaseActivationTitle = mutableStateOf("Lease Activation")
    var leaseActivationDateInput = mutableStateOf(getDate())
    var leaseActivationTimeInput = mutableStateOf(getTime())
    var leaseExpiryTitle = mutableStateOf("Lease Expiry")
    var leaseExpiryDateInput = mutableStateOf(getDate())
    var leaseExpiryTimeInput = mutableStateOf(getTime())

    private val timestamp = getTimeStamp()


    private val sTKPushPassword = BUSINESS_SHORT_CODE.toString()+PASS_KEY+timestamp
    private val pN = mutableStateOf("")
    private val encodedPassWord = encodePassword(passWordToEncode = sTKPushPassword)
    private val amount = calculateTotalCheckoutPrice()?.toInt()

    private val _userDetails: MutableStateFlow<User?> = MutableStateFlow(null)
    val userDetails = _userDetails

    private val _bookingsInfo = MutableStateFlow<RequestState<List<BookingInfo>>>(RequestState.Idle)
    val bookingsInfo = _bookingsInfo

    private val returnStatus = mutableStateOf(ReturnStatus.PENDING)

    val summarySubtotal = mutableStateOf( calculatePricingGivenDates(
        leaseActivationDateTime = formatDateTimeForServer(
            leaseActivationDateInput.value,
            leaseActivationTimeInput.value
        ),
        leaseExpiryDateTime = formatDateTimeForServer(
            leaseExpiryDateInput.value,
            leaseExpiryTimeInput.value
        ),
        pricingPerHour = _selectedBike.value?.price!!
    )
    )

    val dropOffCost = mutableStateOf(1.0)

    val totalSummaryPaymentAmount = mutableStateOf(
        calculateFinalCheckoutPaymentAmount(
            VAT = calculateTotalVATAmount(
                subTotal = summarySubtotal.value,
                dropOffCharge = dropOffCost.value,
                VAT = VAT_CONST
            ),
            subTotal = summarySubtotal.value,
            dropOffCharge = dropOffCost.value
        )
    )


    val VAT = mutableStateOf( calculateTotalVATAmount(
        subTotal = summarySubtotal.value,
        dropOffCharge = dropOffCost.value,
        VAT = VAT_CONST
    ) )

    /*
    private val bRAmount = 1
    private val bRBikeId = selectedBike.value?.bikeId
    private val bRBikeLeaseActivation = formatDateTimeForServer(leaseActivationDateInput.value, leaseActivationTimeInput.value)
    private val bRBikeLeaseExpiry = formatDateTimeForServer(leaseExpiryDateInput.value, leaseActivationTimeInput.value)
    private val bRBikeName   = selectedBike.value?.name
    private val bRBikeDropOffLocation = dropOffLocation
    private val bRUserName = user.value?.fullName
    private val bRUserId = user.value?.userId
    private val bRUserPhoneNumber = user.value?.phoneNumber?.let {
        transformPhoneNumber(it)
    }

     */
/*
    fun logBookingsReq(){
        Log.d("logBkReqAm", bRAmount.toString() )
        Log.d("logBkReqBI", bRBikeId.toString() )
        Log.d("logBkReqLA", bRBikeLeaseActivation.toString() )
        Log.d("logBkReqLE", bRBikeLeaseExpiry.toString() )
        Log.d("logBkReqBN", bRBikeName.toString() )
        Log.d("logBkReqDOL", bRBikeDropOffLocation.toString() )
        Log.d("logBkReqUn", bRUserName.toString() )
        Log.d("logBkReqUId", bRUserId.toString() )
        Log.d("logBkReqUPN", bRUserPhoneNumber.toString() )
    }                bikePlaceViewModel.calculateTotalCheckoutPrice()?.let { it1 ->


 */



    private fun applybookingsReqBody(): BookingsReq? {
        val bRAmount = calculateTotalCheckoutPrice()?.plus(dropOffCost.value)?.plus(VAT.value)
        val bRBikeId = selectedBike.value?.bikeId
        val bRBikeLeaseActivation = formatDateTimeForServer(
            leaseActivationDateInput.value,
            leaseActivationTimeInput.value
        )
        val bRBikeLeaseExpiry = formatDateTimeForServer(
            leaseExpiryDateInput.value,
            leaseExpiryTimeInput.value
        )
        val bRBikeName   = selectedBike.value?.name
        val bRBikeDropOffLocation = dropOffLocation
        val bRUserName = user.value?.fullName
        val bRUserId = user.value?.userId
        val bRUserPhoneNumber = user.value?.phoneNumber?.let {
            transformPhoneNumber(it)
        }
        val bRReturnStatus = returnStatus.value
        //return if (bRBikeName != null && bRBikeId != null && bRUserId != null && bRUserPhoneNumber != null && bRUserName != null) {
           val x = bRAmount?.let {
               BookingsReq(
                   amount = it.toInt(),
                   bikeId = bRBikeId!!,
                   bikeLeaseActivation = bRBikeLeaseActivation,
                   bikeLeaseExpiry = bRBikeLeaseExpiry,
                   bikeName = bRBikeName!!,
                   bikeDropOffLocation = bRBikeDropOffLocation,
                   userName = bRUserName!!,
                   userId = bRUserId!!,
                   userPhoneNumber = bRUserPhoneNumber!!,
                   bikeReturnStatus = bRReturnStatus
               )
           }
        Log.d("AAAAABBBBBRRBB", x.toString())
        return x

    }



    private fun applySTKPushBody(): STKPushRequest? {
        val phoneNumber = user.value?.phoneNumber
        val transformedPhoneNumber = phoneNumber?.let { transformPhoneNumber(phoneNumber = it) }

        return transformedPhoneNumber?.let {
            STKPushRequest(
                accountReference = ACCOUNT_REFERENCE,
                amount = 1,
                businessShortCode = BUSINESS_SHORT_CODE,
                callBackURL = CALLBACK_URL,
                partyA = it,
                partyB = PARTY_B,
                password = encodedPassWord,
                phoneNumber = it,
                timestamp = timestamp,
                transactionDesc = TRANSACTION_DESC,
                transactionType = TRANSACTION_TYPE
            )
        }
    }


    /*
    private var  stkPushRequest = pN?.let {
        STKPushRequest(
            accountReference = ACCOUNT_REFERENCE,
            amount = amount,
            businessShortCode = BUSINESS_SHORT_CODE,
            callBackURL = CALLBACK_URL,
            partyA = it,
            partyB = PARTY_B,
            password = encodedPassWord,
            phoneNumber = it,
            timestamp = timestamp,
            transactionDesc = TRANSACTION_DESC,
            transactionType = TRANSACTION_TYPE
        )
    }

     */





    /*
    val bearerTokenStorage = mutableListOf<BearerTokens>()

    fun dddd() {
        bearerTokenStorage
            .add(
                BearerTokens(
                accessToken = AccessTokenResponse::accessToken.toString(),
                refreshToken = AccessTokenResponse::accessToken.toString()
            ))

    }

     */


    init {
        auth.signOut()
        val currentUser = auth.currentUser
        signedIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(uid = it)
        }
       // fetchAccessToken()
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
            dropOffLocation = bikeDropOffAddress.value,
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
           bikeDropOffAddress: String? =null,
           orderedBikes: List<Bike>? = null
       ) {
           viewModelScope.launch(Dispatchers.IO) {
               val uid = auth.currentUser?.uid
               val user = User(
                   userId = uid,
                   fullName = fullName ?: user.value?.fullName,
                   phoneNumber = phoneNumber ?: user.value?.phoneNumber,
                   imageUrl = imageUrl ?: user.value?.imageUrl,
                   bikeDropOffAddress = bikeDropOffAddress ?: user.value?.bikeDropOffAddress,
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
                    _userDetails.value = user1
                    inProgress.value = false
                    popUpNotification.value = Event("User Data Retrieved Successfully")

                    Log.d("userData", user.value.toString())

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
                    if (cause != null )
                        Log.d("getSelectedBikeVM", """Flow completed with message "${cause.message}" """)
                    else
                        Log.d("getSelectedBikeVM", _selectedBike.value.toString())
                }
                .collect() {
                    _selectedBike.value = it
                    bike.value = it
                    Log.d("getSelectedBikeVM", "Flow Completed Successfully")
                    Log.d("getSelectedBikeVM", _selectedBike.value.toString())

                   // Log.d("makeMpesaPaymentVM", applybookingsReqBody().toString())
                    Log.d("LLLLLLLLLLLLLL", applybookingsReqBody().toString())

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

    fun calculateTotalCheckoutPrice(): Double? {
        return _selectedBike.value?.price?.let {
            calculatePricingGivenDates(
                leaseActivationDateTime = formatDateTimeForServer(
                    leaseActivationDateInput.value,
                    leaseActivationTimeInput.value
                ),
                leaseExpiryDateTime = formatDateTimeForServer(
                    leaseExpiryDateInput.value,
                    leaseExpiryTimeInput.value
                ),
                pricingPerHour = it
            )
        }
    }
    /*
    fun calculateTotalCheckoutPrice(): Double {
        val result = mutableStateOf(0.0)
        try {
            result.value = _selectedBike.value?.price!! * hoursToLease.value.toDouble()
        } catch (ex: Exception) {
            Log.w("main", "${ex.message}")
        }
        return result.value
    }

     */



    fun getAllBikes() {
        _allBikes.value = RequestState.Loading
        Log.d("getAllBikesVM", _allBikes.value.toString())
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllBikesAsFlow()
                .onStart {
                    Log.d("getAllBikesVM", "Started Collecting All Bikes As Flow")
                    Log.d("getAllBikesVM", _allBikes.value.toString())
                }
                .map { bikes: List<Bike> ->
                    bikes.filter { bike: Bike ->
                        bike.isBooked == false
                    }
                }
                .onEach {
                    Log.d("getAllBikesVM", it.toString())
                    Log.d("getAllBikesVM", _allBikes.value.toString())
                }
                .catch {  ex ->
                    Log.d("getAllBikesVM", "Exception Caught: ${ex.message}")
                }
                .onCompletion { cause: Throwable? ->
                    if (cause != null )
                        Log.d("getAllBikesVM", """Flow completed with message "${cause.message}" """)
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
                .map { bikes: List<Bike> ->
                    bikes.sortedWith(compareBy { it.condition?.name?.length } )
                }
                .map { bikes: List<Bike> ->
                    bikes.asReversed()
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
    viewModelScope.launch(Dispatchers.IO) {        user.value?.phoneNumber?.let { Log.d("phone", it) }

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
    /*
    fun fetchAccessToken() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                mpesaService.loadTokens()
                Log.d("fetchAccessToken", mpesaService.getAccessToken().toString())
            }
        } catch (ex: Exception) {
            Log.w("main", "Error while making payment")
        }
    }

     */


    fun makeMpesaPayment() {
        viewModelScope.launch(Dispatchers.IO) {

            applybookingsReqBody()?.let {
                repository.makeBookingWithBPAPI(bookingsReq = it)
                Log.d("makeMpesaPaymentVM",it.toString())
            }

        }

        /*
        try {
            viewModelScope.launch(Dispatchers.IO) {
                applySTKPushBody()?.let { mpesaService.sendPush(it) }
                Log.d("encodedPassword", encodedPassWord)
                Log.d("stkPushBody", applySTKPushBody().toString())
                Log.d("timeStamp", timestamp)
                Log.d("pN", pN.value)
                Log.d("user", user.value.toString())
                Log.d("transformPhoneNumber",
                    user.value?.phoneNumber?.let { transformPhoneNumber(it) }.toString()
                )
            }
        } catch (ex: Exception) {
            Log.w("main", "Error while making payment")
        }

         */
    }


    suspend fun getAllBookingInfoOfUser() {
        _bookingsInfo.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {

            user.value?.userId?.let { userId ->
                BookingsInfoReq(
                    userId = userId,
                    mpesaReceiptNumber = ""
                )
            }?.let { bookingInfoReq: BookingsInfoReq ->
                Log.d("getAllBokinInfoOfUserVM", bookingInfoReq.toString())

                val result = repository.getAllBikeInfoByUserIdAsFlow(bookingInfoReq)

                Log.d("getAllBokinInfoOfUserVM", result.toString())


                _bookingsInfo.value = RequestState.Success(result)

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

    fun validatePhoneNumber(): Boolean {
        return phoneNumber.value.length == 10 && phoneNumber.value.startsWith('0') && phoneNumber.value[1] == '7'
    }

    fun transformPhoneNumber(phoneNumber: String): Long {
        val x= phoneNumber.replaceFirst("0", "254").toLong()
        Log.d("transformPhoneNumber", x.toString())
        return x
    }

}


