package ru.binaryblitz.justforyou.ui.main.deliveries

import android.graphics.Typeface
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.Date

/**
 * Decorate a day by making the text bold
 */
class DayDecorator : DayViewDecorator {

  private var date: CalendarDay? = null

  init {
    date = CalendarDay.today()
  }

  override fun shouldDecorate(day: CalendarDay): Boolean {
    return date != null && day == date
  }

  override fun decorate(view: DayViewFacade) {
    view.addSpan(StyleSpan(Typeface.BOLD))
    view.addSpan(RelativeSizeSpan(1.4f))
  }

  fun setDate(date: Date) {
    this.date = CalendarDay.from(date)
  }
}