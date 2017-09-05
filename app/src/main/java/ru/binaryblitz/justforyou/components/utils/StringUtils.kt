package ru.binaryblitz.justforyou.components.utils

import java.lang.Math.abs


const val programCaseOne = "программа"
const val programCaseTwo = "программы"
const val programCaseThree = "программ"

object StringUtils {
  /**
   * Selects the correct form of the program name depending on count
   */
  fun programCase(count: Long): String {
    var countProgram = count
    var stringForm = java.lang.Long.toString(countProgram) + " "
    countProgram = abs(countProgram)

    if ((countProgram % 10).equals(1) && !(countProgram % 100).equals(11)) {
      stringForm += programCaseOne
    } else if (countProgram % 10 in 2..4 && (countProgram % 100 < 10 || countProgram % 100 >= 20)) {
      stringForm += programCaseTwo
    } else {
      stringForm += programCaseThree
    }
    return stringForm
  }
}
