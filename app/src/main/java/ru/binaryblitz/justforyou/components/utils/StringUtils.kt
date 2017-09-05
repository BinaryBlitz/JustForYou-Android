package ru.binaryblitz.justforyou.components.utils

import java.lang.Math.abs


const val programCaseOne = "программа"
const val programCaseTwo = "программы"
const val programCaseThree = "программ"

object StringUtils {

  /**
   * Selects the correct form of the program name depending on count
   */
  fun programCase(count: Int): String {
    var countProgram = count
    var stringForm = "" + countProgram + " "
    countProgram = abs(countProgram)

    if (countProgram % 10 <= 1 && countProgram % 100 != 11) {
      stringForm += programCaseOne
    } else if (countProgram % 10 in 2..4 && (countProgram % 100 < 10 || countProgram % 100 >= 20)) {
      stringForm += programCaseTwo
    } else {
      stringForm += programCaseThree
    }
    return stringForm
  }
}