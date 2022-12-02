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

const val BUSINESS_SHORT_CODE = 174379
const val TRANSACTION_TYPE = "CustomerPayBillOnline"
const val PARTY_B = 174379
const val CALLBACK_URL = "https://9cce-41-89-227-170.eu.ngrok.io/api/v1/mobile-payment/saf/stk-transaction-result"
const val CALLBACK_URL_2 = "https://enejup2x16w58.x.pipedream.net/"
const val CALLBACK_URL_3 = "https://eo10qkgopt08moe.m.pipedream.net"
const val ACCOUNT_REFERENCE = "BikePlace Enterprises"
const val TRANSACTION_DESC = "Payment for Bikes Leased from BikePlace Enterprises"
const val PASS_KEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"

const val BP_API_BASE_URL = "https://9cce-41-89-227-170.eu.ngrok.io"

const val VAT_CONST = 12.34

sealed class BPAPIEndpoints(val url: String) {
    object MpesaExpress: BPAPIEndpoints(url = "$BP_API_BASE_URL/api/v1/mobile-payment/saf/stk-transaction-request")

    object UserBookingInfoByUserId: BPAPIEndpoints(url = "$BP_API_BASE_URL/api/v1/user-bookings-info")

    object BookingInfoByReceiptId: BPAPIEndpoints(url = "$BP_API_BASE_URL/api/v1/bookings-info-by-receipt-id")

}




