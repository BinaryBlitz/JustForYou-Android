package ru.binaryblitz.justforyou.components.utils

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Class is used for date string formatting and parsing from server
 */
object DateUtils {
  private val serverDateFormat: String = "yyyy-MM-dd'T'hh:mm"

  fun convertToServerDate(date: Date): String {
    return SimpleDateFormat(serverDateFormat).format(date)
  }
}
