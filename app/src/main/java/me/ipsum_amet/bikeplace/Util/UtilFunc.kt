package me.ipsum_amet.bikeplace.Util

import android.util.Base64
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.toByteArray

fun encodePassword(passWordToEncode: String): String {
    val x = Base64.encodeToString(passWordToEncode.toByteArray(), Base64.NO_WRAP)
    Log.d("encodePasswordUtilFunc,", x.toString())
    return x

}
fun getTimeStamp(): String {
    val x = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format( Date())
    Log.d("getTimeStamp,", x.toString())
    return x
}

fun getDate(): String {
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format( Date() )
}
fun getTime(): String {
   return SimpleDateFormat("HH:mm", Locale.getDefault()).format( Date() )
}