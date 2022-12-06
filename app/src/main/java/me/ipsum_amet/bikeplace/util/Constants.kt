package me.ipsum_amet.bikeplace.util

const val REALM_SCHEMA_VERSION = 0L
const val REALM_DB_NAME = "BikePlace.db"
const val APP_ID = "bike_place_app-vcqtd"


const val REGISTER_SCREEN = "register"
const val SIGNIN_SCREEN = "signIn"
const val RESET_PASSWORD_SCREEN = "resetPassword"
const val LIST_SCREEN = "list/{action}"
const val BIKE_SCREEN = "bike/{bikeId}"
const val BIKE_DETAILS_SCREEN = "bikeDetails/{bikeId}"
const val HOME_SCREEN = "home/?action={action}"
const val CATEGORY_LIST_SCREEN = "categoryList/{action}"


const val LIST_ARGUMENT_KEY = "action"
const val BIKE_ARGUMENT_KEY = "bikeId"
const val CATEGORY_LIST_SCREEN_ARGUMENT = "action"

const val SIGNIN_ARGUMENT_KEY = ""
const val HOME_SCREEN_ARGUMENT_KEY_1 = "action"
const val HOME_SCREEN_ARGUMENT_KEY_2 = "bikeId"


const val USERS = "users"
const val BIKES = "bikes"

const val BP_API_BASE_URL = "https://4fdd-105-160-26-230.in.ngrok.io"

sealed class BPAPIEndpoints(val url: String) {
    object AllBookingsInfo: BPAPIEndpoints(url = "$BP_API_BASE_URL/api/v1/all-bookings-info")

    object BookingInfoByReceiptId: BPAPIEndpoints(url = "$BP_API_BASE_URL/api/v1/bookings-info-by-receipt-id")

    object UpdateReturnStatusOfBookingInfo: BPAPIEndpoints(url = "$BP_API_BASE_URL/api/v1/update-booking-info")


}





