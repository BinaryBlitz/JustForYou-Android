package ru.binaryblitz.justforyou.components.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


/**
 * Class is used for date string formatting and parsing from server
 */
object DateUtils {
  private val serverDateFormat: String = "yyyy-MM-dd'T'hh:mm"

  fun convertToServerDate(date: Date, hour: Int, minute:Int): String {
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    var selectedDate = calendar.getTime()
    return SimpleDateFormat(serverDateFormat).format(selectedDate)
  }

  fun parseServerDate(date: String): Date {
    return SimpleDateFormat(serverDateFormat).parse(date)
  }
}
