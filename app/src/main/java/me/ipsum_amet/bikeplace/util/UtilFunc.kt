package me.ipsum_amet.bikeplace.util

import android.util.Log
import kotlinx.datetime.toKotlinLocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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




fun formatISODate(isoDate: String): String {
    //Log.d("formatISODate:", formatedDate.toString())

    val formatter = DateTimeFormat.forPattern("dd MM yyyy, HH:mm")
    //val parser = ISODateTimeFormat.dateHourMinute()

    //2022-11-23T21:15:27.000+00:00
    val parser = ISODateTimeFormat.dateTimeNoMillis()
    //val date = DateTime.parse(isoDate, formatter)
    val parsedDateTimeUsingFormatter = parser.parseDateTime(isoDate.removeRange(19, 23)).toLocalDateTime()
    //val parsedDateTimeUsingFormatter = parser.parseLocalDateTime(isoDate)
    val mm = parsedDateTimeUsingFormatter.minuteOfHour
    val hh = parsedDateTimeUsingFormatter.hourOfDay
    val dd = parsedDateTimeUsingFormatter.dayOfMonth
    val MM = parsedDateTimeUsingFormatter.monthOfYear
    val yy = parsedDateTimeUsingFormatter.year
    val z = parsedDateTimeUsingFormatter.values



    //val date = DateTime.parse( "$parsedDateTimeUsingFormatter", formatter )

    /*
   val formattedDate = isoDate.toLocalDateTime()
   val mm = formattedDate.month
   val dd = formattedDate.dayOfMonth
   val yyyy = formattedDate.year
    */
    //22-11-23T22:22:00.000
    return "$dd-$MM-$yy, $hh:$mm"
}

fun formatDateTimeForServer(leaseActOrExpDate: String, leaseActOrExpTime: String): kotlinx.datetime.LocalDateTime {

    //dd-MM-yyyy HH:mm
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    val javaDateTime = LocalDateTime.parse("$leaseActOrExpDate $leaseActOrExpTime", formatter)

    return  javaDateTime.toKotlinLocalDateTime()

}
